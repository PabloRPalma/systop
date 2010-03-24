package com.systop.cms;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

/**
 * CMS常量类
 * 
 * @author zw
 */
public final class CmsConstants {

  /** 私有构造器 */
  private CmsConstants() {
  }

  /** 资源文件 */
  public static final String BUNDLE_KEY = "application";

  /** 资源绑定对象 */
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_KEY);

  /** 查询编号 */
  public static final int QUERY_CODE = 8;

  /** 性别常量，M-男性 */
  public static final String GENT = "M";

  /** 性别常量，F-女性 */
  public static final String LADY = "F";

  /**
   * 婚姻常量，Y-已婚
   */
  public static final String MARRIED_Y = "Y";

  /** 婚姻常量，N-未婚 */
  public static final String MARRIED_N = "N";

  /** 学历常量，0-初中以下 */
  public static final String ELEMENTARY = "0";

  /** 学历常量，1-初中 */
  public static final String JUNIOR = "1";

  /** 学历常量，2-高中 */
  public static final String SENIOR = "2";

  /** 学历常量，3-大专 */
  public static final String JUNIOR_COLLEGE = "3";

  /** 学历常量，4-本科 */
  public static final String UNDERGRADUATE = "4";

  /** 学历常量，5-本科以上 */
  public static final String DOCTOR = "5";

  /** 政治面貌，0-党员 */
  public static final String PARTY = "0";

  /** 政治面貌，1-团员 */
  public static final String MEMBER = "1";

  /** 政治面貌，2-群众 */
  public static final String MULTITUDE = "2";

  /** 政治面貌，3-其他 */
  public static final String OTHER = "3";

  /** 婚姻常量Map */
  public static final Map<String, String> MARRIED_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    MARRIED_MAP.put(MARRIED_N, "未婚");
    MARRIED_MAP.put(MARRIED_Y, "已婚");
  }

  /** 学历常量Map */
  public static final Map<String, String> DEGREE_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    DEGREE_MAP.put(ELEMENTARY, "初中以下");
    DEGREE_MAP.put(JUNIOR, "初中");
    DEGREE_MAP.put(SENIOR, "高中");
    DEGREE_MAP.put(JUNIOR_COLLEGE, "大专");
    DEGREE_MAP.put(UNDERGRADUATE, "本科");
    DEGREE_MAP.put(DOCTOR, "本科以上");
  }

  /** 政治面貌Map */
  public static final Map<String, String> POLITICAL_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    POLITICAL_MAP.put(PARTY, "党员");
    POLITICAL_MAP.put(MEMBER, "团员");
    POLITICAL_MAP.put(MULTITUDE, "群众");
    POLITICAL_MAP.put(OTHER, "其他");
  }

  /** 性别常量Map */
  public static final Map<String, String> SEX_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    SEX_MAP.put(GENT, "男");
    SEX_MAP.put(LADY, "女");
  }

  /** Y-是 */
  public static final String Y = "1";

  /** N-否 */
  public static final String N = "0";

  /** 是否状态Map */
  public static final Map<String, String> YN_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    YN_MAP.put(Y, "是");
    YN_MAP.put(N, "否");
  }

  /** 缺省的模板目录名称 */
  public static final String DEFAULT_TEMPLATE_DIR = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "templates.defaultDir", "classpath:templates");

  /** 缺省的模板主题 */
  public static final String DEFAULT_TEMPLATE_THEME = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "freemarker.defaultTheme", "simple");

  /** Freemark模板中dataModel的名字 */
  public static final String DEFAULT_FREEMARKER_DATAMODEL_NAME = ResourceBundleUtil.getString(
      RESOURCE_BUNDLE, "freemarker.dataModelName", "data");

  /** 模板中，用于列表的数据的名字 */
  public static final String DEFAULT_LIST_NAME = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "freemarker.listName", "list");

  /** 模板中用于存储标签id */
  public static final String DEFAULT_TAG_ID = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "freemarker.tagid", "tagid");

  /** 缓存中StringTemplateLoader属性名称 */
  public static final String STRING_TEMPLATE_LOADER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "string.templateloader", "stringTemplateLoader");

  /** 主页名称 */
  public static final String INDEX = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.webview.index", "index");

  /** 页面后缀 */
  public static final String POSTFIX = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.webview.postfix", "shtml");

  /** 文章管理在模板中使用的名称 */
  public static final String ARTICLE_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "ArticleFreeMarkerManager.beanName", "ArticleBean");

  /** 栏目管理在模板中使用的名称 */
  public static final String CATALOG_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "CatalogFreeMarkerManager.beanName", "CatalogBean");

  /** 链接管理在模板中使用的名称 */
  public static final String LINK_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "LinkFreeMarkerManager.beanName", "LinkBean");

  /** 公告管理在模板中使用的名称 */
  public static final String ANNOUNCE_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "AnnounceFreeMarkerManager.beanName", "AnnounceBean");

  /** 建议咨询管理在模板中使用的名称 */
  public static final String ADVISORY_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "AdvisoryFreeMarkerManager.beanName", "AdvisoryBean");
  
  /**软件下载管理在模板中使用的名称 */
  public static final String SOFTWARE_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "SoftwareFreeMarkerManager.beanName", "SoftwareBean");
  
  /** 地震目录在模板中使用的名称 */
  public static final String CZCAT_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "CzCatFreeMarkerManager.beanName", "CzCatBean");
  
  /** 元数据在模板中使用的名称 */
  public static final String METADATA_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "MetadataFreeMarkerManager.beanName", "MetadataBean");
  
  /**网站配置在模板中使用的名称*/
  public static final String SITECFG_BENA = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "SiteCfgFreeMarkerManager.beanName", "SiteCfgBean");

  /** 网站开始访问目录 */
  public static final String START_ROOT = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.webview.startroot", "/eq-ds");

  /** 解析自定义模板表达示 */
  public static final String FM_TMPT_REGEX = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.webview.fmtempt.regex", "<@FreeMarker_Template:([^>]*)/>");

  /** 管理员角色 */
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  
  /** 模板资源文件上传路径 */
  public static final String RES_ROOT = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.template.resroot", "ResRoot");
  
  /** 文章上传路径 */
  public static final String ARTICLE_ATT_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.article.uploadroot", "/uploadFiles/articles/att/");
  
  /** 超链接Logo上传路径 */
  public static final String LINK_SITE_LOGO = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.link.uploadroot", "/uploadFiles/link/logo/");
  
  /** 软件上传路径 */
  public static final String SOFT_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.software.uploadroot", "/uploadFiles/software/");
  
  /** 首页Flash推荐文章无图片时的默认图片 */
  public static final String ELITE_ART_IMG = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "cms.elite.articles.img", "/images/nopic.gif");
  
  
}