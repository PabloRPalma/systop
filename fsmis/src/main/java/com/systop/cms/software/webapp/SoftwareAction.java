package com.systop.cms.software.webapp;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.cms.CmsConstants;
import com.systop.cms.model.SoftCatas;
import com.systop.cms.model.Software;
import com.systop.cms.software.catalog.service.SoftCatasManager;
import com.systop.cms.software.service.SoftwareManager;
import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;

/**
 * 软件管理Action
 * 
 * @author Lunch
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SoftwareAction extends DefaultCrudAction<Software, SoftwareManager> {

  @Autowired
  private SoftCatasManager softCatasManager;

  /**
   * 上传的文件
   */
  private File soft;

  /**
   * 上传的文件名称
   */
  private String softFileName;

  private String downFileUrl;

  /**
   * 软件信息列表，可根据软件名称及其类别进行查询
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String index() {
    String hql = "from Software s where s.name is not null ";
    List args = new ArrayList();
    if (StringUtils.isNotBlank(getModel().getName())) {
      hql = hql + "and s.name like ? ";
      args.add("%" + getModel().getName() + "%");
    }
    hql = hql + "order by s.pubDate desc";
    List<Software> softs = getManager().query(hql, args.toArray());
    getRequest().setAttribute("items", softs);
    return INDEX;
  }

  /**
   * 添加软件，跳转页面用
   * 
   * @return
   */
  public String newSoft() {
    return SUCCESS;
  }

  /** 获取所有的软件类别，页面使用 */
  public List<SoftCatas> getAllSoftCatas() {
    return softCatasManager.get();
  }

  /**
   * 保存软件信息
   */
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "软件名称是必须的.") })
  public String save() {
    try {
      if (soft != null) {
        String fileRelativePath = null;
        //保存文件不重命名
        fileRelativePath = UpLoadUtil.doUpload(soft, softFileName, CmsConstants.SOFT_FOLDER,
            getServletContext(), false);
        // 计算出软件大小KB
        getModel().setSize(soft.length() / 1024);
        // 相对路径，可用于下载地址
        getModel().setDownUrl(fileRelativePath);
      }
      getManager().save(getModel());
    } catch (Exception e) {
      this.addActionError(e.getMessage());
      return INPUT;
    }
    return SUCCESS;
  }

  /**
   * 删除软件信息
   */
  public String remove() {
    getManager().batchRemove(getServletContext(), getSelectedItems());
    return SUCCESS;
  }

  /**
   * 软件下载，并计数
   * 
   * @return
   */
  public String down() {
    if (getModel() != null && getModel().getId() != null) {
      String filePath = getServletContext().getRealPath(getModel().getDownUrl());
      File file = new File(filePath);
      if (file.exists()) {// 判断文件是否存在
        Integer count = getModel().getDownCount() == null ? 0 : getModel().getDownCount();
        // 计数
        count++;
        getModel().setDownCount(count);
        getManager().save(getModel());
        downFileUrl = getModel().getDownUrl();
        // 返回success指向Stream类型
        return SUCCESS;
      }
    }
    addActionError("软件下载失败，文件可能已经被删除");
    return INPUT;
  }

  /**
   * 配合文件下载， 配置文件中使用
   * 
   * @return
   */
  public InputStream getDownFileStream() {
    return ServletActionContext.getServletContext().getResourceAsStream(downFileUrl);
  }

  /**
   * 配置文件中获取下载的文件名。支持中文。
   */
  public String getDownFileName() {
    String fileName = null;
    File file = new File(getServletContext().getRealPath(downFileUrl));
    if (file.exists()) {
      try {
        fileName = new String(file.getName().getBytes(), "ISO8859-1");
      } catch (UnsupportedEncodingException e) {
        logger.error("文件下载时，文件名称编码错误");
        e.printStackTrace();
      }
    } else {
      throw new ApplicationException("文件不存在!");
    }
    return fileName;
  }

  /**
   * 前台网站显示的软件下载列表,同时可以根据软件类别查询
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String forntIndex() {
    String hql = "from Software s ";
    List args = new ArrayList();
    if (getModel() != null && getModel().getSoftCatalog() != null) {
      hql = hql + "where s.softCatalog.id = ? ";
      args.add(getModel().getSoftCatalog().getId());
    }
    hql = hql + "order by s.downCount desc";
    List<Software> softs = getManager().query(hql, args.toArray());
    getRequest().setAttribute("items", softs);
    return INDEX;
  }

  /**
   * @return the soft
   */
  public File getSoft() {
    return soft;
  }

  /**
   * @param soft the soft to set
   */
  public void setSoft(File soft) {
    this.soft = soft;
  }

  /**
   * @return the softFileName
   */
  public String getSoftFileName() {
    return softFileName;
  }

  /**
   * @param softFileName the softFileName to set
   */
  public void setSoftFileName(String softFileName) {
    this.softFileName = softFileName;
  }

}
