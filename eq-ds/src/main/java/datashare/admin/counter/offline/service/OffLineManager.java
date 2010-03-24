package datashare.admin.counter.offline.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;

import datashare.admin.counter.offline.model.OffLine;


/**
 * 离线服务service
 * @author dhm
 *
 */
@SuppressWarnings("serial")
@Service
public class OffLineManager extends BaseGenericsManager<OffLine> {

  /**
   * 保存离线服务信息
   * @param offLine
   */
  @Transactional
  public void add(OffLine offLine) {
    if(get(offLine)){
      save(offLine);
    }else{
      update(offLine);
    }
  }
  
  /**
   * 判断是否以存在此服务信息
   * @param offLine
   * @return
   */
  private boolean get(OffLine offLine){
    if(offLine.equals(get(offLine.getId()))){
      return false;
    }
    return true;
  }
  /**
   * 删除离线服务信息
   * @param id
   */
  @Transactional
  public void remove(Integer id){
    super.remove(get(id));
  }
}
