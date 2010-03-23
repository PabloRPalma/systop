package com.systop.cms.webapp.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.systop.cms.Constants;
import com.systop.cms.audit.ArticleAuditWorkflowManager;
import com.systop.cms.model.Attachment;
import com.systop.cms.model.Content;
import com.systop.cms.service.ContentManager;
import com.systop.common.dao.Page;
import com.systop.common.util.CollectionUtil;
import com.systop.common.util.DateUtil;
import com.systop.common.util.ReflectUtil;
import com.systop.common.webapp.struts2.action.BaseDwrAjaxAction;

/**
 * 
 * @author Sam Lee
 * 
 */
public class CMSDojoAction extends BaseDwrAjaxAction {

  /**
   * <tt>Manager</tt> of the <tt>Content</tt>.
   */
  private ContentManager contentManager;

  /**
   * 文章审核工作流
   */
  private ArticleAuditWorkflowManager workflowManager;

  /**
   * 当即将被新建的内容的附件id都默认为－2
   */
  @SuppressWarnings("unused")
  private static final int DEFAULT_ATTACHMENTID = -2;

  public ContentManager getContentManager() {
    return contentManager;
  }

  public void setContentManager(ContentManager contentManager) {
    this.contentManager = contentManager;
  }

  /**
   * 根据父目录Id，列出子目录Id。
   * 
   * @param parentId 父目录Id，如果为<tt>null</tt>,则表示列出第一级目录.
   * @return 将<tt>Content</tt>对象转化为符合 <a href="http://dojotoolkits.org">
   *         dojo</a>要求的Map对象。
   */
  @SuppressWarnings("unchecked")
  public List getFoldersByParentId(Integer parentId) {

    List<Content> folders = contentManager.getContentsByParentId(parentId,
        Constants.TYPE_FOLDER);
    List result = new ArrayList();
    // 转化为符合dojo要求的Map对象.
    for (Content folder : folders) {
      Map map = new HashMap();
      map.put("title", folder.getTitle());
      map.put("objectId", folder.getId());
      map.put("isFolder", StringUtils.equals(folder.getType(),
          Constants.TYPE_FOLDER));
      result.add(map);
    }

    return result;
  }

  /**
   * 保存一个目录
   * 
   * @param parentId 父目录Id
   * @param folder 即将保存的Folder
   */
  public Integer saveFolder(Integer parentId, Content folder) {
    return contentManager.saveFolder(parentId, folder);
  }

  /**
   * 删除所选目录
   * 
   * @param folderId 即将被删除的目录ID
   */
  public void removeFolder(Integer folderId) {
    contentManager.removeCascade(folderId);
  }

  /**
   * 准备编辑所选的内容
   * 
   * @param id 所选内容ID
   * @return
   */
  public Content get(Integer id) {
    return contentManager.get(id);
  }

  /**
   * 更新编辑好的目录
   * 
   * @param folderForm 从前台传来的编辑好的目录
   * @return
   */
  public Integer updateFolder(Content folderForm) {
    return contentManager.updateFolder(folderForm);
  }

  /**
   * 根据目录或内容，列出下级内容
   * 
   * @param parentId 父内容Id
   * @return List of Map
   */
  @SuppressWarnings("unchecked")
  public List getContentsByParentId(Integer parentId) {

    List<Content> contents = contentManager.getContentsByParentId(parentId,
        Constants.TYPE_RICHTEXT);
    List result = new ArrayList();
    for (Content content : contents) {
      Map row = ReflectUtil.toMap(content, new String[] { "id", "title",
          "subtitle", "createTime", "author", "updateTime", "updater",
          "expireDate" }, true);

      convertDateField("createTime", row);
      convertDateField("updateTime", row);
      convertDateField("expireDate", row);
      CollectionUtil.cleanoutNullForMap(row, "&nbsp;");

      result.add(row);
    }

    return result;
  }

  /**
   * 返回指定<code>Content</code>的所有附件（<code>Attachment</code>）
   * @param contentId
   * @return
   */
  public List getAttachments(Integer contentId) {
    return contentManager.getAttachments(contentId);
  }

