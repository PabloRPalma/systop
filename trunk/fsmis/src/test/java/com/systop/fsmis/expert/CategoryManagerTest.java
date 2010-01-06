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
	//测试保存方法
	public void testSave(){
		expertCategory = new ExpertCategory();
		expertCategory.setName("计算机");
		expertCategory.setDescn("计算机专家");
		expertCategory.setExperts(null);
		categoryManager.save(expertCategory);
		
		assertEquals("计算机", (categoryManager.get(expertCategory.getId())).getName());
		assertEquals("计算机专家", (categoryManager.get(expertCategory.getId())).getDescn());
	}
}
