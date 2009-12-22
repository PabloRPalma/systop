package com.systop.fsmis.supervisor.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.util.DateUtil;
import com.systop.core.util.XlsImportHelper;
import com.systop.core.webapp.struts2.action.BaseAction;
import com.systop.core.webapp.struts2.upload.UploadedFileHandler;
import com.systop.fsmis.model.Supervisor;
import com.systop.fsmis.supervisor.service.SupervisorManager;
import com.systop.fsmis.supervisor.service.SupervisorXlsImportHelperFactory;

/**
 * 通过Excel导入监管员信息
 * @author ShangHua
 */
@SuppressWarnings({ "unchecked", "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupervisorImportAction extends BaseAction {
	/**
	 * 处理文件上传的接口
	 */
	protected UploadedFileHandler uploadedFileHandler;
	
	/**
	 * 监管员管理Manager
	 */
	private SupervisorManager supervisorManager;

	/**
	 * 存放导入过程中错误信息
	 */
	private List<String> errorInfo;

	/**
	 * 导入数据文件
	 */
	private File data;

	/**
	 * 导入数据文件名称
	 */
	private String dataFileName;

	/** 
	 * 备份文件下载 
	 */
	private String downFileName = null;

	/** 
	 * 下载文件流 
	 */
	private InputStream inputStream = null;

	/**
	 * 从Excel中导入监管员信息到数据库
	 */
	public String importData() {
		errorInfo = new ArrayList<String>();
	  //判断上传文件是否存在
		if (StringUtils.isBlank(dataFileName)) { 
			errorInfo.add("请选择要导入的文件!");
			return INPUT;
		}
		if (StringUtils.isNotBlank(dataFileName)) { 
		  //判断后缀是否是Xls文件
			if (!XlsImportHelper.isAllowedXls(dataFileName)) {
				errorInfo.add("只能上传以”.xls“为后缀的文件!");
				return INPUT;
			}
		}
		XlsImportHelper xih = SupervisorXlsImportHelperFactory.create();
		//读取数据存放在此list中
		List<String> list = new ArrayList<String>();
		try {
			InputStream is = new FileInputStream(data);
			Workbook rwb = Workbook.getWorkbook(is);
			//获取第一张Sheet表
			Sheet rs = rwb.getSheet(0);
			for (int i = 1; i < rs.getRows(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				//得到导入数据map
				xih.importCommonProperties(map, rs, i);
				String code = (String) map.get("code"); //编号
				String name = (String) (map.get("name")); //姓名
				String idNumber = (String) (map.get("idNumber")); //身份证号
				String gender = (String) (map.get("gender")); //性别
				String birthday = (String) (map.get("birthday")); //出生日期
				String unit = (String) (map.get("unit")); //单位
				String duty = (String) (map.get("duty")); //职务
				String deptName = (String) (map.get("deptName")); //所属部门
				String isLeader = (String) (map.get("isLeader")); //是否负责人
				String mobile = (String) (map.get("mobile")); //移动电话
				String phone = (String) (map.get("phone")); //固定电话
				String superviseRegion = (String) (map.get("superviseRegion")); //监管区域

				//所属部门为空不进行导入
				if (StringUtils.isBlank(deptName)) {
					continue;
				}
				//姓名为空不进行导入
				if (StringUtils.isBlank(name)) {
					list.add("Excel表中监管员姓名为空，不做导入处理!");
					continue;
				}
				//获得当前登录用户
				User user = UserUtil.getPrincipal(getRequest());	
				if (user.getDept().getName().equals(deptName)) {
					/*
					 * List<PersonInfo> persons = personInfoManager.getPersons(enterprise
					 * .getId(), personName, idcard); 
					 * for (PersonInfo personInfo :persons) { 
					 * supervisorManager.remove(personInfo); 
					 * }
					 */
					Supervisor supervisor = new Supervisor();
					supervisor.setDept(user.getDept());
					supervisor.setCode(code);
					supervisor.setName(name);
					supervisor.setIdNumber(idNumber);
					supervisor.setGender(gender);
					supervisor.setBirthday(DateUtil.convertStringToDate(birthday));
					supervisor.setUnit(unit);
					supervisor.setDuty(duty);
					supervisor.setIsLeader(isLeader);
					supervisor.setMobile(mobile);
					supervisor.setPhone(phone);
					supervisor.setSuperviseRegion(superviseRegion);
					supervisorManager.save(supervisor);
				} else {
					list.add("所属部门【" + deptName + "】不是当前登录所在部门，不允许导入!");
					continue;
				}
			}
		} catch (Exception e) {
			errorInfo.add("Excel表连接失败.");
			return INPUT;
		}
		if (list.size() > 0) {
			errorInfo.addAll(list);
			return INPUT;
		}
		return SUCCESS;
	}

	/**
   * 
   */
	protected Map getTableTypes() {
		return SupervisorXlsImportHelperFactory.properties;
	}

	/**
	 * @return
	 */
	public UploadedFileHandler getUploadedFileHandler() {
		return uploadedFileHandler;
	}

	/**
	 * 设置{@link UploadedFileHandler}对象，用于处理单个上传文件.
	 * @param uploadedFileHandler
	 */
	public void setUploadedFileHandler(UploadedFileHandler uploadedFileHandler) {
		this.uploadedFileHandler = uploadedFileHandler;
	}
	
	public File getData() {
		return data;
	}

	public void setData(File data) {
		this.data = data;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public String getDownFileName() {
		return downFileName;
	}

	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public SupervisorManager getSupervisorManager() {
		return supervisorManager;
	}

	public void setSupervisorManager(SupervisorManager supervisorManager) {
		this.supervisorManager = supervisorManager;
	}

	public List<String> getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(List<String> errorInfo) {
		this.errorInfo = errorInfo;
	}

}