  /**
   * 
   * @param attachmentId
   */
  public String prepareRemoveAttachment(Integer attachmentId) {
    return contentManager.prepareRemoveAttachment(attachmentId);
  }

  /**
   * 转化制定的Map中的一个entry，使其value由String变为Integer
   * @param map 给定Map
   * @param key 给定Key
   */
  @SuppressWarnings("unchecked")
  private static void convertStringEntry(Map map, String key) {
    if (map.containsKey(key)) {
      if (map.get(key) != null) {
        try {
          map.put(key, Integer.valueOf(map.get(key).toString()));
        } catch (NumberFormatException e) {
          // log.warn("Number format exception" + map.get(key));
        }
      }
    }
  }

  /**
   * 分页查询内容
   * @param filter 查询条件
   * @param sort 排序条件
   * @param pageSize 页容量
   * @param pageNo 页码
   * @return Page.
   * @see ContentManager#query(Map, String[], int, int)
   */
  @SuppressWarnings("unchecked")
  public Page query(Map filter, String[] sort, int pageSize, int pageNo) {

    convertStringEntry(filter, "parent");
    convertStringEntry(filter, "author");
    convertStringEntry(filter, "updater");

    convertDateEntry(filter, "createTime");
    convertDateEntry(filter, "updateTime");

    Object parentId = filter.get("parent");
    if (parentId != null) {
      if (parentId.equals("-1")) { // Id为－1，表示查询根目录
        filter.put("parent", null);
      } else {
        Content parent = contentManager.get(Integer.parseInt(parentId
            .toString()));
        filter.put("parent", parent);
      }
    } else { // Id为null， 表示查询所有目录
      filter.remove("parent");
    }

    Page page = contentManager.query(filter, sort, pageSize, pageNo);

    List<Content> data = page.getData();
    checkSelectedContents(data);
    List mapData = new ArrayList();
    for (Content content : data) {
      Map row = ReflectUtil.toMap(content, new String[] { "id", "title",
          "subtitle", "createTime", "author", "updateTime", "updater",
          "expireDate", "type" , "audited"}, true);
      convertDateField("createTime", row);
      convertDateField("updateTime", row);
      convertDateField("expireDate", row);
      CollectionUtil.cleanoutNullForMap(row, "&nbsp;");
      // 处理change属性，这个属性位于父类，所以不用反射得到
      row.put("changed", Boolean.valueOf(content.getChanged()));

      mapData.add(row);
    }

    return new Page(Page.getStartOfPage(pageNo), page.getTotalCount(),
        pageSize, mapData);
  }

  /**
   * 转化输入参数中的Date字段为真正的Date
   * @param map 制定的Map filter
   * @param fieldName 字段名称
   */
  @SuppressWarnings("unchecked")
  private void convertDateEntry(Map map, String fieldName) {
    if (map == null) {
      return;
    }
    if (map.containsKey(fieldName)) {
      Object dateObj = map.get(fieldName);
      map.put(fieldName, parseDateField(dateObj));
    }
  }

  /**
   * 解析Date字段，可以自动转化日期字符串为Date类型,并且转化为当天的最后一秒
   * @param dateObj 日期对象，可以是日期字符串
   */
  private Date parseDateField(Object dateObj) {
    if (dateObj == null) {
      return null;
    }
    Date date = null;

    if (dateObj instanceof Date) {
      date = (Date) dateObj;
    } else {
      try {
        date = DateUtil.convertStringToDate(((String) dateObj));
      } catch (ParseException e) {
        log.info(e.getMessage());
        Long dt = Long.parseLong((String) dateObj);
        date = new Date(dt.longValue());
      }
    }

    if (date != null) {
      date = DateUtil.lastSecondOfDate(date);
    }

    return date;
  }

  /**
   * 将Date字段转化为文本字段
   * 
   * @param fieldName 字段名称
   * @param row 行Map
   */
  
  private void convertDateField(String fieldName, Map row) {
    if (row.get(fieldName) != null) {
      row.put(fieldName, DateUtil
          .convertDateToString((Date) row.get(fieldName)));
    }
  }

