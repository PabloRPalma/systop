package com.systop.fsmis.office.doc.webapp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.model.Document;
import com.systop.fsmis.model.DocumentType;
import com.systop.fsmis.office.doc.service.DocumentManager;
import com.systop.fsmis.office.doctype.service.DocumentTypeManager;

/**
 * 内部文章管理的struts2 Action。
 * 
 * @author ZW
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DocumentAction extends ExtJsCrudAction<Document, DocumentManager> {

	/** 栏目管理类 */
	@Autowired
	private DocumentTypeManager documentTypeManager;

	/**
	 * 按文章名称查询文章
	 */
	@Override
	public String index() {
		// 创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 设置查询条件
	 * 
	 * @return
	 */
	private DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class);
		criteria.createAlias("documentType", "documentType");
		if (getModel().getDocumentType() != null
				&& getModel().getDocumentType().getId() != null) {
			criteria.add(Restrictions.eq("documentType.id", getModel().getDocumentType().getId()));
		} 
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			criteria.add(Restrictions.like("title", MatchMode.ANYWHERE
					.toMatchString(getModel().getTitle())));			
		}
		return criteria;
	}

	/** 查看文章信息 */
	public String view() {
		return "view";
	}

	/** 获取栏目列表 */
	@SuppressWarnings("unchecked")
	public List getDocumentTypeMap() {
		return documentTypeManager.getDocumentTypesList(0, 0);
	}

	/**
	 * 保存内部文章
	 */
	@Override
	public String save() {
		if (getModel().getDocumentType() != null
				&& getModel().getDocumentType().getId() != null) {
			getModel().setDocumentType(
					getManager().getDao().get(DocumentType.class,
							getModel().getDocumentType().getId()));
		} else {
			addActionError("无法添加，请选择栏目！");
			return INPUT;
		}
		getManager().save(getModel());
		return SUCCESS;
	}
}
