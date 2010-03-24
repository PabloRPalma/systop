package datashare.admin.method.service;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;

import datashare.admin.method.model.Method;
import datashare.base.service.Definable;

/**
 * 测项管理类
 * @author wbb
 */
@SuppressWarnings("unchecked")
@Service
public class MethodManager extends BaseGenericsManager<Method>  implements Definable{

  @Override
  public boolean isDefined() {
    return get().size() > 0;
  }
}
