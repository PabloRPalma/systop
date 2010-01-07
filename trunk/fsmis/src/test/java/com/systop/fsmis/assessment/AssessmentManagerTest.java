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
	
	/**
	 * 测试得到所有事件标题方法
	 * 新增事件后保存
	 * 验证得到的事件标题数组大小是否大于零
	 */
	public void testgetAllTitles() {
		//新增事件后保存
		FsCase fsCase = new FsCase();
		fsCase.setTitle("case title");
		fsCaseManager.save(fsCase);

		//获取所有事件标题
		String[] titles = assessmentManager.getAllTitles();
		
		//验证得到的事件标题数组大小是否大于零
		assertTrue(titles.length > 0);
	}
	
	/**
	 * 测试保存风险评估审核信息方法
	 * 新增评估、审核记录后保存
	 * 验证是否保存成功
	 */
	public void testauditSave() {
		//新增一条评估记录
		Assessment assessment = new Assessment();
		assessment.setState("0");
		assessmentManager.save(assessment);

		//新增一条审核记录
		CheckResult checkResult = new CheckResult();
		checkResult.setIsAgree("0");
		checkResult.setResult("result");
		checkResult.setAssessment(assessment);

		//保存风险评估审核信息
		assessmentManager.auditSave(checkResult);
		
		//验证是否保存成功
		String hql = "from CheckResult where result = 'result'";
		CheckResult cR = (CheckResult)assessmentManager.getDao().findObject(hql);
		assertEquals("result", cR.getResult());
	}
}
