package com.systop.common.modules.security.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.systop.common.modules.security.acegi.resourcedetails.ResourceDetails;
import com.systop.common.modules.security.user.model.Role;
import com.systop.common.modules.security.user.service.init.SysRolesProviderImpl;
import com.systop.core.Constants;

/**
 * 用户管理及相关模块的常量类
 * @author Sam Lee
 *
 */
public final class UserConstants {
  /**
   * 在Web目录下存放照片的路径
   */
  public static final String USER_PHOTO_PATH = "/uploaded/photo";
  /**
   * 权限操作－URL
   */
  public static final String PERMISSION_OPERATION_URL = "target_url";
  /**
   * 权限操作－函数
   */
  public static final String PERMISSION_OPERATION_FUNC = "target_function";
  /**
   * 用户类型－注册用户
   */
  public static final String USER_TYPE_REGIST = "0";
  /**
   * 用户类型－系统用户
   */
  public static final String USER_TYPE_SYS = "1";
  /**
   * 用户状态－未审核
   */
  public static final String USER_STATUS_UNUSABLE = "0";
  /**
   * 用户状态－可用
   */
  public static final String USER_STATUS_USABLE = "1";
  
  /**
   * 用户状态－待激活
   */
  public static final String USER_STATUS_UNENABLED = "2";
  
  /**
   * 性别常量，M-男性
   */
  public static final String GENT = "M";

  /**
   * 性别常量，F-女性
   */
  public static final String LADY = "F";
  
  /**
   * 地震行业，Y-行业内
   * */
  public static final String INDUSTRY_INTERNAL = "Y";
  
  /**
   * 地震行业，N-行业外
   * */
  public static final String INDUSTRY_EXTERNAL = "N";
  
  /**
   * 学历，1-博士
   */
  public static final String USER_DEGREE_DOCTOR = "1";
  /**
   * 学历，2-硕士
   */
  public static final String USER_DEGREE_MASTER = "2";
  /**
   * 学历，3-学士
   */
  public static final String USER_DEGREE_BACHELOR = "3";
  /**
   * 学历，4-其他
   */
  public static final String USER_DEGREE_OTHERS = "4";
  
  /**
   * 用户单位性质 1-政府机构
   */
  public static final String USER_UNITKIND_GOV = "1";
  /**
   * 用户单位性质 2-科研机构
   */
  public static final String USER_UNITKIND_SCI = "2";
  /**
   * 用户单位性质 3-教育院校
   */
  public static final String USER_UNITKIND_EDU = "3";
  /**
   * 用户单位性质 4-商业公司
   */
  public static final String USER_UNITKIND_BUS = "4";
  /**
   * 用户单位性质 5-民间组织
   */
  public static final String USER_UNITKIND_POP = "5";
  /**
   * 用户单位性质 6-其他
   */
  public static final String USER_UNITKIND_OTH = "6";
  /**
   * 用户级别 0-普通用户
   */
  public static final String USER_LEVEL_NORMAL = SysRolesProviderImpl.ROLE_NORMAL.getName();
  /**
   * 用户级别 1-高级用户
   */
  public static final String USER_LEVEL_SENIOR = SysRolesProviderImpl.ROLE_SENIOR.getName();
  
  /**
   * 系统管理员角色
   */
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  /**
   * 高级用户角色
   */
  public static final String ROLE_SENIOR = "ROLE_SENIOR";
  /**
   * 普通用户角色
   */
  public static final String ROLE_NORMAL = "ROLE_NORMAL";
  /**
   * 查看经纬度角色
   */
  public static final String ROLE_LONGLAT = "ROLE_LONGLAT";
  /**
   * 查看地震目录角色
   */
  public static final String ROLE_CATALOG = "ROLE_CATALOG";
  /**
   * 查看震相角色
   */
  public static final String ROLE_PHASE = "ROLE_PHASE";
  /**
   * 查看事件波形角色
   */
  public static final String ROLE_EVENT = "ROLE_EVENT";
  /**
   * 查看连续波形角色
   */
  public static final String ROLE_CONTINUE = "ROLE_CONTINUE";
  
  private static Role sysRole(String roleName, String descn) {
    Role role = new Role();
    role.setName(roleName);
    role.setDescn(descn);
    role.setIsSys(Constants.STATUS_AVAILABLE); //标记为系统角色
    return role;
  }
  
  /**
   * 系统角色列表
   */
  public static final List<Role> SYS_ROLES = new ArrayList<Role>();
  
  static {
    SYS_ROLES.add(sysRole(ROLE_ADMIN, "系统管理员角色"));
  }
  