  /**
   * 保存内容（insert or update）
   * @return 被保存的内容
   */
  public Content saveContent(Content toSave, Integer parentId,
      Attachment[] files) {
    if (parentId != null && parentId.intValue() == -1) {
      parentId = null;
    }
    Content content = contentManager.saveContent(toSave, parentId);
    for (int i = 0; i < files.length; i++) {
      Integer attachmentId = files[i].getId();
      if (attachmentId == DEFAULT_ATTACHMENTID) {
        Attachment attachment = new Attachment();
        attachment
            .setPath(com.systop.common.webapp.upload.Constants.DEFAULT_DIRECTORY
                + "/" + files[i].getPath());
        attachment.setIsDel(Constants.NOT_DEL);
        attachment.setContent(content);
        content.getAttachments().add(attachment);
      }
    }
    contentManager.save(content);
    contentManager.removeAttachments(content);
    
    //启动工作流
    if (workflowManager != null) {
      workflowManager.startWorkflow(content.getId());
    }
    return content;
  }
  /**
   * 返回等待审核的文章
   */
  public List getUnaudited() {
    if (workflowManager == null) {
      return Collections.EMPTY_LIST;
    }
    List<Integer> ids = workflowManager
        .getUnauditedArticles("ROLE_CMS_AUDITOR");
    List articles = new ArrayList(ids.size());
    for (Integer id : ids) {
      if (id != null) {
        articles.add(contentManager.get(id));
      }
    }

    return articles;
  }
  /**
   * 文章审核
   * @param articleId 待审核的articleId
   * @param passed 是否通过
   */
  public void audit(Integer articleId, boolean passed) {
    if (workflowManager == null) {
      return;
    }
    
    workflowManager.audit(articleId, passed, "ROLE_CMS_AUDITOR");
  }
  
  /**
   * 选中的Content的id要存放在Session中，这个是session name
   */
  private static final String SELECTED_IDS_NAME = "selectedContentIds";

  /**
   * 选择一个Conent，client通过checkbox选择Content，并通知server端
   * @param contentId 被选择Content的id
   * @param isSelect 是选中(true)还是反选中(false)
   */
  @SuppressWarnings("unchecked")
  public void selectContent(Integer contentId, boolean isSelect) {
    Object obj = getSession().getAttribute(SELECTED_IDS_NAME);
    Set ids = null;
    if (obj == null) {
      ids = Collections.synchronizedSet(new HashSet());
    } else {
      ids = (Set) obj;
    }
    if (isSelect && !ids.contains(contentId)) { // 选中
      ids.add(contentId);
    }

    if (!isSelect && ids.contains(contentId)) { // 反选中
      ids.remove(contentId);
    }

    getSession().setAttribute(SELECTED_IDS_NAME, ids);
  }

  /**
   * 坚持列表中的Content对象，如果id在选中之列，则设置changed属性
   * @param contents 给定的Contnet列表
   */
  private void checkSelectedContents(List<Content> contents) {
    Set ids = (Set) getSession().getAttribute(SELECTED_IDS_NAME);
    if (contents == null || ids == null || ids.size() == 0) {
      return;
    }

    for (Content content : contents) {
      if (ids.contains(content.getId())) {
        content.setChanged(true);
      } else {
        content.setChanged(false);
      }
    }
  }

  /**
   * 删除选中的内容
   * 
   */
  @SuppressWarnings("unchecked")
  public void removeSelected() {
    Set<Integer> ids = (Set) getSession().getAttribute(SELECTED_IDS_NAME);
    if (ids == null || ids.size() == 0) {
      log.debug("no contents selected");
      return;
    }
    Integer[] idArray = ids.toArray(new Integer[] {});
    for (Integer id : idArray) {
      contentManager.removeCascade(id);
      ids.remove(id);
    }

    if (ids.size() == 0) {
      getSession().removeAttribute(SELECTED_IDS_NAME);
    }
  }

  /**
   * 返回内容所在的路径
   * @param id
   * @return
   */
  public List getPath(Integer id) {
    List path = new ArrayList();
    if (id != -1) {
      path = contentManager.getPath(id, path);
    }

    return path;
  }

  public void setWorkflowManager(ArticleAuditWorkflowManager workflowManager) {
    this.workflowManager = workflowManager;
  }

}
