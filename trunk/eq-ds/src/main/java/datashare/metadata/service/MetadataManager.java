package datashare.metadata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;

import datashare.metadata.model.Metadata;

/**
 * 元数据管理service
 * @author DU
 */
@Service
public class MetadataManager extends BaseGenericsManager<Metadata> {

  /**
   * 取得元数据
   * @param type 类型
   */
  @SuppressWarnings("unchecked")
  public Metadata getMatadataInfo(String type) {
    Metadata metadata = null;
    List mtList = getDao().query("from Metadata m where m.type = ?",type);
    if (mtList != null && mtList.size() > 0) {
      metadata = (Metadata) mtList.get(0);
    }
    return metadata;
  }
}
