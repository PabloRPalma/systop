package com.systop.fsmis.web.wap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.ReflectUtil;
import com.systop.fsmis.model.Corp;

/**
 * WAP企业信息管理类
 * @author DU
 *
 */
@Service
public class CorpWapManager extends BaseGenericsManager<Corp>{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 根据企业名称列出企业信息
	 * @param corpName 企业名称，支持模糊查询和分页查询
	 * @param pageSize 分页容量
	 * @param startIndex 起始行索引
	 */
	@SuppressWarnings("unchecked")
  public List getAllCorps(String corpName, int pageSize, int startIndex) {
		Page page = PageUtil.getPage(startIndex+1, pageSize);		
		List<Object> args = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from Corp c where 1=1");
		if (StringUtils.isNotBlank(corpName)) {
			hql.append(" and c.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(corpName));
		}
		hql.append(" order by c.name asc");
		page = getDao().pagedQuery(page, hql.toString(), args.toArray());
		
		return page.getData();
	}
	
	/**
	 * 根据企业名称查询符合条件的记录数量
	 * @param corpName 公司名称
	 * @return 符合条件的记录数量
	 */
	public int getCorpCount(String corpName) {
		String sql = "select count(*) from corps c where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(corpName)) {
			sql += " and c.name like ?";
			args.add(MatchMode.ANYWHERE.toMatchString(corpName));
		}
		return jdbcTemplate.queryForInt(sql, args.toArray());
	}
	
	/**
	 * 查看企业详情
	 * @param id 企业ID
	 */
	@SuppressWarnings("unchecked")
  public Map viewCorp(Integer id) {
		Map map = new HashMap();
		Corp corp = get(id);
		if (corp != null) {
      map = ReflectUtil.toMap(corp, null, true);
  	}
		return map;
	}
}
