package com.systop.fsmis.fscase.report;

import java.util.Date;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.corp.service.CorpManager;
import com.systop.fsmis.fscase.report.service.ReportManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.fscase.task.service.TaskManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskDetail;

/**
 * 部门上报管理测试类
 * @author DU
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ReportManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private ReportManager reportManager;
	
	@Autowired
	private FsCaseManager fsCaseManager;
	
	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private CorpManager corpManager;
	
	@Autowired
	private DeptManager deptManager;
	
	/**
	 * 所属区县
	 */
	private Dept county;
	
	/**
	 * 准备数据
	 */
	protected void setUp() throws Exception {
		county = deptManager.findObject("from Dept d where d.name like ? ",
				MatchMode.ANYWHERE.toMatchString("裕华区"));
	}
	
	/**
	 * 测试保存部门上报事件
	 */
	public void testSaveReportInfoOfCase() {		
		Corp corp = generateCorp("测试企业", "石家庄大学", county);
		corpManager.save(corp);
		
		FsCase fsCase1 = generateFsCase("事件1", county);
		Task task1 = generateTask("任务1");
		TaskDetail taskDetail1 = generateTaskDetail("成功的办他1");
		
		FsCase fsCase2 = generateFsCase("事件2", county);
		Task task2 = generateTask("任务2");
		TaskDetail taskDetail2 = generateTaskDetail("成功的办他2");
		
		reportManager.saveReportInfoOfCase(fsCase1, task1, taskDetail1, corp, "企业1");
		assertEquals("事件1", reportManager.get(fsCase1.getId()).getTitle());
		assertEquals("任务1", taskManager.get(task1.getId()).getTitle());
		Corp rstCorp = corpManager.findObject("from Corp c where c.name = ?", "企业1");
		assertNotNull(rstCorp);
		
		reportManager.saveReportInfoOfCase(fsCase2, task2, taskDetail2, corp, "测试企业");
		assertNotNull(reportManager.get(fsCase2.getId()));
		assertNotNull(taskManager.get(task2.getId()));
	}
	
	/**
	 * 测试删除上报事件及相关任务
	 */
	public void testRemoveCase() {
		FsCase fsCase = generateFsCase("测试事件", county);
		Task task = generateTask("测试任务");
		TaskDetail taskDetail = generateTaskDetail("测试任务处理结果");
		task.setFsCase(fsCase);
		taskDetail.setTask(task);
		fsCaseManager.save(fsCase);
		taskManager.save(task);
		taskManager.getDao().save(taskDetail);
		reportManager.removeCase(fsCase.getId());
		
		assertNull(fsCaseManager.get(fsCase.getId()));
	}
	
	/**
	 * 测试根据上报事件取得对应事件的任务信息
	 */
	public void testGetTaskOfCase() {
		FsCase fsCase = generateFsCase("测试事件取得", county);
		Task task = generateTask("测试任务取得");
		task.setFsCase(fsCase);
		fsCaseManager.save(fsCase);
		taskManager.save(task);
		
		assertNotNull(reportManager.getTaskOfCase(fsCase.getId()));
	}
	
	/**
	 * 测试根据任务ID取得该任务的详细信息
	 */
	public void testGetTaskDetailOfTask() {
		Task task = generateTask("测试任务及详细");
		TaskDetail taskDetail = generateTaskDetail("测试任务明细");
		taskDetail.setTask(task);
		taskDetail.setDept(county);
		taskManager.save(task);
		taskManager.getDao().save(taskDetail);
		TaskDetail taskDetailRst = reportManager.getTaskDetailOfTask(task.getId(), county.getId());
		
		assertNotNull(taskDetailRst);
	}
	
	/**
	 * 测试根据企业名称和区县ID取得企业信息
	 */
	@SuppressWarnings("unchecked")
  public void testGetCorpByName() {
		Corp corp = generateCorp("新龙公司", "时代方舟", county);
		corpManager.save(corp);
		Map corpRst = reportManager.getCorpMapByName("新龙公司", county.getId());
		
		assertNotNull(corpRst);
		assertEquals("新龙公司", corpRst.get("name"));
	}
	
	/**
	 * 测试根据区县ID取得该区县下所有的企业
	 */
	public void testGetCorpOfCounty() {
		Corp corp1 = generateCorp("新龙公司", "时代方舟A座", county);
		corp1.setBusinessLicense("2010011089");
		Corp corp2 = generateCorp("WIFI", "时代方舟B座", county);
		corpManager.save(corp1);
		corpManager.save(corp2);
		String[] corpRst = reportManager.getCorpOfCounty(county.getId());
		
		assertNotNull(corpRst);
		assertTrue(corpRst.length >= 2);
	}
	
	/**
	 * 生成事件测试数据
	 * @param name 名称
	 * @param county 区县
	 */
	private FsCase generateFsCase(String name, Dept county) {
		FsCase fsCase = new FsCase();
		fsCase.setTitle(name);
		fsCase.setAddress("石家庄裕华区");
		fsCase.setCaseTime(new Date());
		fsCase.setCounty(county);
		
		return fsCase;
	}
	
	/**
	 * 生成任务测试数据
	 * @param name 名称
	 */
	private Task generateTask(String name) {
		Task task = new Task();
		task.setTitle(name);
		task.setDescn("任务 "+name+" 描述");
		
		return task;
	}
	
	/**
	 * 生成任务详细信息测试数据
	 * @param result 处理结果
	 */
	private TaskDetail generateTaskDetail(String result) {
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setResult(result);
		taskDetail.setProcess(result);
		
		return taskDetail;
	}
	
	/**
	 * 生成企业测试数据
	 * @param name 名称
	 * @param address 地址
	 */
	private Corp generateCorp(String name, String address, Dept county) {
		Corp corp = new Corp();
		corp.setName(name);
		corp.setAddress(address);
		corp.setBusinessLicense("20100128");
		corp.setOperateDetails("综合");
		corp.setDept(county);
		
		return corp;
	}
}
