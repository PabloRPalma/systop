package com.systop.cms.webapp.taglibs;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.systop.cms.model.Content;
import com.systop.common.dao.impl.BaseHibernateDAO;
import com.systop.common.util.ReflectUtil;
import com.systop.common.util.RequestUtil;
import com.systop.common.webapp.taglibs.BaseSpringAwareBodyTagSupport;

/**
 * 用于显示一个内容的指定字段的Tag， 如果显示同一个内容的不同字段，可以重复使用
 * 并且不会反复查询数据。Taglib会把这个内容放在Request范围内，并以“contentDetail” 命名
 * @author Sam
 * 
 */
public class ContentDisplayerTag extends BaseSpringAwareBodyTagSupport {
  /**
   * 内容在Request中的名字
   */
  public static final String CONTENT_REQUEST_NAME = "contentDetail";

  /**
   * Content Id（Integer类型）在Request中的名字或Content Id本身
   * Taglib会在HttpServletRequest的paramenters,attributes,session中寻找
   * 如果没有找到则表示这是一个contentId（而不是Id对象在Request中的名字）
   */
  private String contentId;

  /**
   * 需要显示的字段名
   */
  private String fieldName;

  /**
   * @see TagSupport#doEndTag()
   */
  @Override
  public int doEndTag() throws JspException {
    Object cnts = RequestUtil.getValue((HttpServletRequest) pageContext
        .getRequest(), CONTENT_REQUEST_NAME, null);
    // 所有Content以id为key放在Map中，
    // 这个Map以CONTENT_REQUEST_NAME为名字放在Request中
    Map contents = (cnts == null) ? Collections.synchronizedMap(new HashMap())
        : (Map) cnts;
    String strId = (String) RequestUtil.getValue(
        (HttpServletRequest) pageContext.getRequest(), contentId, contentId);

    Integer id = null;
    try {
      id = Integer.parseInt(strId);
    } catch (NumberFormatException e) {
      return TagSupport.EVAL_PAGE;
    }

    Content content = null;
    if (contents.containsKey(id)) {
      content = (Content) contents.get(id); // 如果contents中包含id，则直接取出
      log.debug("Get content from request.");
    } else {
      // 如果contents中没有，则查询数据库
      BaseHibernateDAO dao = (BaseHibernateDAO) getBean("baseDao");
      content = dao.getObject(Content.class, id);
      contents.put(id, content);
      log.debug("put content to request");
    }
    ((HttpServletRequest) pageContext.getRequest()).setAttribute(
        CONTENT_REQUEST_NAME, contents);

    Object obj = ReflectUtil.get(content, fieldName.toLowerCase());
    if (obj instanceof Date) {
      obj = ((Date) obj).getTime();
    }
    try {
      pageContext.getOut().write((obj == null) ? "" : obj.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return TagSupport.EVAL_PAGE;
  }

  /**
   * @see TagSupport#release()
   */
  @Override
  public void release() {
    this.contentId = null;
    this.fieldName = null;
    super.release();
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String field) {
    this.fieldName = field;
  }

}
