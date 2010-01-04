package com.systop.fsmis.urgentcase;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.UrgentCase;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentResult;
import com.systop.fsmis.model.UrgentType;
import com.systop.fsmis.urgentcase.service.UrgentCaseManager;
import com.systop.fsmis.urgentcase.ucgroup.service.UcGroupManager;
import com.systop.fsmis.urgentcase.uctype.service.UcTypeManager;

/**
 * 应急测试
 * 
 * @author yj
 * 
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class UrgentCaseManagerTest extends BaseTransactionalTestCase {
	/**
	 * 应急事件管理
	 */
	@Autowired
	private UrgentCaseManager urgentCaseManager;
	/**
	 * 部门管理
	 */
	@Autowired
	private DeptManager deptManager;
	/**
	 * 应急组管理
	 */
	@Autowired
	private UcGroupManager ucGroupManager;
	/**
	 * 应急类别管理
	 */
	@Autowired
	private UcTypeManager ucTypeManager;
	// 所属区县
	private Dept county;
	// 应急事件类别
	private UrgentType ut;
	// 应急事件
	private UrgentCase uc;

	protected void setUp() throws Exception {
		UrgentCase urgentCase = new UrgentCase();
		urgentCase.setCode("2010");
		county = deptManager.findObject("from Dept d where d.name like ? ",
				MatchMode.ANYWHERE.toMatchString("裕华区"));
		urgentCase.setCounty(county);
		urgentCaseManager.save(urgentCase);
		ut = ucTypeManager.findObject(
				"from UrgentType ut where ut.name like ?", MatchMode.ANYWHERE
						.toMatchString("初级农产品环节"));
		uc = urgentCaseManager.findObject(
				"from UrgentCase uc where uc.code =?", "2010");
	}

	/**
	 * 测试保存应急事件审核结果
	 */
	public void testSaveCheckResult() {
		UrgentCase urgentCase = new UrgentCase();
		urgentCase.setStatus("1");
		urgentCase.setIsAgree("1");

		CheckResult checkResult = new CheckResult();
		checkResult.setChecker(new User());
		checkResult.setCheckTime(new Date());
		checkResult.setIsAgree("1");
		checkResult.setResult("不同意");
		checkResult.setUrgentCase(urgentCase);

		urgentCaseManager.save(urgentCase);
		urgentCaseManager.getDao().save(checkResult);

		assertEquals("1", urgentCaseManager.get(urgentCase.getId()).getStatus());
		assertEquals("1", urgentCaseManager.get(urgentCase.getId())
				.getIsAgree());
		assertEquals("不同意", urgentCaseManager.getDao().get(
				checkResult.getClass(), checkResult.getId()).getResult());
		assertEquals(urgentCase.getId(), urgentCaseManager.getDao().get(
				checkResult.getClass(), checkResult.getId()).getUrgentCase()
				.getId());
	}

	/**
	 * 测试根据用户所在区县取得该区县下的所有派遣环节
	 */
	@SuppressWarnings("unchecked")
	public void testGetAllUcTypeByCounty() {
		List<UrgentType> ucTypes = Collections.EMPTY_LIST;
		ucTypes = urgentCaseManager.getAllUcTypeByCounty(county);
		for (Iterator<UrgentType> itr = ucTypes.iterator(); itr.hasNext();) {
			UrgentType ut = itr.next();
			System.out.println(ut.getName());
		}
	}

	/**
	 * 测试应急事件任务派遣
	 */
	@SuppressWarnings("unchecked")
	public void testSendUrgentCase() {
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		List<UrgentResult> urList = urgentCaseManager.getDao().query(
						"from UrgentResult  ur where ur.county.id=? and ur.urgentCase.id=? ",
						county.getId(), uc.getId());
		for (Iterator<UrgentResult> itr = urList.iterator(); itr.hasNext();) {
			UrgentResult ur = itr.next();
			System.out.println(ur.getUrgentGroup().getName() + ":"
					+ ur.getContent());
		}
		List<UrgentGroup> ugList = ucGroupManager.getDao().query(
				"from UrgentGroup ug where ug.isOriginal=? and ug.county.id=?",
				FsConstants.N, county.getId());
		for (Iterator<UrgentGroup> itr = ugList.iterator(); itr.hasNext();) {
			UrgentGroup ug = itr.next();
			System.out.println(ug.getName());
		}
		assertEquals(UcConstants.CASE_STATUS_SENDDED, urgentCaseManager.get(
				uc.getId()).getStatus());
	}
	/**
	 * 测试根据应急事件ID及区县ID取得该事件的派发情况
	 */
	@SuppressWarnings("unchecked")
	public void testQueryGroupResult() {
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		List<UrgentResult> urList = ucGroupManager.getDao().query(
						"from UrgentResult ur where ur.county.id=?  and ur.urgentCase.id = ? ",
						county.getId(), uc.getId());
		for (Iterator<UrgentResult> itr = urList.iterator(); itr.hasNext();) {
			UrgentResult ur = itr.next();
			System.out.println(ur.getUrgentGroup().getName() + ":"
					+ ur.getContent());
		}
	}

	/**
	 * 测试根据应急事件ID、区县ID及指挥组ID取得该事件的派发结果
	 */
	@SuppressWarnings("unchecked")
	public void testGetUrgentResultByIds() {
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);

		Map contentMap = Collections.EMPTY_MAP;

		UrgentGroup ug = ucGroupManager.findObject(
						"from UrgentGroup ug where ug.isOriginal=? and ug.county.id=? and ug.name like ?",
						FsConstants.N, county.getId(), MatchMode.ANYWHERE
								.toMatchString("接待"));
		contentMap = urgentCaseManager.getUrgentResultByIds(uc.getId(), county
				.getId(), ug.getId());
		System.out.println("map长度" + contentMap.size());

	}

}
