package com.systop.fsmis.expert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTestCase;
import com.systop.fsmis.expert.service.ExpertManager;
import com.systop.fsmis.model.Expert;
import com.systop.fsmis.model.ExpertCategory;
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ExpertManagerTest extends BaseTestCase {

	private Expert expert;
	private ExpertCategory expertCategory;
	@Autowired
	private ExpertManager expertManager;
	
	public void testSave(){
		expert = new Expert();
		expert.setName("张三");
		expert.setBirthDate(new Date());
		expert.setEmail("testExpert@sina.com");
		expert.setDegree("学士");
		expert.setExpertCategory(expertCategory);
		expert.setGraduateSchool("石家庄职业技术学院");
		expert.setHomePhone("0311-86681177");
		expert.setLevel("一级");
		expert.setUnits("石家庄职业技术学院");
		expert.setTitle("教授");
		expert.setSummery("张三出生在一个贫穷的家庭，自幼父母双亡，是奶奶靠捡破烂把他养大的");
		expert.setSchoolRecord("石家庄职业技术学院");
		expert.setRemark("无");
		expert.setPosition("石家庄职业技术学院");
		expert.setOfficePhone("13275896253");
		expert.setMobile("12334321");
		expertManager.save(expert);
		
		assertEquals("张三", (expertManager.get(expert.getId())).getName());
		assertEquals("testExpert@sina.com", (expertManager.get(expert.getId())).getEmail());
	}
	
	@SuppressWarnings("unchecked")
	public void testGetExpertCategory(){
		List list = Collections.EMPTY_LIST;
		list = expertManager.getExpertCategory();
		
		assertTrue(list.size() > 0);
	}
	
	public void testRemove(){
		expert = expertManager.findObject("from Expert where name = ?", "张三");
		expertManager.remove(expert, "path");
	}
}
