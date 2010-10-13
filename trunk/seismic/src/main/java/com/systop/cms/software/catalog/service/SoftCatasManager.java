package com.systop.cms.software.catalog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.cms.model.SoftCatas;
import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;

@Service
@Transactional
public class SoftCatasManager extends BaseGenericsManager<SoftCatas> {
  
  /**
   * 返回所有软件类别信息
   * @return
   */

  public List<SoftCatas> getSoftCatz(){
    return this.query("from SoftCatas");
  }
  
  /**
   * 根据ID返回指定软件类别
   * @param id
   * @return
   */
  public SoftCatas getSoftCatasById(Integer id){
    return this.findObject("from SoftCatas o where o.id = ?", new Object[]{id});
  }
  /**
   * 保存软件类别信息
   */
  public void save(SoftCatas softCatas){
    if(getDao().exists(softCatas, "name")){
      throw new ApplicationException("软件类别保存失败!" + softCatas.getName() + "已存在！");
    }
    getDao().saveOrUpdate(softCatas);
  }
 
  /**
   * 删除软件类别信息
   */
  public void delete(SoftCatas softCatas){
    getDao().delete(softCatas);
  }
}
