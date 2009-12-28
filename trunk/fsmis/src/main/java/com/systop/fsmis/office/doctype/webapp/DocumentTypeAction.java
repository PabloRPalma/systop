package com.systop.fsmis.office.doctype.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.model.DocumentType;
import com.systop.fsmis.office.doctype.service.DocumentTypeManager;

/**
 * 内部文章栏目管理的struts2 Action。
 * 
 * @author ZW
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DocumentTypeAction extends ExtJsCrudAction<DocumentType, DocumentTypeManager> {

	/**
	 * 查询栏目名称
	 */
	private String typeName;
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 栏目查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 设定查询条件
	 */
	private DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
		if(getModel().getId() != null) {
			criteria.add(Restrictions.eq("parentDocumentType.id", getModel().getId()));
		} else {
			criteria.add(Restrictions.isNull("parentDocumentType"));
		}
		if (StringUtils.isNotBlank(typeName)) {
			criteria.add(Restrictions.like("name", typeName, MatchMode.ANYWHERE));			
		}
		return criteria;
	}
	
	/**
	 * 保存栏目信息，其中栏目名称为必填
	 */
	@Override
	public String save() {
		try {
			if (getManager().getDao().exists(getModel(), "name")) {
				addActionError("无法添加，添加的栏目名称已存在！");
				return INPUT;
			}
			if (getModel().getParentDocumentType() != null
					&& getModel().getParentDocumentType().getId() != null) {
				getModel().setParentDocumentType(
						getManager().get(getModel().getParentDocumentType().getId()));
			} else {
				getModel().setParentDocumentType(null);
			}
			getManager().save(getModel());
			return SUCCESS;
		}catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	public String view() {
		return "view";
	}
	
	/**
	 * 获取栏目列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDocumentTypeMap() {
		return getManager().getDocumentTypesList(0);
	}

	/**
	 * 删除栏目
	 */
	@Override
	public String remove() {
		if (!getModel().getDocuments().isEmpty()) {
			addActionError("该栏目有文章，无法删除，请先删除文章！");
			return "error";
		}
		if(!getModel().getChildDocumentTypes().isEmpty()){
			addActionError("该栏目有子栏目，无法删除，请先删除子栏目！");
			return "error";
		}
		return super.remove();
	}

	/**
	 * 查询栏目下的文章
	 */
	@SuppressWarnings("unchecked")
	public String indexArticles() {

		// 创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		String hql = "from Document d where d.documentType.id=?";
		List args = new ArrayList();
		args.add(getModel().getId());
		page = getManager().pageQuery(page, hql, args.toArray());
		items = page.getData();
		restorePageData(page);
		return "indexArticles";
	}
	
}
