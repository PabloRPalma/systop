package com.systop.fsmis.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.FsCase;
/**
 * 评估信息管理测试类
 * @author zzg
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class AssessmentManagerTest extends BaseTransactionalTestCase {
	//注入评估信息管理类
	@Autowired
	private AssessmentManager assessmentManager;
	//注入一般案件管理类
	@Autowired
	private FsCaseManager fsCaseManager;
	//测试得到所有事件标题方法
	public void testgetAllTitles() {
		FsCase fsCase = new FsCase();
		fsCase.setTitle("case title");
		fsCaseManager.save(fsCase);

		String[] titles = assessmentManager.getAllTitles();
		assertTrue(titles.length > 0);
	}
	//测试保存风险评估审核信息方法
	public void testauditSave() {
		Assessment assessment = new Assessment();
		assessment.setState("0");
		assessmentManager.save(assessment);

		CheckResult checkResult = new CheckResult();
		checkResult.setIsAgree("0");
		checkResult.setResult("result");
		checkResult.setAssessment(assessment);

		assessmentManager.auditSave(checkResult);
		
		String hql = "from CheckResult where result = 'result'";
		CheckResult cR = (CheckResult)assessmentManager.getDao().findObject(hql);
		
		assertEquals("result", cR.getResult());
	}
}
