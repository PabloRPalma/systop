package com.systop.cms.service;

import java.util.List;
import java.util.Map;

import com.systop.cms.model.Attachment;
import com.systop.cms.model.Content;
import com.systop.common.dao.Page;
import com.systop.common.service.Manager;

/**
 * 内容(<code>Content</code>)管理接口. 
 * 用于对<code>Content</code>进行各种操作，包括CRUD，复制、剪切
 * 粘贴、创建快捷方式等等。
 * @author Sam
 * 
 */
public interface ContentManager extends Manager<Content> {
  /**
   * 列出指定<code>Content</code>下的所有Content，以更新时间(updateTime) 降序排列。
   * @param parent <code>Content</code>
   * @return 包含<code>Content</code>实例的List，如果<code>Content</code>
   *         下没有任何内容，返回空List
   */
  List<Content> findByParent(Content parent);
  
  /**
   * 保存（insert or update）一篇文章
   * @param content 文章内容
   * @param parentId 父目录Id
   * @return 保存之后的Content对象
   */
  Content saveContent(Content content, Integer parentId);
 
  /**
   * 在指定的<code>Content</code>创建某个<code>Content</code>的快捷方式。
   * @param contentId <code>Content</code>的主键
   * @param targetParent 指定的Content，
   * 可以和原始<code>Content</code> 所在的Content重复
   */
  void createShortcut(Integer contentId, Content targetParent);

  /**
   * 移动一个<code>Content</code>到指定的<code>Content</code>
   * @param srcContent 被移动的<code>Content</code>
   * @param targetParent 目的地
   */
  void moveContent(Content srcContent, Content targetParent);

  /**
   * 复制一个<code>Content</code>到指定的<code>Content</code>
   * @param srcContent 被复制的Content
   * @param targetParent 指定的目的地
   */
  void pasteContent(Content srcContent, Content targetParent);

  /**
   * 删除指定的<code>Content</code>，同时删除其快捷方式。
   * @param contentIds 指定的<code>Content</code>的主键s
   */
  void removeContents(Integer[] contentIds);

  /**
   *　把指定attachmentId的（<code>Attachment</code>标记为删除
   * @param attachmentId
   */
  String prepareRemoveAttachment(Integer attachmentId);
  
  /**
   *　删除<code>Content</code>的所有附件（<code>Attachment</code>
   * @param content
   */
  void removeAttachments(Content content);
  /**
   * 为指定的<code>Content</code>添加一个附件（<code>Attachment</code>）
   * @param content 指定的<code>Content</code>
   * @param attachment 添加的附件
   */
  void addAttachment(Content content, Attachment attachment);

  /**
   * 返回指定<code>Content</code>的所有附件（<code>Attachment</code>）
   * @param contentId
   */
  List getAttachments(Integer contentId);
  /**
   * 根据父节点和子节点内容类型，查询子节点。
   * @param parent 指定父节点
   * @param contentType 子节点的内容类型。0 - 普通内容；1 - 目录
   * @return List of folder or empty list.
   */
  List findByParent(Content parent, String contentType);

  /**
   * 根据目录或内容，列出下级内容
   * @param parentId 父内容Id
   * @param contentType 子节点的内容类型。0 - 普通内容；1 - 目录
   * @return List of content or empty list.
   */
  public List getContentsByParentId(Integer parentId, String contentType);

  /**
   * 分页查询
   * @param filter 查询条件，由字段名称和字段值组成的Map对象
   * @param sort 排序字段,第一个元素为排序字段名称，第二个为排序方向（asc,desc）
   * @param pageSize 页容量
   * @param pageNo 当前页码
   * @return List of Content
   *  
   */
  public Page query(Map filter, String[] sort, int pageSize, int pageNo);
  /**
   * 保存一个目录
   * 
   * @param parentId 父目录Id
   * @param folder 即将保存的Folder
   */
  public Integer saveFolder(Integer parentId, Content folder);

  /**
   * 更新编辑好的目录
   * 
   * @param folderForm 从前台传来的编辑好的目录
   * @return
   */
  public Integer updateFolder(Content folderForm);
  /**
   * 删除内容，如果内容是一个目录，则同时删除目录下的内容.
   * @param contentId 被删除的内容id
   */
  public void removeCascade(Integer contentId);
  
  /**
   * 
   * @param parentId
   */
  public List getPath(Integer id, List titles);

}
