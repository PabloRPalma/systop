package com.systop.fsmis.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTestCase;
import com.systop.fsmis.expert.service.CategoryManager;
import com.systop.fsmis.model.ExpertCategory;
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class CategoryManagerTest extends BaseTestCase {

	@Autowired
	private CategoryManager categoryManager;
	private ExpertCategory expertCategory;
	public void testSave(){
		expertCategory = new ExpertCategory();
		expertCategory.setName("计算机");
		expertCategory.setDescn("计算机专家");
		expertCategory.setExperts(null);
		categoryManager.save(expertCategory);
	}
}
