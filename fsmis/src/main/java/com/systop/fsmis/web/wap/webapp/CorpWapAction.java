package com.systop.fsmis.web.wap.webapp;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.web.wap.service.CorpWapManager;

/**
 * WAP企业信息管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CorpWapAction extends DefaultCrudAction<Corp, CorpWapManager>{
	
	/** pageCompany 企业信息列表当前页索引 ,初始化为0 */
	private int pageCompany = 0;
	
	/** pageSize 页容量 */
	private static final int pageSize = 5;
	
	/** pageCount 数据页总数 */
	private int pageCount = 0;
	
	/** beginIndex 起始索引 */
	private int beginIndex = 0;

	/** queryCorpName 企业查询关键字 */
	private String queryCorpName;
	
	/** 保存企业列表分页信息 */
	private List companylist;
	
	/** 企业编号 */
	private Integer companyId;
	
	/** 企业信息 */
	private Map company;
	
	/**
	 * 企业信息查询列表
	 */
	@Override
	public String index() {
		companylist = getManager().getAllCorps(StringUtils.trim(queryCorpName),
				pageSize, pageCompany);
		int companyCount = getManager().getCorpCount(queryCorpName);
		pageCount = companyCount % pageSize == 0 ? companyCount / pageSize
				: companyCount / pageSize + 1;// 计算总页数
		beginIndex = pageSize*pageCompany + 1;//计算起始索引	
		return INDEX;
	}
	
	/**
	 * 查看企业信息
	 */
	@Override
	public String view() {
		company = getManager().viewCorp(companyId);
		return super.view();
	}
	
	/**
	 * 获取每页数据量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获得页总数
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 获得当前页索引
	 */
	public int getPageCompany() {
		return pageCompany;
	}

	/**
	 * 设置当前页索引
	 */
	public void setPageCompany(int pageCompany) {
		this.pageCompany = pageCompany;
	}

	/**
	 * 获得企业查询结果
	 */
	public List getCompanylist() {
		return companylist;
	}

	/**
	 * 获得企业名称检索关键字
	 */
	public String getQueryCorpName() {
		return queryCorpName;
	}

	/**
	 * 设置企业名称检索关键字
	 */
	public void setQueryCorpName(String queryCorpName) {
		this.queryCorpName = queryCorpName;
	}

	public Map getCompany() {
		return company;
	}

	public Integer getCompanyId() {
  	return companyId;
  }
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
}
