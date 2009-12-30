package com.systop.fsmis.urgentcase;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.UrgentCase;
import com.systop.fsmis.urgentcase.service.UrgentCaseManager;

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
		assertEquals("1", urgentCaseManager.get(urgentCase.getId()).getIsAgree());
		assertEquals("不同意", urgentCaseManager.getDao().get(checkResult.getClass(), checkResult.getId()).getResult());
		assertEquals(urgentCase.getId(), urgentCaseManager.getDao().get(checkResult.getClass(), checkResult.getId()).getUrgentCase().getId());
	}

}
