package com.systop.cms.template.webapp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.CmsConstants;
import com.systop.cms.model.Templates;
import com.systop.cms.template.TemConstants;
import com.systop.cms.template.service.TemplateManager;
import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;

/**
 * 模板管理 Action
 * 
 * @author Bin
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TemplateAction extends DefaultCrudAction<Templates, TemplateManager> {
  /**
   * 查询模板类型
   */
  private String queryType;

  /** 模板资源路径是否存在 */
  private boolean pathNotExists;

  /** 进入目录 */
  private String respath;

  /** 返回页面后显示的路径 */
  private String viewPath;

  /**
   * 资源文件
   */
  private File[] res;

  /**
   * 资源文件保存后的名称
   */
  private String[] resFileName;

  /**
   * 添加模板
   */
  public String newTemplate() {
    // 设置默认值
    getModel().setIsDef(CmsConstants.N);
    getModel().setIsComm(CmsConstants.N);
    return SUCCESS;
  }

  /**
   * 执行查询
   * 
   * @see com.systop.core.webapp.struts2.action.DefaultCrudAction#pageQuery()
   */
  @Override
  protected Page pageQuery() {
    return getManager().pageQuery(PageUtil.getPage(getPageNo(), getPageSize()),
        setupDetachedCriteria());
  }

  /**
   * 封装查询条件
   */
  protected DetachedCriteria setupDetachedCriteria() {
    if (StringUtils.isNotBlank(queryType)) {
      getModel().setType(queryType);
    }

    Example example = Example.create(getModel()).ignoreCase().setPropertySelector(
    /**
     * 选择不为null的属性作为查询条件
     */
    new PropertySelector() {
      public boolean include(Object propertyValue, String propertyName, Type type) {
        return propertyValue != null;
      }
    }).enableLike(MatchMode.ANYWHERE);

    DetachedCriteria criteria = DetachedCriteria.forClass(getModel().getClass()).add(example);
    return super.setupSort(criteria);
  }

  /**
   * 保存模板
   */
  @Override
  public String save() {
    if (getManager().getDao().exists(getModel(), "name")) {
      addActionError("模板名称：" + getModel().getName() + " 已存在");
      return INPUT;
    }
    String result = super.save();
    // 默认模板一个类型只能设置一个
    if (getModel().getIsDef().equals(CmsConstants.Y) && SUCCESS.equals(result)) {
      String type = getModel().getType();
      List<Templates> list = getManager().findByType(type, getModel().getId());
      for (Templates template : list) {
        template.setIsDef(CmsConstants.N);
        getManager().save(template);
      }
    }

    // 清空ServletContext中CmsConstants.STRING_TEMPLATE_LOADER属性的值
    getServletContext().setAttribute(CmsConstants.STRING_TEMPLATE_LOADER, null);
    return result;
  }

  /**
   * 显示资源文件列表
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String filesList() {

    // 文件路径是否存在，在filelist.jsp页面使用此状态
    pathNotExists = false;

    File root = new File(getServletContext().getRealPath(CmsConstants.RES_ROOT));
    // 如果CmsConstants.RES_ROOT对应的跟目录不存在，创建[初次访问]
    if (!root.exists()) {
      root.mkdir();
    }
    // 为请求的路径的开始位置添加CmsConstants.RES_ROOT
    respath = ResFileUtil.addResRoot(respath);

    // 获得Server端真实的文件存放路径
    String resRealPath = getServletContext().getRealPath(respath);
    logger.debug(resRealPath);

    // 创建实际访问的文件
    root = new File(resRealPath);
    if (!root.exists()) {// 如果不存在，直接返回
      pathNotExists = true;
      return SUCCESS;
    }
    viewPath = "/" + respath.replace("\\", "/");
    //如果是以 '/' 结尾。将其擦去
    viewPath = viewPath.endsWith("/") ? viewPath.substring(0, viewPath.length() - 1) : viewPath;

    // 获得父路径，为返回上级目录准备
    String parentName = ResFileUtil.paresParentPath(root.getParent());
    ResFile parentFile = new ResFile();
    parentFile.setName(parentName);
    getRequest().setAttribute("parentFile", parentFile);

    // 获取访问目录的子目录和文件
    File[] subFiles = root.listFiles();
    List list = new ArrayList();
    if (subFiles != null) {
      for (int i = 0; i < subFiles.length; i++) {
        ResFile resf = new ResFile();
        resf.setDirectory(subFiles[i].isDirectory());
        resf.setName(subFiles[i].getName());
        resf.setSize(subFiles[i].length());
        resf.setPath(ResFileUtil.switchWebPath(subFiles[i].getPath()));
        resf.setReqUrl("/" + ResFileUtil.switchUsePath(subFiles[i].getPath()).replace("\\", "/"));
        list.add(resf);
      }
    }

    items = list;
    return SUCCESS;
  }

  /**
   * 保存资源文件
   * 
   * @return
   */
  public String saveResFiles() {
    String resRealPath = CmsConstants.RES_ROOT;

    // 为请求的路径的开始位置添加CmsConstants.RES_ROOT
    resRealPath = ResFileUtil.addResRoot(respath);
    if (res != null) {
      for (int i = 0; i < res.length; i++) {
        UpLoadUtil.doUpload(res[i], resFileName[i], resRealPath, getServletContext(), false);
      }
    }
    return SUCCESS;
  }

  /**
   * dwr删除方法
   * 
   * @param id 类别id
   * @return SUCCESS 删除成功 ERROR 类别下存在链接
   */
  public String dwrRemove(Integer id) {
    Templates templ = getManager().get(id);
    if (templ.getTemplateForArtrticles().isEmpty() && templ.getTemplateForCatalogs().isEmpty()) {
      getManager().remove(templ);
      return SUCCESS;
    } else {
      return ERROR;
    }
  }

  /**
   * 得到模板类型列表
   */
  public Map<String, String> getTypes() {
    return TemConstants.TEMPLATE_TYPE_MAP;
  }

  /**
   * 是否类型列表
   */
  public Map<String, String> getYns() {
    return CmsConstants.YN_MAP;
  }

  public String getQueryType() {
    return queryType;
  }

  public void setQueryType(String queryType) {
    this.queryType = queryType;
  }

  /**
   * @return the pathNotExists
   */
  public boolean isPathNotExists() {
    return pathNotExists;
  }

  /**
   * @param pathNotExists the pathNotExists to set
   */
  public void setPathNotExists(boolean pathNotExists) {
    this.pathNotExists = pathNotExists;
  }

  public File[] getRes() {
    return res;
  }

  public void setRes(File[] res) {
    this.res = res;
  }

  public String[] getResFileName() {
    return resFileName;
  }

  public void setResFileName(String[] resFileName) {
    this.resFileName = resFileName;
  }

  public String getRespath() {
    return respath;
  }

  /** 做url转换编码 */
  public void setRespath(String respath) {
    try {
      this.respath = URLDecoder.decode(respath, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return the viewPath
   */
  public String getViewPath() {
    return viewPath;
  }
}
