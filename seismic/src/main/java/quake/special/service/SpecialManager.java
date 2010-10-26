package quake.special.service;

import quake.special.model.Special;

import com.systop.core.service.BaseGenericsManager;

public class SpecialManager extends BaseGenericsManager<Special> {
  /**
   * 根据Id得到special实体
   * @param  id
   * @return Special实体
   */
  public Special getSpecialById(Integer id) {
    String hql = "from Special s where s.id = ?";
    return (Special) getDao().findObject(hql,
        new Object[] { id });
  }
}
