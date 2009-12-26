package com.systop.fsmis.expert.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.expert.ExpertConstants;
import com.systop.fsmis.expert.service.ExpertManager;
import com.systop.fsmis.model.Expert;
import com.systop.fsmis.model.ExpertCategory;

/**
 * 专家信息管理Action
 * @author ShangHua
 *
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpertAction extends ExtJsCrudAction<Expert, ExpertManager> {
	/** 
	 * 照片
	 */
	private File photo;
	/** 
	 * 照片存储名称
	 */
	private String photoFileName;
	/**
	 * 专家ID
	 */
	private Integer expertId;
	/**
	 * Json返回结果
	 */
	private Map<String, String> delResult;
	
	/**
	 * 重写父类的index方法，实现分页检索风险评估信息
	 */
	@Override
	public String index() {
		StringBuffer hql = new StringBuffer();
		hql.append("from Expert e where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql.append(" and e.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		if (StringUtils.isNotBlank(getModel().getLevel())) {
			hql.append(" and e.level = ?");
			args.add(getModel().getLevel());
		}
		if (getModel().getExpertCategory() != null && getModel().getExpertCategory().getId() != null) {
			hql.append(" and e.expertCategory.id = ?");
			args.add(getModel().getExpertCategory().getId());
		}		
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return INDEX;
	}
	
	/**
	 * 保存专家信息
	 */
	@Override
	public String save() {
		try {
			if (photo != null) {
				String fileRelativePath = null;
				fileRelativePath = UpLoadUtil.doUpload(photo, photoFileName,
						FsConstants.COMPANY_PHOTOS_FOLDER, getServletContext(), true);
				logger.info("photo path:{}", fileRelativePath);
				getModel().setPhotoPath(fileRelativePath);	
			}
			//设置专家类别
			if (getModel().getExpertCategory() != null) {
				ExpertCategory expertCategory = getManager().getDao().get(ExpertCategory.class, getModel().getExpertCategory().getId());
				getModel().setExpertCategory(expertCategory);	
			}	
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	/**
	 * 删除专家信息
	 */
	@Override
	public String remove(){
		Expert expert = getManager().get(getModel().getId());
		//如果存在照片，则连照片一起删除
		String Path = getModel().getPhotoPath();
		if(StringUtils.isNotBlank(Path)){
			getManager().remove(expert, getRealPath(Path));
			return SUCCESS;
		}
		return super.remove();
	}
	
	/**
	 * 删除专家照片
	 */
	public String deletePhoto() {
		delResult = Collections.synchronizedMap(new HashMap<String, String>());
		// 对于编辑专家信息的情况，处理删除原照片的请求
		if (expertId != null) {
			Expert expert = getManager().get(expertId);
			String relativePath = expert.getPhotoPath();
			if (StringUtils.isNotEmpty(relativePath)) {
				File file = new File(getServletContext().getRealPath(relativePath));
				if (file.exists()) {
					file.delete();
				}
			}
			// 删除数据库中企业照片的路径
			expert.setPhotoPath(null);
			getManager().save(expert);
			delResult.put("result", "success");
		} else {
			delResult.put("result", "error");
		}
		return "jsonRst";
	}
	
  /**
   * 返回专家级别集合
   * @return list
   */
  public Map<String, String> getExpertLevels() {
    return ExpertConstants.EXPERT_LEVELS;
  }
  
  /**
   * 返回专家类别集合
   * @return list
   */
  public List<Map> getExpertCateList() {
    List list = Collections.EMPTY_LIST;
    list = getManager().getExpertCategory();
    return list;
  }

  /**
   * 
   * @return
   */
	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public Integer getExpertId() {
		return expertId;
	}

	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}

	public Map<String, String> getDelResult() {
		return delResult;
	}

	public void setDelResult(Map<String, String> delResult) {
		this.delResult = delResult;
	}

}
