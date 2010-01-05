package com.systop.fsmis.assessment.webapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.ApplicationException;
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.assessment.service.AsseMemberManager;
import com.systop.fsmis.assessment.service.ExpertMembersManager;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.Expert;

/**
 * 针对风险评估实体选择专家组成员
 * @author ShangHua
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MembersSelectorAction extends
		ExtJsCrudAction<Expert, ExpertMembersManager> {
	
	private Assessment assessment;
	
	private static final String TEMPLATE_REMOVED_MEMBERS = "TEMPLATE_REMOVED_MEMBERS";
	
	private static final String TEMPLATE_ADDED_MEMBERS = "TEMPLATE_ADDED_MEMBERS";
	
	private String expertType;
	
	/**
	 * 评估专家成员管理Manager
	 */
	@Autowired
	private AsseMemberManager asseMemberManager;
	
	// 查看的评估申请所参与的专家
	//private List<Assessment> memberedList = new ArrayList<Assessment>();

	/**
	 * 初始化Assessment实例
	 */
	public void initAssessment() {
		if (getAssessment() == null || getAssessment().getId() == null) {
			throw new ApplicationException("Please select assessment to assign to");
		}
		assessment = getManager().getAssessmentById(assessment.getId());
		if (assessment == null) {
			throw new ApplicationException("Please select assessment to assign to");
		}
	}
	
	/**
	 * 客户端通过CheckBox选择一个专家，提交服务器端，将这个Expert存入Session
	 * 
	 * @return
	 */
	public String selectMember() {
		if (getAssessment() == null || getAssessment().getId() == null
				|| getModel() == null || getModel().getId() == null) {
			throw new ApplicationException("Please select a expert at least.");
		}
		selectMember(getAssessment().getId(), getModel().getId(), true);
		
		return JSON;
	}
	
	/**
	 * 页面通过AJAX方式把为风险评估分配的专家成员Id和风险评估Id传入服务器。
	 * @param assessmentId 风险评估Id
	 * @param expertId 专家Id
	 * @param selected
	 */
	private void selectMember(Integer assessmentId, Integer expertId, boolean selected) {
		if (expertId == null || assessmentId == null) {
			return;
		}
		initAssessment();
    
		setAssessment(getManager().getAssessmentById(assessmentId));
		if (getModel() == null || getAssessment() == null) {
			return;
		}
		if (selected) {
			temporaryAddMember(assessment, getModel());
		} else {
			temporaryRemoveMember(assessment, getModel());
		}
	}
	
	/**
	 * 客户端通过CheckBox反选一个用户，通知服务器端，将这个Expert从Session中剔出
	 * 
	 * @return
	 */
	public String deselectMember() {
		if (getAssessment() == null || getAssessment().getId() == null
				|| getModel() == null || getModel().getId() == null) {
			throw new ApplicationException("Please select a expert at least.");
		}
		selectMember(getAssessment().getId(), getModel().getId(), false);

		return JSON;
	}
	
	/**
	 * 取消保存评估专家成员信息
	 * @return
	 */
	public String cancelSaveAssessmentMembers() {
		initAssessment();
		clearSession(assessment);

		return JSON;
	}
	
	/**
	 * 为指定的项目设定成员
	 * 
	 * @return
	 */
	public String saveAssessmentMembers() {
    initAssessment();
    //临时删除（反分配）的专家
    Set<Integer> members = getTemporaryMembers(assessment, TEMPLATE_REMOVED_MEMBERS);
    for (Integer expertId : members) {
  		//根据评估Id、专家Id和Session中存在的专家类别获取中间表中已存在的专家信息
    	AsseMember asseMember = asseMemberManager.getAsseMember(assessment.getId(), expertId, getExpertType());
      if (asseMember != null) {
      	asseMemberManager.remove(asseMember);
      }
    }
    //临时添加（分配）的专家
    members = getTemporaryMembers(assessment, TEMPLATE_ADDED_MEMBERS);
    for (Integer expertId : members) {
      Expert expert = new Expert();
      expert = getManager().get(expertId);
      if (expert != null) {
  			AsseMember asseMember = new AsseMember();
  			asseMember.setExpert(expert);
  			asseMember.setAssessment(assessment);
  			//根据Session设置中间表中的
  			asseMember.setType(getExpertType());
  			asseMemberManager.save(asseMember);
      }
    }

    clearSession(assessment);

		return JSON;
	}
	
	/**
	 * 
	 * @param assessment
	 */
	public void clearSession(Assessment assessment) {

	    HttpSession session = getRequest().getSession();
	    Map map = (Map) session.getAttribute(TEMPLATE_ADDED_MEMBERS);
	    if (map.containsKey(assessment.getId())) {
	      map.remove(assessment.getId());
	    }
	    map = (Map) session.getAttribute(TEMPLATE_REMOVED_MEMBERS);
	    if (map.containsKey(assessment.getId())) {
	      map.remove(assessment.getId());
	    }
	    //清除专家类型Session
	    getRequest().getSession().removeAttribute("expertType");
	}
	
	/**
	 * 根据评估分配专家成员情况，在Session中暂存一个已分配的Expert Id
	 * @param assessment 指定的风险评估实体
	 * @param expert 将从项目中删除的用户
	 */
	private void temporaryAddMember(Assessment assessment, Expert expert) {
		Set membersAdded = getTemporaryMembers(assessment, TEMPLATE_ADDED_MEMBERS);
		//根据评估Id、专家Id和Session中存在的专家类别获取中间表中已存在的专家信息
		AsseMember asseMember = asseMemberManager.getAsseMember(assessment.getId(), expert.getId(), getExpertType());
		if (asseMember == null) {
			membersAdded.add(expert.getId());
		}

		Set memberRemoved = getTemporaryMembers(assessment, TEMPLATE_REMOVED_MEMBERS);
		memberRemoved.remove(expert.getId());
	}
	
	/**
	 * 根据评估分配专家成员情况，在Session中暂存一个反分配的Expert Id
	 * @param assessment 指定的风险评估实体
	 * @param expert 将从风险评估中删除的专家
	 */
	public void temporaryRemoveMember(Assessment assessment, Expert expert) {
		setAssessment(getManager().getAssessmentById(assessment.getId()));

		Set membersRemoved = getTemporaryMembers(assessment, TEMPLATE_REMOVED_MEMBERS);
		//根据评估Id、专家Id和Session中存在的专家类别获取中间表中已存在的专家信息
		AsseMember asseMember = asseMemberManager.getAsseMember(assessment.getId(), expert.getId(), getExpertType());		
		if (asseMember.getExpert() != null) {
			membersRemoved.add(expert.getId());
		}

		Set memberIdsAdded = getTemporaryMembers(assessment, TEMPLATE_ADDED_MEMBERS);
		memberIdsAdded.remove(expert.getId());
	}
	
	/**
	 * 从Session中取得某个项目的临时Members，包括已指定和待指定的
	 * @param assessment 指定的风险评估实体
	 * @param sessionName session的键名
	 * @return
	 */
	public Set getTemporaryMembers(Assessment assessment, String sessionName) {
		Map AssessmentMembers = (Map) getRequest().getSession().getAttribute(
				sessionName);
		if (AssessmentMembers == null) {
			AssessmentMembers = Collections.synchronizedMap(new HashMap());
			getRequest().getSession().setAttribute(sessionName, AssessmentMembers);
		}
		Set expertIds = (Set) AssessmentMembers.get(assessment.getId());
		if (expertIds == null) {
			expertIds = Collections.synchronizedSet(new HashSet());
			AssessmentMembers.put(assessment.getId(), expertIds);
		}

		return expertIds;
	}

	/**
	 * 列出所有专家，同时，根据当前{@link #assessment}的专家组成员，将已经归属该风险评估{@link #assessment}
	 * 的专家的changed属性设置为true
	 * 
	 * @return
	 */
	public String membersOfAssessment() {
		initAssessment();
		// 得到当前专家组成员集合		
    List<AsseMember> members = asseMemberManager.getAsseMembers(assessment.getId(), expertType);
   
		// 得到其它类型的专家组成员集合		  
    List<AsseMember> otherMembers = asseMemberManager.getAsseOtherMembers(assessment.getId(), expertType);
    
    //将页面选择的专家类型放到Session中便于操作。
    getRequest().getSession().setAttribute("expertType", expertType);
    
   	List mapMembers = new ArrayList(members.size());
		page = PageUtil.getPage(getPageNo(), getPageSize());
		// 分页查询全部/根据姓名查询
		String expertName = StringUtils.EMPTY;
		try {
			  expertName = java.net.URLDecoder.decode(getModel().getName() , "UTF-8");
		    } catch (UnsupportedEncodingException e) {
			   e.printStackTrace();
		}
		if (getModel() == null || StringUtils.isBlank(getModel().getName())) {
			page = getManager().pageQuery(page, ("from Expert e"));
		} else {
			page = getManager().pageQuery(page, ("from Expert e where e.name like ?"),
					   MatchMode.ANYWHERE.toMatchString(expertName));
		}
		// 得到专家实体集合
		List<Expert> allExperts = page.getData();
    // 存放已选择的其他类别的专家信息
		List<Expert> experts = new ArrayList();	
		for (Iterator otherItr = allExperts.iterator(); otherItr.hasNext();) {
			Expert expert = (Expert) otherItr.next();
			if (CollectionUtils.isNotEmpty(otherMembers)) {			
		    for (AsseMember otherAsseMember : otherMembers) {
					if (otherAsseMember.getExpert().equals(expert)) {
						experts.add(expert);
					} 
		    }
			}
		}
		//如果存在已选择的其他类别的专家集合，则从总的专家集合中将其剔除。
		if (CollectionUtils.isNotEmpty(experts)) {
			allExperts.removeAll(experts);	
		}
		
		/**
		 * 在所有专家实体集合中遍历，如果已经包含在当前评估对象专家成员的专家实体集合中，则让其成为选中状态
		 */	
		for (Iterator itr = allExperts.iterator(); itr.hasNext();) {
			Expert expert = (Expert) itr.next();
			expert.setChanged(false);
			
			if (CollectionUtils.isNotEmpty(members)) {
		    for (AsseMember asseMember : members) {
		      // 如果遍历到的专家已经包含在专家成员组中，则设定为选中
					if (asseMember.getExpert().equals(expert)) {
						expert.setChanged(true);
					}
				}
			}
			// 转换为Map，防止延迟加载
			Map map = ReflectUtil.toMap(expert, new String[] { "id", "name", "level", "mobile"}, true);
			map.put("expertCategory", expert.getExpertCategory().getName());
			map.put("changed", expert.getChanged());
			mapMembers.add(map);
		}
		page.setData(mapMembers);

		return JSON;
	}
	
	/**
	 * 
	 * @return
	 */
	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public String getExpertType() {
		expertType = String.valueOf(getRequest().getSession().getAttribute("expertType"));
		return expertType;
	}

	public void setExpertType(String expertType) {
		this.expertType = expertType;
	}
	
}