  /**
   * 省份
   */
  public static String PROVINCE_INFO_BJ = "BJ";
  public static String PROVINCE_INFO_TJ = "TJ";
  public static String PROVINCE_INFO_HE = "HE";
  public static String PROVINCE_INFO_SX = "SX";
  public static String PROVINCE_INFO_NM = "NM";
  public static String PROVINCE_INFO_LN = "LN";
  public static String PROVINCE_INFO_XJ = "XJ";
  public static String PROVINCE_INFO_HL = "HL";
  public static String PROVINCE_INFO_SH = "SH";
  public static String PROVINCE_INFO_JS = "JS";
  public static String PROVINCE_INFO_ZJ = "ZJ";
  public static String PROVINCE_INFO_AH = "AH";
  public static String PROVINCE_INFO_FJ = "FJ";
  public static String PROVINCE_INFO_JX = "JX";
  public static String PROVINCE_INFO_SD = "SD";
  public static String PROVINCE_INFO_HA = "HA";
  public static String PROVINCE_INFO_NX = "NX";
  public static String PROVINCE_INFO_SN = "SN";
  public static String PROVINCE_INFO_GD = "GD";
  public static String PROVINCE_INFO_GX = "GX";
  public static String PROVINCE_INFO_HI = "HI";
  public static String PROVINCE_INFO_SC = "SC";
  public static String PROVINCE_INFO_GS = "GS";
  public static String PROVINCE_INFO_YN = "YN";
  public static String PROVINCE_INFO_HN = "HN";
  public static String PROVINCE_INFO_CQ = "CQ";
  public static String PROVINCE_INFO_HB = "HB";
  public static String PROVINCE_INFO_XZ = "XZ";
  public static String PROVINCE_INFO_QH = "QH";
  public static String PROVINCE_INFO_GZ = "GZ";
  public static String PROVINCE_INFO_JL = "JL";
  public static String PROVINCE_INFO_HK = "HK";
  public static String PROVINCE_INFO_MK = "MK";
  public static String PROVINCE_INFO_TW = "TW";
  
  /**
   * 注册用户省份Map
   */
  public static final Map<String, String> PROVINCE_INFO_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_BJ, "北京");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_TJ, "天津");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HE, "河北");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_SX, "山西");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_NM, "内蒙古");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_LN, "辽宁");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_XJ, "新疆");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HL, "黑龙江");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_SH, "上海");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_JS, "江苏");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_ZJ, "浙江");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_AH, "安徽");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_FJ, "福建");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_JX, "江西");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_SD, "山东");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HA, "河南");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_NX, "宁夏");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_SN, "陕西");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_GD, "广东");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_GX, "广西");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HI, "海南");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_SC, "四川");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_GS, "甘肃");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_YN, "云南");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HN, "湖南");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_CQ, "重庆");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HB, "湖北");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_XZ, "西藏");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_QH, "青海");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_GZ, "贵州");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_JL, "吉林");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_HK, "香港");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_MK, "澳门");
    PROVINCE_INFO_MAP.put(PROVINCE_INFO_TW, "台湾");
  }
  /**
   * 权限操作列表
   */
  public static final Map<String, String> PERMISSION_OPERATIONS = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    PERMISSION_OPERATIONS.put(PERMISSION_OPERATION_FUNC, "函数权限");
    PERMISSION_OPERATIONS.put(PERMISSION_OPERATION_URL, "URL权限");
  }
  
  /**
   * 注册用户状态Map
   */
  public static final Map<String, String> USER_STATUS = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    USER_STATUS.put(USER_STATUS_UNUSABLE, "未审核");
    USER_STATUS.put(USER_STATUS_USABLE, "已通过");
    USER_STATUS.put(USER_STATUS_UNENABLED, "待激活");
  }
  
  /**
   * 性别常量Map
   */
  public static final Map<String, String> SEX_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    SEX_MAP.put(GENT, "男");
    SEX_MAP.put(LADY, "女");
  }
  
  /**
   * 行业内外常量Map
   */
  public static final Map<String, String> INDUSTRY_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    INDUSTRY_MAP.put(INDUSTRY_INTERNAL, "内");
    INDUSTRY_MAP.put(INDUSTRY_EXTERNAL, "外");
  }
  
  /**
   * 学历常量Map
   */
  public static final Map<String, String> DEGREE_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    DEGREE_MAP.put(USER_DEGREE_DOCTOR, "博士");
    DEGREE_MAP.put(USER_DEGREE_MASTER, "硕士");
    DEGREE_MAP.put(USER_DEGREE_BACHELOR, "学士");
    DEGREE_MAP.put(USER_DEGREE_OTHERS, "其他");
  }
  
  /**
   * 用户单位性质常量Map
   */
  public static final Map<String, String> USER_UNITKIND_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    USER_UNITKIND_MAP.put(USER_UNITKIND_GOV, "政府机构");
    USER_UNITKIND_MAP.put(USER_UNITKIND_SCI, "科研机构");
    USER_UNITKIND_MAP.put(USER_UNITKIND_EDU, "教育院校");
    USER_UNITKIND_MAP.put(USER_UNITKIND_BUS, "商业公司");
    USER_UNITKIND_MAP.put(USER_UNITKIND_POP, "民间组织");
    USER_UNITKIND_MAP.put(USER_UNITKIND_OTH, "其他...");
  }
  
  /**
   * 用户级别常量Map
   */
  public static final Map<String, String> USER_LEVEL_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    USER_LEVEL_MAP.put(USER_LEVEL_NORMAL, "普通用户");
    USER_LEVEL_MAP.put(USER_LEVEL_SENIOR, "高级用户");
  }
  
  /**
   * URL资源
   */
  public static final String RESOURCE_TYPE_URL = ResourceDetails.RES_TYPE_URL;
  /**
   * 函数资源
   */
  public static final String RESOURCE_TYPE_FUNCTION 
    = ResourceDetails.RES_TYPE_FUNCTION;
  
  /**
   * 权限操作列表
   */
  public static final Map<String, String> RESOURCE_TYPES = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    RESOURCE_TYPES.put(RESOURCE_TYPE_URL, "URL资源");
    RESOURCE_TYPES.put(RESOURCE_TYPE_FUNCTION, "函数资源");
  }
  /**
   * 缺省密码，在修改的时候，如果输入缺省密码，则表示使用原来的密码。
   */
  public static final String DEFAULT_PASSWORD = "*********";
  /**
   * 防止实例化
   */
  private UserConstants() {    
  }
}
