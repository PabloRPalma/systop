package com.systop.fsmis.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.expert.service.CategoryManager;
import com.systop.fsmis.model.ExpertCategory;
/**
 * 专家类别管理类
 * @author zzg
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class CategoryManagerTest extends BaseTransactionalTestCase {
	//注入类别管理类
	@Autowired
	private CategoryManager categoryManager;
	
	private ExpertCategory expertCategory;
	/**
	 * 测试保存类别方法
	 * 设置描述属性后保存
	 * 验证是否成功保存
	 */
	public void testSave(){
		//新增一条专家类别记录
		expertCategory = new ExpertCategory();
		expertCategory.setDescn("计算机专家");
		categoryManager.save(expertCategory);
		
		//验证是否成功保存
		assertEquals("计算机专家", (categoryManager.get(expertCategory.getId())).getDescn());
	}
}
