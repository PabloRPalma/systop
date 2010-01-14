package com.systop.fsmis.fscase.gather.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.GatherConfiger;

/**
 * 汇总单体事件生成多体事件
 * 
 * @author ZW
 * 
 */
@Service
public class GatherFsCaseManager extends BaseGenericsManager<FsCase> {

	/**
	 * 汇总单体事件，创建多体事件
	 * 
	 * @param caseTypeId        事件类别
	 * @param loginUserCounty   事件所属机构
	 */
	@Transactional
	public void gatherFscase(Integer caseTypeId, Dept country) {
		// 处理市级的多体事件汇总
		gatherCity(caseTypeId);
		// 处理区县级的多体事件汇总
		gatherCounty(caseTypeId, country);


	}
	
	/**
	 * 处理多体事件汇总--区县级
	 * 
	 * @param caseTypeId         事件类别
	 * @param country            事件所属部门
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void gatherCounty(Integer caseTypeId, Dept country) {
		// 得到相应的汇总配置条件--区县级
		List<GatherConfiger> confCountyList = getConfigerList(CaseConstants.COUNTY);
		if (confCountyList == null || CollectionUtils.isEmpty(confCountyList)) {
			return;
		}
		// 循环区县级汇总配置条件
		for (GatherConfiger configer : confCountyList) {
			// 查询单体事件个数
			int fsCaseCount = getFsCaseCount(caseTypeId, country, configer);
			List<FsCase> fsList = getFsCase(caseTypeId, country, configer);
			// 得到此汇总配置条件相关的多体事件
			StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
			List arg = new ArrayList();
			hql.append("fe.gatherConfiger.id = ? ");
			arg.add(configer.getId());
			hql.append("and fe.status <> ? ");
			arg.add(CaseConstants.CASE_UN_RESOLVE);
			hql.append("and fe.caseTime between ? and ?");
			arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configer.getDays()));
			List<FsCase> oldMCase = this.query(hql.toString(), arg.toArray());
			int oldCaseCount = oldMCase.size();
			
			logger.info("单体个数：{}  配置条件：{}", fsCaseCount, configer.getRecords());
			logger.info("多体事件的数量：{}", oldCaseCount);
			// 单体事件个数是否大于等于汇总配置条件设置的个数
			if (fsCaseCount - oldCaseCount >= configer.getRecords()) {
				// 查询相对应的多体事件，如果有则删除其
				List<FsCase> mFsCase = getMFsCase(caseTypeId, country, configer.getKeyWord());
				if (mFsCase != null) {
					for (FsCase mCase : mFsCase) {
						remove(mCase);
					}
				}
				// 新建多体事件，并与查出的单体事件建立关联
				FsCase cMFCase = new FsCase();
				cMFCase.setCaseType(getDao().get(CaseType.class, caseTypeId));
				for (FsCase fCase : fsList) {
					cMFCase.getGenericCases().add(fCase);
				}
				cMFCase.setIsMultiple(FsConstants.Y);
				cMFCase.setCounty(country);
				cMFCase.setGatherConfiger(configer);
				cMFCase.setCaseTime(fsList.get(0).getCaseTime());
				cMFCase.setIsSubmited(FsConstants.N);
				cMFCase.setStatus(CaseConstants.CASE_UN_RESOLVE);
				cMFCase.setTitle(fsCaseCount + "个单体事件" + "根据关键字" + "'"
						+ configer.getKeyWord() + "'" + "自动汇总成 " + cMFCase.getCounty().getName() 
						+ " 多体事件");
				getDao().save(cMFCase);
			}
		}
	}
	
	/**
	 * 处理多体事件汇总--市级
	 * 
	 * @param caseTypeId      事件类别
	 */
	@Transactional
	public void gatherCity(Integer caseTypeId) {
		List<GatherConfiger> confCity = getConfigerList(CaseConstants.CITY);
		if (confCity == null || CollectionUtils.isEmpty(confCity)) {
			return;
		}
		// 循环市级汇总配置条件
		for (GatherConfiger configerCity : confCity) {
			// 查询单体事件个数
			int fsCaseCityCount = getFsCaseCityCount(caseTypeId, configerCity);
			List<FsCase> fsCityList = getCityFsCase(caseTypeId, configerCity);
			logger.info("市单体个数：{}  市配置条件：{}", fsCaseCityCount, configerCity
					.getRecords());
			// 单体事件个数是否大于等于汇总配置条件设置的个数
			if (fsCaseCityCount >= configerCity.getRecords()) {
				// 查询相对应的多体事件，如果有则删除其
				Dept city = (Dept) getDao().findObject("from Dept d where d.parentDept.id is null ");
				List<FsCase> mFsCase = getMFsCase(caseTypeId, city, configerCity.getKeyWord());
				if (mFsCase != null) {
					for (FsCase mCase : mFsCase) {
						remove(mCase);
					}
				}
				// 新建多体事件，并与查出的单体事件建立关联
				FsCase aMFCase = new FsCase();
				aMFCase.setCaseType(getDao().get(CaseType.class, caseTypeId));
				for (FsCase fCase : fsCityList) {
					aMFCase.getGenericCases().add(fCase);
				}
				aMFCase.setIsMultiple(FsConstants.Y);
				aMFCase.setCounty(city);
				aMFCase.setGatherConfiger(configerCity);
				aMFCase.setCaseTime(fsCityList.get(0).getCaseTime());
				aMFCase.setIsSubmited(FsConstants.N);
				aMFCase.setStatus(CaseConstants.CASE_UN_RESOLVE);
				aMFCase.setTitle(fsCaseCityCount + " 个单体事件" + "根据关键字" + "'"
						+ configerCity.getKeyWord() + "'" + "自动汇总成 " + city.getName() 
						+ " 多体事件"	);
				getDao().save(aMFCase);
			}
		}
	}
	
