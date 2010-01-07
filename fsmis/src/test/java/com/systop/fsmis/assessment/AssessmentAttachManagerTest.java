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
	
	/**
	 * 测试移除附件方法
	 * 新增附件后保存
	 * 验证是否可成功删除新增实体和文件
	 */
	public void testremoveAttach() throws IOException{
		//新增一条附件记录
		AssessmentAttach assessmentAttach = new AssessmentAttach();
		assessmentAttach.setTitle("title");
		assessmentAttachManager.save(assessmentAttach);
		
		//创建一磁盘文件，作为测试附件
		File file = new File("c:/test.jpg");
		file.createNewFile();
		
		//移除附件
		assessmentAttachManager.removeAttach(assessmentAttach, "c:/test.jpg");
	}
}
