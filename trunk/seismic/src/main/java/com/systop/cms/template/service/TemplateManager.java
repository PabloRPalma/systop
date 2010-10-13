package com.systop.cms.template.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.model.Templates;
import com.systop.core.service.BaseGenericsManager;;

/**
 * 模板管理
 * @author Bin
 */
@Service
public class TemplateManager extends BaseGenericsManager<Templates> {
  /**
   * 根据模板类型得到模板列表
   * @param type 模板类型
   * @param id 排除的模板id
   * @return list of <code>Templates</code>
   */
  @SuppressWarnings("unchecked")
  public List<Templates> findByType(String type, Integer id) {
    if (type == null || type.equals("")) {
      return Collections.EMPTY_LIST;
    }
    String hql = "from Templates where type = ? and id <> ?";
    List<Templates> list = query(hql, new Object[]{type, id});
    return list;
  }
}
