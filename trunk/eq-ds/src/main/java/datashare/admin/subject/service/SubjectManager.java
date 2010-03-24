package datashare.admin.subject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.core.service.BaseGenericsManager;

import datashare.admin.subject.model.Subject;
import datashare.base.service.Definable;

/**
 * 学科管理Manager
 * @author wbb
 */
@Service
public class SubjectManager extends BaseGenericsManager<Subject> implements Definable {
  @Override
  @Transactional
  public void save(Subject subject) {
    Assert.notNull(subject);
    subject.setMethodIds(subject.getMethodIdArray());
    getDao().saveOrUpdate(subject);
  }

  @Override
  public boolean isDefined() {
    return get().size() >= 5;
  }
}