	/**
	 * 得到满足条件的单体事件的个数（不分区县）
	 * 
	 * @param caseTypeId
	 * @param configer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getFsCaseCityCount(Integer caseTypeId,
			GatherConfiger configerCity) {
		StringBuffer hql = new StringBuffer(
				"select count(*) from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		hql.append("and fe.title like ? ");
		arg.add(MatchMode.ANYWHERE.toMatchString(configerCity.getKeyWord()));
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configerCity
				.getDays()));
		arg.add(new Date());
		List list = getDao().query(hql.toString(), arg.toArray());
		return (list != null && list.size() > 0) ? Integer.valueOf(list.get(0)
				.toString()) : 0;
	}
	
	/**
	 * 得到满足条件的单体事件（不分区县）
	 * 
	 * @param caseTypeId
	 * @param configerCity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getCityFsCase(Integer caseTypeId,
			GatherConfiger configerCity) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		hql.append("and fe.title like ? ");
		arg.add(MatchMode.ANYWHERE.toMatchString(configerCity.getKeyWord()));
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configerCity
				.getDays()));
		arg.add(new Date());
		hql.append("order by fe.caseTime asc");
		List<FsCase> list = getDao().query(hql.toString(), arg.toArray());
		return list;
	}
	
	/**
	 * 得到满足条件的多体事件
	 * 
	 * @param caseTypeId     事件类别
	 * @param country        事件所属部门
	 * @param keyword        关键字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getMFsCase(Integer caseTypeId, Dept country, String keyWord) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List args = new ArrayList();
		hql.append("and fe.caseType.id = ? ");
		args.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		args.add(FsConstants.Y);
		hql.append("and fe.county.id = ? ");
		args.add(country.getId());
		hql.append("and fe.title like ? ");
		args.add(MatchMode.ANYWHERE.toMatchString(keyWord));
		hql.append("and fe.status = ? ");
		args.add(CaseConstants.CASE_UN_RESOLVE);
		List<FsCase> mFsList = query(hql.toString(), args.toArray());
		return (mFsList != null && mFsList.size() > 0) ? mFsList : null;
	}
	
	/**
	 * 得到满足条件的单体事件
	 * 
	 * @param caseTypeId        事件类别
	 * @param country           事件关联部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getFsCase(Integer caseTypeId, Dept country,
			GatherConfiger configer) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		hql.append("and fe.title like ? ");
		arg.add(MatchMode.ANYWHERE.toMatchString(configer.getKeyWord()));
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.county.id = ? ");
		arg.add(country.getId());
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configer
				.getDays()));
		arg.add(new Date());
		hql.append("order by fe.caseTime asc");
		List<FsCase> list = getDao().query(hql.toString(), arg.toArray());
		return list;
	}
	
	/**
	 * 得到满足条件的单体事件个数
	 * 
	 * @param caseTypeId      事件类别
	 * @param country         事件所属部门
	 * @param configer        配置汇总条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getFsCaseCount(Integer caseTypeId, Dept country,
			GatherConfiger configer) {
		StringBuffer hql = new StringBuffer(
				"select count(*) from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		hql.append("and fe.title like ? ");
		arg.add(MatchMode.ANYWHERE.toMatchString(configer.getKeyWord()));
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.county.id = ? ");
		arg.add(country.getId());
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configer
				.getDays()));
		arg.add(new Date());
		List list = getDao().query(hql.toString(), arg.toArray());
		return (list != null && list.size() > 0) ? Integer.valueOf(list.get(0)
				.toString()) : 0;
	}

	/**
	 * 根据用户所属机构，得到其相应的汇总配置条件
	 */
	@SuppressWarnings("unchecked")
	private List<GatherConfiger> getConfigerList(String flag) {
		String configerHql = "from GatherConfiger gc where gc.level = ?";
		List<GatherConfiger> configerList = getDao().query(configerHql, flag);
		return configerList;
	}
}
