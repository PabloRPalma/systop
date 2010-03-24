package datashare.descr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;

import datashare.descr.model.Descr;

/**
 * 说明描述信息service
 * @author DU
 */
@Service
public class DescrManager extends BaseGenericsManager<Descr> {
  
  /**
   * 取得描述信息
   * @param url 访问路径
   * @param type 类型
   */
  @SuppressWarnings("unchecked")
  public Descr getDescribeByUrlAndType(String url, String type) {
    Descr descr = null;
    List desList = getDao().query("from Descr d where d.type = ? and d.accessUrl = ?",
        new Object[]{type, url});
    if (desList != null && desList.size() > 0) {
      descr = (Descr) desList.get(0);
    }
    return descr;
  }
}
