package com.systop.fsmis.supervisor.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.systop.core.util.XlsImportHelper;
import com.systop.core.util.XlsImportHelper.Converter;
import com.systop.core.util.XlsImportHelper.StringConverter;

/**
 * Excel数据导入工具类
 * @author ShangHua
 */
@Service
public class SupervisorXlsImportHelperFactory {
  /**
   * 私有构造器
   */
  private SupervisorXlsImportHelperFactory() {
  }

  /**
   * 与Sheet对应的Map
   */
  public static Map<String, Converter> properties = new HashMap<String, Converter>();
  static {
    //所属部门名称
    properties.put("code", new StringConverter(0)); //编号
    properties.put("name", new StringConverter(1)); //姓名   
    properties.put("idNumber", new StringConverter(2)); //身份证号    
    properties.put("gender", new StringConverter(3));  //性别   
    properties.put("birthday", new StringConverter(4)); //出生日期
    properties.put("unit", new StringConverter(5)); //单位     
    properties.put("duty", new StringConverter(6)); //职务   
    properties.put("deptName", new StringConverter(7)); //所属部门    
    properties.put("isLeader", new StringConverter(8)); //是否负责人
    properties.put("mobile", new StringConverter(9)); //移动电话  
    properties.put("phone", new StringConverter(10)); //固定电话   
    properties.put("superviseRegion", new StringConverter(11)); //监管区域        
  }

  /**
   * 创建并返回XlsImportHelper
   */
  public static XlsImportHelper create() {
    XlsImportHelper xih = new XlsImportHelper();
    xih.setProperties(properties);
    return xih;
  }
}
