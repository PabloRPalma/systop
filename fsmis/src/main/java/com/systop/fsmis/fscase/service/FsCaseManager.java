package com.systop.fsmis.fscase.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.GatherConfiger;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SmsSendManager;

//FIXME:这个注释?...不再是一般的事件了。
/**
 * 一般事件
 * 
 * @author shaozhiyuan
 */
@Service
public class FsCaseManager extends BaseGenericsManager<FsCase> {

	@Autowired
	private SmsSendManager sesSendManager;

	/**
	 * 根据单体事件的编号查询事件的详情
	 * 
	 * @param code
	 *          单体事件编号
	 * @return 对应的单体事件
	 */
	public FsCase getGenericCaseByCode(String code) {
		return findObject("from FsCase g where g.code =?", code);
	}

	/**
	 * 给信息员发送信息核实事件
	 * 
	 * @param fsCase
	 *          事件信息
	 * @param name
	 *          接收人姓名
	 * @param moblie
	 *          接收人手机号
	 * @param msgContent
	 *          短信内容
	 */
	@Transactional
	public void sendMsg(FsCase fsCase, String name, String moblie,
			String msgContent) {
		SmsSend smsSend = new SmsSend();
		// 定义短信发送内容，组织短信发送内容
		StringBuffer content = new StringBuffer();
		content.append("ID:").append(fsCase.getId()).append("\\n");
		content.append(msgContent);
		smsSend.setName(name);
		smsSend.setMobileNum(moblie);
		smsSend.setContent(content.toString());
		sesSendManager.addMessage(smsSend);
	}
	
	/**
	 * 汇总单体事件，创建多体事件
	 * 
	 * @param caseTypeId
	 *            事件类别
	 * @param loginUserCounty
	 *            事件所属机构
	 */
	@Transactional
	public void gatherFscase(Integer caseTypeId, Dept country) {
		logger.info("多体事件汇总进来了");
		// 处理市级的多体事件汇总
		gatherCity(caseTypeId);
		// 处理区县级的多体事件汇总
		gatherCounty(caseTypeId, country);
				
		
	}
	
	/**
	 * 处理多体事件汇总--区县级
	 * @param caseTypeId 事件类别
	 * @param country 事件所属部门
	 */
	@Transactional
	private void gatherCounty(Integer caseTypeId, Dept country) {
		// 得到相应的汇总配置条件--区县级
		GatherConfiger configer = getConfigerList(CaseConstants.COUNTY);
		if (configer == null) {
			return;
		}

		// 查询单体事件个数
		int fsCaseCount = getFsCaseCount(caseTypeId, country, configer);
		List<FsCase> fsList = getFsCase(caseTypeId, country, configer);
		logger.info("单体个数：{}  配置条件：{}", fsCaseCount, configer.getRecords());
		// 单体事件个数是否大于等于汇总配置条件设置的个数
		if (fsCaseCount >= configer.getRecords()) {
			// 查询相对应的多体事件，如果有则删除其
			List<FsCase> mFsCase = getMFsCase(caseTypeId, country);
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
			cMFCase.setIsSubmited(FsConstants.N);
			cMFCase.setStatus(CaseConstants.CASE_UN_RESOLVE);
			cMFCase.setTitle("单体事件自动汇总的多体事件"+cMFCase.getCounty().getName());
			getDao().save(cMFCase);
		}
	}

