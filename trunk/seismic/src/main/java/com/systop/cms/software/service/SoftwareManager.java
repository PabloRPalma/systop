package com.systop.cms.software.service;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.cms.model.Software;
import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;

/**
 * 软件下载的Manager
 * 
 * @author Lunch
 */
@Service
public class SoftwareManager extends BaseGenericsManager<Software> {

  
  /**
   * 保存软件信息，简单严重软件名称不能重复,以及是否有文件下载地址
   */
  @Transactional
  public void save(Software soft) {
    if (getDao().exists(soft, "name")) {
      throw new ApplicationException("保存软件失败,软件名称'" + soft.getName() + "'已存在.");
    }
    if (StringUtils.isBlank(soft.getDownUrl())) {
      throw new ApplicationException("请给出有效的文件下载地址.");
    }
    // 软件发布日期
    soft.setPubDate(new Date());
    getDao().saveOrUpdate(soft);
  }

  /**
   * 批量删除软件信息
   * 
   * @param selectIds
   */
  @Transactional
  public void batchRemove(ServletContext servletContext, Serializable[] selectIds) {
    if (selectIds != null) {
      for (Serializable id : selectIds) {
        Software soft = get(Integer.parseInt((String) id));
        if (soft != null) {
          removeFile(servletContext, soft);
          getDao().delete(soft);
        }
      }
    }
  }

  /**
   * 删除指定软件信息包含的物理文件
   * 
   * @param soft
   */
  private void removeFile(ServletContext servletContext, Software soft) {
    File file = new File(servletContext.getRealPath(soft.getDownUrl()));
    if (file.exists()) {
      file.delete();
      logger.debug("文件:" + soft.getDownUrl() + "成功删除");
    }
  }

}
