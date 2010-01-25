package com.systop.fsmis.fscase.gather.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.xwork.StringUtils;
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
	 * @param caseTypeId          事件类别
	 * @param loginUserCounty     事件所属机构
	 */
	@Transactional
	public void gatherFscase(Integer caseTypeId, Dept country) {
		// 处理市级的多体事件汇总
		gatherCity(caseTypeId);
		// 处理区县级的多体事件汇总
		gatherCounty(caseTypeId, country);

	}
	
	/**
	 * 处理多体事件汇总--市级
	 * 
	 * @param caseTypeId     事件类别
	 */
	@Transactional
	public void gatherCity(Integer caseTypeId) {
		List<GatherConfiger> confCity = getConfigerList(CaseConstants.CITY);
		if (confCity == null || CollectionUtils.isEmpty(confCity)) {
			return;
		}
		Dept city = (Dept) getDao().findObject("from Dept d where d.parentDept.id is null");
		// 循环市级汇总配置条件
		for (GatherConfiger configerCity : confCity) {
			
			// 查询单体事件个数
			int fsCaseCityCount = getFsCaseCityCount(caseTypeId, configerCity);
			List<FsCase> fsCityList = getCityFsCase(caseTypeId, configerCity);
			
			// 得到此汇总配置条件相关的,状态不为0的多体事件
			List<FsCase> oldMCase = this.getConfFsCases(configerCity, city);
			// 多体事件对应的单体事件个数
			int oldCaseCount = 0;
			if (oldMCase != null && !CollectionUtils.isEmpty(oldMCase)) {
				for (FsCase fcase : oldMCase) {
					oldCaseCount += fcase.getGenericCases().size();
				}
			}
			logger.info("市单体个数：{}  市配置条件：{}", fsCaseCityCount, configerCity
					.getRecords());
			logger.info("市级多体事件的数量：{}", oldCaseCount);
			// 单体事件个数是否大于等于汇总配置条件设置的个数
			if (fsCaseCityCount - oldCaseCount >= configerCity.getRecords()) {
				// 查询相对应的多体事件，如果有则删除其
				List<FsCase> mFsCase = getMFsCase(caseTypeId, city,
						configerCity);
				if (mFsCase != null) {
					for (FsCase mCase : mFsCase) {
						remove(mCase);
					}
				}
				// 得到状态不为0的多体事件关联单体事件与符合条件的单体事件之差
				List<FsCase> newFsCase = reject(oldMCase, fsCityList);
				// 新建多体事件，并与查出的单体事件建立关联
				FsCase aMFCase = new FsCase();
				aMFCase.setCaseType(getDao().get(CaseType.class, caseTypeId));
				logger.info("市级差集合数量：{}", newFsCase.size());
				//不为空时，未包含入多体事件的单体事件与多体建立关联
				if (CollectionUtils.isEmpty(newFsCase)) {
					for (FsCase fCase : fsCityList) {
						aMFCase.getGenericCases().add(fCase);
					}
				} else {
					for (FsCase fCase : newFsCase) {
						aMFCase.getGenericCases().add(fCase);
					}
				}
				aMFCase.setIsMultiple(FsConstants.Y);
				aMFCase.setCounty(city);
				aMFCase.setGatherConfiger(configerCity);
				aMFCase.setCaseTime(fsCityList.get(0).getCaseTime());
				aMFCase.setIsSubmited(FsConstants.N);
				aMFCase.setStatus(CaseConstants.CASE_UN_RESOLVE);
				aMFCase.setTitle(fsCaseCityCount - oldCaseCount + " 个单体事件" + "根据关键字" + " "
						+ configerCity.getKeyWord() + " " + "自动汇总成 "
						+ city.getName() + " 多体事件");
				getDao().save(aMFCase);
			}
		}
	}

	/**
	 * 处理多体事件汇总--区县级
	 * 
	 * @param caseTypeId         事件类别
	 * @param country            事件所属部门
	 */
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

			// 得到此汇总配置条件相关的,状态不为0的多体事件
			List<FsCase> oldMCase = this.getConfFsCases(configer, country);
			// 多体事件对应的单体事件个数
			int oldCaseCount = 0;
			if (oldMCase != null && !CollectionUtils.isEmpty(oldMCase)) {
				for (FsCase fcase : oldMCase) {
					oldCaseCount += fcase.getGenericCases().size();
				}
			}
			logger.info("单体个数：{}  配置条件：{}", fsCaseCount, configer.getRecords());
			logger.info("多体事件的数量：{}", oldCaseCount);
			// 单体事件个数是否大于等于汇总配置条件设置的个数
			if (fsCaseCount - oldCaseCount >= configer.getRecords()) {
				// 查询相对应的多体事件，如果有则删除其
				List<FsCase> mFsCase = getMFsCase(caseTypeId, country, configer);
				if (mFsCase != null) {
					for (FsCase mCase : mFsCase) {
						remove(mCase);
					}
				}
				// 得到状态不为0的多体事件关联单体事件与符合条件的单体事件之差
				List<FsCase> newFsCase = reject(oldMCase, fsList);
				// 新建多体事件，并与查出的单体事件建立关联
				FsCase cMFCase = new FsCase();
				cMFCase.setCaseType(getDao().get(CaseType.class, caseTypeId));
				logger.info("差集合数量：{}", newFsCase.size());
				if (CollectionUtils.isEmpty(newFsCase)) {
					for (FsCase fCase : fsList) {
						cMFCase.getGenericCases().add(fCase);
					}
				} else {
					for (FsCase fCase : newFsCase) {
						cMFCase.getGenericCases().add(fCase);
					}
				}
				cMFCase.setIsMultiple(FsConstants.Y);
				cMFCase.setCounty(country);
				cMFCase.setGatherConfiger(configer);
				cMFCase.setCaseTime(fsList.get(0).getCaseTime());
				cMFCase.setIsSubmited(FsConstants.N);
				cMFCase.setStatus(CaseConstants.CASE_UN_RESOLVE);
				cMFCase.setTitle(fsCaseCount - oldCaseCount + "个单体事件" + "根据关键字" + " "
						+ configer.getKeyWord() + " " + "自动汇总成 "
						+ cMFCase.getCounty().getName() + " 多体事件");
				getDao().save(cMFCase);
			}
		}
	}

	/**
	 * 从符合条件的单体事件中去掉符合条件的状态不为0的多体事件所关联的单位事件
	 * 
	 * @param oldMCase
	 * @param fsList
	 * @return
	 */
	private List<FsCase> reject(List<FsCase> oldMCase, List<FsCase> fsList) {
		List<FsCase> tempList = new ArrayList<FsCase>();
		//取多体事件中的单体事件
		for (FsCase oldCase : oldMCase) {
			tempList.addAll(oldCase.getGenericCases());
		}
		Set<FsCase> oldSet = new HashSet<FsCase>();
		Set<FsCase> fsSet = new HashSet<FsCase>();
		oldSet.addAll(tempList);
		fsSet.addAll(fsList);
		logger.info("Old: {} single:{}", oldSet.size(), fsSet.size());
		//求查出的单体事件与多体事件中包含的单体事件之差，找出未汇总的单体事件
		Set<FsCase> newSet = this.difference(fsSet, oldSet);
		List<FsCase> newFsList = new ArrayList<FsCase>();
		if (!CollectionUtils.isEmpty(newSet)) {
			newFsList.addAll(newSet);
		}
		return newFsList;
	}

	/** 
	 * 求差集 
	 */
	@SuppressWarnings("unchecked")
	private <T> Set<T> difference(Set<T> setA, Set<T> setB) {
		Set<T> setDifference;
		T item;

		if (setA instanceof TreeSet) {
			setDifference = new TreeSet<T>();
		} else {
			setDifference = new HashSet<T>();
		}

		// 判断一下集合的数量大小 再比较
		if (setA.size() > setB.size()) {
			Iterator<T> iterA = setA.iterator();
			while (iterA.hasNext()) {
				item = iterA.next();
				if (!setB.contains(item)) {
					setDifference.add(item);
				}
			}
		} else {
			Iterator<T> iterB = setB.iterator();
			while (iterB.hasNext()) {
				item = iterB.next();
				if (!setA.contains(item)) {
					setDifference.add(item);
				}
			}
		}
		return setDifference;
	}

	/**
	 * 得到符合汇总条件的状态不为0的多体事件
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getConfFsCases(GatherConfiger configer, Dept county) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		hql.append("and fe.gatherConfiger.id = ? ");
		arg.add(configer.getId());
		hql.append("and fe.status <> ? ");
		arg.add(CaseConstants.CASE_UN_RESOLVE);
		hql.append("and fe.county.id = ? ");
		arg.add(county.getId());
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.Y);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configer
				.getDays()));
		arg.add(new Date());
		List<FsCase> oldMCase = query(hql.toString(), arg.toArray());
		return oldMCase;
	}

	/**
	 * 得到满足条件的单体事件的个数（不分区县）
	 * 
	 * @param caseTypeId
	 * @param configer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getFsCaseCityCount(Integer caseTypeId, GatherConfiger configerCity) {
		StringBuffer hql = new StringBuffer("select count(*) from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		if(StringUtils.isNotBlank(configerCity.getKeyWord())) {
			hql.append("and fe.title like ? ");
			arg.add(MatchMode.ANYWHERE.toMatchString(configerCity.getKeyWord()));
		}
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configerCity.getDays()));
		arg.add(new Date());
		List list = query(hql.toString(), arg.toArray());
		return (list != null && list.size() > 0) ? Integer.valueOf(list.get(0).toString()) : 0;
	}

	/**
	 * 得到满足条件的单体事件（不分区县）
	 * 
	 * @param caseTypeId
	 * @param configerCity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getCityFsCase(Integer caseTypeId, GatherConfiger configerCity) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		if(StringUtils.isNotBlank(configerCity.getKeyWord())) {
			hql.append("and fe.title like ? ");
			arg.add(MatchMode.ANYWHERE.toMatchString(configerCity.getKeyWord()));
		}
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configerCity.getDays()));
		arg.add(new Date());
		hql.append("order by fe.caseTime asc");
		List<FsCase> list = query(hql.toString(), arg.toArray());
		return list;
	}

	/**
	 * 得到满足条件的多体事件
	 * 
	 * @param caseTypeId        事件类别
	 * @param country           事件所属部门
	 * @param keyword           关键字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getMFsCase(Integer caseTypeId, Dept country,
			GatherConfiger configer) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List args = new ArrayList();
		hql.append("and fe.caseType.id = ? ");
		args.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		args.add(FsConstants.Y);
		hql.append("and fe.county.id = ? ");
		args.add(country.getId());
		hql.append("and fe.gatherConfiger.id = ? ");
		args.add(configer.getId());
		hql.append("and fe.status = ? ");
		args.add(CaseConstants.CASE_UN_RESOLVE);
		List<FsCase> mFsList = query(hql.toString(), args.toArray());
		return (mFsList != null && mFsList.size() > 0) ? mFsList : null;
	}

	/**
	 * 得到满足条件的单体事件
	 * 
	 * @param caseTypeId         事件类别
	 * @param country            事件关联部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getFsCase(Integer caseTypeId, Dept country,
			GatherConfiger configer) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		if(StringUtils.isNotBlank(configer.getKeyWord())) {
			hql.append("and fe.title like ? ");
			arg.add(MatchMode.ANYWHERE.toMatchString(configer.getKeyWord()));
		}
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.county.id = ? ");
		arg.add(country.getId());
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configer.getDays()));
		arg.add(new Date());
		hql.append("order by fe.caseTime asc");
		List<FsCase> list = query(hql.toString(), arg.toArray());
		return list;
	}

	/**
	 * 得到满足条件的单体事件个数
	 * 
	 * @param caseTypeId        事件类别
	 * @param country           事件所属部门
	 * @param configer          配置汇总条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getFsCaseCount(Integer caseTypeId, Dept country,
			GatherConfiger configer) {
		StringBuffer hql = new StringBuffer("select count(*) from FsCase fe where 1=1 ");
		List arg = new ArrayList();
		if(StringUtils.isNotBlank(configer.getKeyWord())) {
			hql.append("and fe.title like ? ");
			arg.add(MatchMode.ANYWHERE.toMatchString(configer.getKeyWord()));
		}
		hql.append("and fe.status = ? ");
		arg.add(CaseConstants.CASE_CLOSED);
		hql.append("and fe.caseType.id = ? ");
		arg.add(caseTypeId);
		hql.append("and fe.county.id = ? ");
		arg.add(country.getId());
		hql.append("and fe.isMultiple = ? ");
		arg.add(FsConstants.N);
		hql.append("and fe.caseTime between ? and ?");
		arg.add(DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, -configer.getDays()));
		arg.add(new Date());
		List list = getDao().query(hql.toString(), arg.toArray());
		return (list != null && list.size() > 0) ? Integer.valueOf(list.get(0).toString()) : 0;
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