	/**
	 * 处理多体事件汇总--市级
	 * @param caseTypeId 事件类别
	 */
	@Transactional
	public void gatherCity(Integer caseTypeId) {
		GatherConfiger configerCity = getConfigerList(CaseConstants.CITY);
		if (configerCity == null) {
			return;
		}
		// 查询单体事件个数
		int fsCaseCityCount = getFsCaseCityCount(caseTypeId, configerCity);
		List<FsCase> fsCityList = getCityFsCase(caseTypeId, configerCity);
		logger.info("市单体个数：{}  市配置条件：{}", fsCaseCityCount, configerCity.getRecords());
		// 单体事件个数是否大于等于汇总配置条件设置的个数
		if (fsCaseCityCount >= configerCity.getRecords()) {
			// 查询相对应的多体事件，如果有则删除其
			List<FsCase> mFsCase = getCityMFsCase(caseTypeId);
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
			Dept city = (Dept) getDao().findObject("from Dept d where d.parentDept.id is null ");
			aMFCase.setCounty(city);
			aMFCase.setIsSubmited(FsConstants.N);
			aMFCase.setStatus(CaseConstants.CASE_UN_RESOLVE);
			aMFCase.setTitle("单体事件自动汇总的多体事件" + city.getName());
			getDao().save(aMFCase);
		}
	}
	
	/**
	 * 得到满足条件的多体事件（不分区县）
	 * @param caseTypeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getCityMFsCase(Integer caseTypeId) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List args = new ArrayList();
		hql.append("and fe.caseType.id = ? ");
		args.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		args.add(FsConstants.Y);
		hql.append("and fe.status = ? ");
		args.add(CaseConstants.CASE_UN_RESOLVE);
		List<FsCase> mFsList = query(hql.toString(), args.toArray());
		return (mFsList != null && mFsList.size() > 0) ? mFsList : null;
	}

	/**
	 * 得到满足条件的单体事件（不分区县）
	 * @param caseTypeId
	 * @param configerCity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getCityFsCase(Integer caseTypeId,
			GatherConfiger configerCity) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
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
		List<FsCase> list = getDao().query(hql.toString(), arg.toArray());
		return list;
	}

	/**
	 * 得到满足条件的单体事件的个数（不分区县）
	 * @param caseTypeId
	 * @param configer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getFsCaseCityCount(Integer caseTypeId, GatherConfiger configerCity) {
		StringBuffer hql = new StringBuffer("select count(*) from FsCase fe where 1=1 ");
		List arg = new ArrayList();
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
	 * 得到满足条件的单体事件
	 * 
	 * @param caseTypeId      事件类别
	 * @param country         事件关联部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getFsCase(Integer caseTypeId, Dept country, GatherConfiger configer) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List arg = new ArrayList();
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
		List<FsCase> list = getDao().query(hql.toString(), arg.toArray());
		return list;
	}

	/**
	 * 得到满足条件的多体事件
	 * 
	 * @param caseTypeId         事件类别
	 * @param country            事件所属部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FsCase> getMFsCase(Integer caseTypeId, Dept country) {
		StringBuffer hql = new StringBuffer("from FsCase fe where 1=1 ");
		List args = new ArrayList();
		hql.append("and fe.caseType.id = ? ");
		args.add(caseTypeId);
		hql.append("and fe.isMultiple = ? ");
		args.add(FsConstants.Y);
		hql.append("and fe.county.id = ? ");
		args.add(country.getId());
		hql.append("and fe.status = ? ");
		args.add(CaseConstants.CASE_UN_RESOLVE);
		List<FsCase> mFsList = query(hql.toString(), args.toArray());
		return (mFsList != null && mFsList.size() > 0) ? mFsList : null;
	}

	/**
	 * 得到满足条件的单体事件个数
	 * 
	 * @param caseTypeId         事件类别
	 * @param country            事件所属部门
	 * @param configer           配置汇总条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getFsCaseCount(Integer caseTypeId, Dept country,
			GatherConfiger configer) {
		StringBuffer hql = new StringBuffer(
				"select count(*) from FsCase fe where 1=1 ");
		List arg = new ArrayList();
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
	private GatherConfiger getConfigerList(String flag) {
		String configerHql = "from GatherConfiger gc where gc.level = ?";
		List<GatherConfiger> configerList = Collections.EMPTY_LIST;
		configerList = getDao().query(configerHql, flag);
		return (configerList != null && configerList.size() > 0) ? configerList
				.get(0) : null;
	}
}
