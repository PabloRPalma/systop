package com.systop.fsmis.fscase.jointtask.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailManager;
import com.systop.fsmis.model.JointTaskDetail;

/**
 * 联合整治任务明细管理Action
 * @author ShangHua
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JointTaskDetailAction extends
		ExtJsCrudAction<JointTaskDetail, JointTaskDetailManager> {
	
	/**
	 * 联合整治任务Id
	 */
	private Integer jointTaskId;
	
	/**
	 * 重写父类的index方法，实现分页检索任务附件信息
	 */
	@Override
	public String index() {	
		if (jointTaskId != null) {
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			String hql = "from JointTaskDetail jtd where jtd.jointTask.id = ?";
			getManager().pageQuery(page, hql, jointTaskId);
			items = page.getData();
			restorePageData(page);
		}
		return INDEX;
	}
	
	
	/**
	 * 根据部门ID获得对应部门的任务明细，部门ID传参为model.dept.id
	 * @return
	 */
	public String deptTaskDetailIndex() {
		//获得当前登录用户
		User user = UserUtil.getPrincipal(getRequest());
		StringBuffer hql = new StringBuffer();
		hql.append("from JointTaskDetail jtd where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		//联合整治任务必须已审核
		hql.append(" and jtd.jointTask.status = ?");
		args.add(FsConstants.Y);
		//根据用户登录部门查询
		if((user.getDept() != null) && (user.getDept().getId() != null)){
			hql.append(" and jtd.dept.id = ?");
			args.add(user.getDept().getId());
		}
		//根据联合整治任务表的任务标题查询
		if(getModel().getJointTask() != null && StringUtils.isNotBlank(getModel().getJointTask().getTitle())){
			hql.append(" and jtd.jointTask.title like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getJointTask().getTitle()));
		}
		hql.append(" order by jtd.jointTask.createDate desc");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return "deptTaskDetailIndex";
	}
	
	/**
	 * 联合整治任务查看
	 */
	public String view(){
		if (getModel().getId() != null ){
			getManager().view(getModel());	
		}
		return "view";
	}
	
	/**
	 * 联合整治任务接收
	 */
	public String receive(){
		if(getModel().getId() != null ){
			getManager().receiveTask(getModel());
		}
		return SUCCESS;
	}
	
	/**
	 * 联合整治任务处理
	 */
	public String result(){
		return "result";
	}
	
	/**
	 * 联合整治任务处理结果保存
	 */
	public String resultSave(){
		if (getModel().getId() != null ) {
			getModel().setInputer(UserUtil.getPrincipal(getRequest()));
		  getManager().saveResult(getModel());
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getJointTaskId() {
		return jointTaskId;
	}

	public void setJointTaskId(Integer jointTaskId) {
		this.jointTaskId = jointTaskId;
	}
	
}
