package datashare.admin.subject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import datashare.admin.method.model.Method;
import datashare.admin.method.service.MethodManager;
import datashare.admin.subject.model.Subject;
import datashare.base.test.BaseTestCase;

public class SubjectManagerTest extends BaseTestCase {
  @Autowired
  private SubjectManager subjectManager;

  @Autowired
  private MethodManager methodManager;

  /**
   * 保存学科
   */
  public void testSave() {
    Subject sj = new Subject();
    sj.setName("重力");
    sj.setId("03");
    subjectManager.save(sj);
    assertTrue(sj.getId() != null);
    logger.info("new ID:" + sj.getId());
  }

  /**
   * 级联保存
   */
  public void testSaveMethod() {
    Subject sj = new Subject();
    sj.setName("重力");
    sj.setId("03");

    //得到所有测项
    List<Method> list = methodManager.get();
    String[] ids = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      ids[i] = list.get(i).getId();
    }
    sj.setMethodIds(ids);

    subjectManager.save(sj);
    assertTrue(sj.getId() != null);
    logger.info("method size:" + sj.getMethods().size());
  }
}
