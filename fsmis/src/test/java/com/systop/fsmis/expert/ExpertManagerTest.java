package com.systop.fsmis.expert;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTestCase;
import com.systop.fsmis.expert.service.ExpertManager;
import com.systop.fsmis.model.Expert;
import com.systop.fsmis.model.ExpertCategory;
/**
 * 专家管理测试类
 * @author zzg
 * 测试了sava 和 remove等方法，继承BaseTestCase不使用回滚
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ExpertManagerTest extends BaseTestCase {
	//专家
	private Expert expert;
	
	//专家类别
	private ExpertCategory expertCategory;
	
	//注入专家管理类
	@Autowired
	private ExpertManager expertManager;
	
	/**
	 * 测试保存方法
	 * 新增专家记录验证是否保存成功
	 */
	public void testSave(){
		//新增专家记录
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
		expertManager.save(expert);
		
		//验证是否保存成功
		assertEquals("张三", (expertManager.get(expert.getId())).getName());
		assertEquals("testExpert@sina.com", (expertManager.get(expert.getId())).getEmail());
	}
	
	/**
	 * 测试获取专家类别方法
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testGetExpertCategory(){
		//获取专家类别List
		List list = Collections.EMPTY_LIST;
		list = expertManager.getExpertCategory();
		
		//验证是否成功获取
		assertTrue(list.size() > 0);
	}
	
	/**
	 * 测试删除专家方法
	 * 
	 */
	public void testRemove() throws IOException{
		//获取一条专家记录
		expert = expertManager.findObject("from Expert where name = ?", "张三");
		
		//创建一个专家照片磁盘文件
		File file = new File("c:/test.jpg");
		file.createNewFile();
		
		//删除专家
		expertManager.remove(expert, "c:/test.jpg");
	}
}
