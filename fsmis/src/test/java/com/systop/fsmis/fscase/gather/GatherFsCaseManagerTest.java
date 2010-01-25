package com.systop.fsmis.fscase.gather;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.gather.service.GatherFsCaseManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.GatherConfiger;

/**
 * 多体事件汇总测试类
 * @author ZW
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class GatherFsCaseManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private FsCaseManager fsCaseManager;
	
	//注入事件manager
	@Autowired
	private GatherFsCaseManager gatherFsCaseManager;
	
	/**
	 * 测试多体事件汇总
	 */
	public void testGatherFscase() {
		//新建汇总配置条件
		GatherConfiger confCounty = new GatherConfiger();
		confCounty.setKeyWord("事件");
		confCounty.setDays(5);
		confCounty.setRecords(3);
		confCounty.setLevel(CaseConstants.COUNTY);
		gatherFsCaseManager.getDao().save(confCounty);
		
		//新建部门
		Dept yh = new Dept();
		yh.setName("裕华区");
		
		Dept qd = new Dept();
		qd.setName("桥东区");
		
		Dept sjz = new Dept();
		sjz.setName("石家庄市政府");
		
		yh.setParentDept(sjz);
		qd.setParentDept(sjz);
		
		sjz.getChildDepts().add(qd);
		sjz.getChildDepts().add(yh);
				
		gatherFsCaseManager.getDao().save(yh);
		gatherFsCaseManager.getDao().save(qd);
		gatherFsCaseManager.getDao().save(sjz);
		//新建事件类别
		CaseType type = new CaseType();
		type.setName("测试");
		type.setDescn("类型描述");
		gatherFsCaseManager.getDao().save(type);
		
		//新建单体事件
		for(int i = 0; i < 7; i++) {
			FsCase fsCase = new FsCase();
			fsCase.setTitle("事件" + i );
			fsCase.setCaseTime(new Date());
			fsCase.setStatus(CaseConstants.CASE_PROCESSED);
			fsCase.setCaseType(type);
			fsCase.setCode(String.valueOf(i));
			fsCase.setDescn("内容" + i);
			fsCase.setCounty(yh);
			fsCaseManager.save(fsCase);
		}
		
		/*for(int i = 0; i < 4; i++) {
			FsCase fsCase = new FsCase();
			fsCase.setTitle("事件" + i );
			fsCase.setCaseTime(new Date());
			fsCase.setStatus(CaseConstants.CASE_PROCESSED);
			fsCase.setCaseType(type);
			fsCase.setCode(String.valueOf(i));
			fsCase.setDescn("内容" + i);
			fsCase.setCounty(qd);
			fsCaseManager.save(fsCase);
		}
		
		for(int i = 0; i < 4; i++) {
			FsCase fsCase = new FsCase();
			fsCase.setTitle("凉菜" + i );
			fsCase.setCaseTime(new Date());
			fsCase.setStatus(CaseConstants.CASE_PROCESSED);
			fsCase.setCaseType(type);
			fsCase.setCode(String.valueOf(i));
			fsCase.setDescn("内容" + i);
			fsCase.setCounty(qd);
			fsCaseManager.save(fsCase);
		}*/
		String hql = "from FsCase f where f.status = ?";
		List<FsCase> list = fsCaseManager.query(hql, CaseConstants.CASE_PROCESSED);
		
		for(FsCase fs : list) {
			fs.setStatus(CaseConstants.CASE_CLOSED);
			fsCaseManager.save(fs);
			logger.info("状态: {} 时间：{}", fs.getStatus(), fs.getCaseTime());
			gatherFsCaseManager.gatherFscase(fs.getCaseType().getId(), fs.getCounty());
		}
		String mHql = "from FsCase f where f.isMultiple = ?";
		List<FsCase> mList = fsCaseManager.query(mHql, FsConstants.Y);
		assertEquals(1, mList.size());
	}
}
