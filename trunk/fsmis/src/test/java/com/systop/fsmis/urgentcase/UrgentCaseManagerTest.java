package com.systop.fsmis.urgentcase;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.systop.fsmis.sms.SmsSendManager;
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
	/**
	 * 短信发送管理类
	 */
	@Autowired
	private SmsSendManager smsSendManager;
	/**
	 * 所属区县
	 */
	private Dept county;
	/**
	 * 应急事件类别
	 */
	private UrgentType ut;
	/**
	 * 应急事件
	 */
	private UrgentCase uc;

	/**
	 * 准备数据
	 */
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		UrgentCase urgentCase = new UrgentCase();
		urgentCase.setCode("2010");
		county = deptManager.findObject("from Dept d where d.name like ? ",
				MatchMode.ANYWHERE.toMatchString("裕华区"));
		urgentCase.setCounty(county);
		urgentCaseManager.save(urgentCase);
		ut = ucTypeManager.findObject("from UrgentType ut where ut.name like ?",
				MatchMode.ANYWHERE.toMatchString("初级农产品环节"));
		uc = urgentCaseManager.findObject("from UrgentCase uc where uc.code =?",
				"2010");
		List<UrgentGroup> ugList = ucGroupManager.getDao().query(
				"from UrgentGroup ug where ug.isOriginal=? and ug.county.id=?",
				FsConstants.Y, county.getId());
		for (UrgentGroup ug : ugList) {
			ug.setMobel("13315123999");
			ucGroupManager.save(ug);
		}
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

		// 测试应急事件标识位
		assertEquals("1", urgentCaseManager.get(urgentCase.getId()).getStatus());
		assertEquals("1", urgentCaseManager.get(urgentCase.getId()).getIsAgree());
		assertEquals("不同意", urgentCaseManager.getDao().get(checkResult.getClass(),
				checkResult.getId()).getResult());
		assertEquals(urgentCase.getId(), urgentCaseManager.getDao().get(
				checkResult.getClass(), checkResult.getId()).getUrgentCase().getId());
	}

	/**
	 * 测试根据用户所在区县取得该区县下的所有派遣环节
	 */
	@SuppressWarnings("unchecked")
	public void testGetAllUcTypeByCounty() {
		List<UrgentType> ucTypes = Collections.EMPTY_LIST;
		ucTypes = urgentCaseManager.getAllUcTypeByCounty(county);
		assertTrue(ucTypes.size() > 0);
	}

	/**
	 * 测试应急事件任务派遣
	 */
	@SuppressWarnings("unchecked")
	public void testSendUrgentCase() {
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		// 测试应急事件生成各小组结果
		List<UrgentResult> urList = urgentCaseManager.getDao().query(
				"from UrgentResult  ur where ur.county.id=? and ur.urgentCase.id=? ",
				county.getId(), uc.getId());
		assertTrue(urList.size() > 0);
		// 测试应急事件重新生成组
		List<UrgentGroup> ugList = ucGroupManager.getDao().query(
				"from UrgentGroup ug where ug.isOriginal=? and ug.county.id=?",
				FsConstants.N, county.getId());
		assertTrue(ugList.size() > 0);
		// 测试应急事件标识位
		assertEquals(UcConstants.CASE_STATUS_SENDDED, urgentCaseManager.get(
				uc.getId()).getStatus());
	}

	/**
	 * 测试根据应急事件ID及区县ID取得该事件的派发情况 前提派遣事件
	 */
	@SuppressWarnings("unchecked")
	public void testQueryGroupResult() {
		// 派遣应急事件
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		List<UrgentResult> urList = ucGroupManager.getDao().query(
				"from UrgentResult ur where ur.county.id=?  and ur.urgentCase.id = ? ",
				county.getId(), uc.getId());
		assertTrue(urList.size() > 0);
	}

	/**
	 * 测试根据应急事件ID、区县ID及指挥组ID取得该事件的派发结果 前提派遣事件
	 */
	@SuppressWarnings("unchecked")
	public void testGetUrgentResultByIds() {
		// 派遣应急事件
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);

		Map contentMap = Collections.EMPTY_MAP;

		List<UrgentResult> urgentResults = urgentCaseManager.queryGroupResult(uc
				.getId(), county.getId());
		for (UrgentResult urgentRst : urgentResults) {
			contentMap = urgentCaseManager.getUrgentResultByIds(uc.getId(), county
					.getId(), urgentRst.getUrgentGroup().getId());
			System.out.println(contentMap.toString());
		}
	}

	/**
	 * 测试保存应急事件各个指挥组的处理结果 前提派遣事件
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testSaveGroupResult() {
		// 派遣应急事件
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		// 应急事件各小组结果
		List<UrgentResult> urgentResults = urgentCaseManager.queryGroupResult(uc
				.getId(), county.getId());
		for (UrgentResult urgentRst : urgentResults) {
			if (urgentRst.getUrgentGroup().getCategory().equals("Receive")) {
				// 未修改的结果
				System.out.println(urgentRst.getContent());
				// 修改
				urgentCaseManager.saveGroupResult(String.valueOf(uc.getId()), county,
						String.valueOf(urgentRst.getUrgentGroup().getId()), "a:b:c:d:e");
			}

		}

		List<UrgentResult> urList = urgentCaseManager.queryGroupResult(uc.getId(),
				county.getId());
		for (UrgentResult urgentRst : urList) {
			// 修改后结果
			System.out.println(urgentRst.getContent());
		}
	}

	/**
	 * 测试根据应急事件ID及区县ID取得该事件指挥组的处理结果集 前提派遣事件
	 */
	@SuppressWarnings("unchecked")
	public void testViewResultReports() {
		// 派遣应急事件
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		Map<String, Map> resultMap = new LinkedHashMap();
		resultMap = urgentCaseManager.viewResultReports(uc.getId(), county.getId());
		List<UrgentResult> resultList = urgentCaseManager.queryGroupResult(uc
				.getId(), county.getId());
		for (UrgentResult urgentResult : resultList) {
			if (StringUtils.isNotBlank(urgentResult.getContent())) {
				System.out.println(resultMap.get(urgentResult.getUrgentGroup()
						.getName()));
			}
		}
	}

	/**
	 * 测试取得应急事件的派遣情况对应的数据组 前提派遣事件
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testGetGroupIdsOfCase() {
		// 派遣应急事件
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		List<Integer> ugs = urgentCaseManager.getGroupIdsOfCase(uc.getId(), county
				.getId());
		assertTrue(ugs.size() > 0);
	}

	/**
	 * 测试删除应急事件的派遣情况对应的数据组
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testDelRroupOfCase() {
		List gourpIds = urgentCaseManager.getGroupIdsOfCase(uc.getId(), county
				.getId());
		Integer ucId = uc.getId();
		urgentCaseManager.remove(uc);
		urgentCaseManager.delRroupOfCase(gourpIds);
		assertEquals(0, urgentCaseManager.getGroupIdsOfCase(ucId, county.getId())
				.size());
	}

	/**
	 * 测试向事件派遣的相关组的负责人发送短信
	 */
	public void testSendSms() {
		urgentCaseManager.sendSms("13315123999;13273197023", "测试", county);
		assertEquals("13315123999", smsSendManager.findObject(
				"from SmsSend where content=?", "测试").getMobileNum());
	}

	/**
	 * 测试取得事件派发结果中指挥组的负责人与操作人的手机号码 以分号分隔的手机号码字符串
	 */
	public void testGetOperatorOfGroupForCase() {
		// 派遣应急事件
		urgentCaseManager.sendUrgentCase(String.valueOf(uc.getId()), String
				.valueOf(ut.getId()), county);
		assertEquals(
				"13315123999;13315123999;13315123999;13315123999;13315123999;13315123999;13315123999;13315123999;",
				urgentCaseManager.getOperatorOfGroupForCase(uc.getId(), county.getId()));
	}
}
