package com.systop.fsmis.assessment;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.assessment.service.AssessmentAttachManager;
import com.systop.fsmis.model.AssessmentAttach;
/**
 * 评估附件管理测试类
 * @author zzg
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class AssessmentAttachManagerTest extends BaseTransactionalTestCase {
	//注入评估附件管理类
	@Autowired
	private AssessmentAttachManager assessmentAttachManager;
	//测试移除附件方法
	public void testremoveAttach() throws IOException{
		AssessmentAttach assessmentAttach = new AssessmentAttach();
		assessmentAttach.setTitle("title");
		assessmentAttachManager.save(assessmentAttach);
		
		File file = new File("c:/test.jpg");
		file.createNewFile();
		
		assertEquals("title", (assessmentAttachManager.get(assessmentAttach.getId()).getTitle()));
		
		assessmentAttachManager.removeAttach(assessmentAttach, "c:/test.jpg");
	}
}
