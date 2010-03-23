package com.systop.cms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.systop.cms.Constants;
import com.systop.cms.model.Attachment;
import com.systop.cms.model.Content;
import com.systop.cms.service.ContentManager;
import com.systop.common.dao.Page;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.UserManager;
import com.systop.common.service.BaseManager;
import com.systop.common.util.ReflectUtil;

/**
 * @see {@link ContentManager}
 * @author qian.wang
 * 
 */
@SuppressWarnings("unchecked")
public class ContentManagerImpl extends BaseManager<Content> implements
    ContentManager {
  /**
   * @see ContentManager#removeCascade(java.lang.Integer)
   */
  public void removeCascade(Integer contentId) {
    Content content = get(contentId);
    if (content == null) {
      return;
    }
    if (StringUtils.equals(content.getType(), Constants.TYPE_FOLDER)) {
      Set<Content> children = content.getChildren();
      if (children != null) {
        for (Content child : children) {
          removeCascade(child.getId());
        }
      }
    }

    removeById(contentId);
  }

  /**
   * 
   */
  private UserManager userManager;

  /**
   * @see ContentManager#addAttachment(Content, Attachment)
   */
  public synchronized void addAttachment(Content content, 
      Attachment attachment) {
    attachment.setContent(content);
    content.getAttachments().add(attachment);
    save(content);
  }

  /**
   * @see ContentManager#copyContent(Content, Content)
   */
  public synchronized void pasteContent(Content srcContent, 
      Content targetParent) {
    Content copyedContent = new Content();
    try {
      BeanUtils.copyProperties(copyedContent, srcContent);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return;
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      return;
    }
    copyedContent.setId(null);
    copyedContent.setParent(targetParent);
    targetParent.getChildren().add(copyedContent);
    save(copyedContent);
  }

  /**
   * 根据contentId查找到要建立快捷方式的内容对象，并对其建立快捷方式
   * 
   * @see ContentManager#createShortcut(java.lang.Integer, Content)
   */
  public void createShortcut(Integer contentId, Content targetParent) {
    Content originalContent = this.getObject(contentId);
    Content shortCut = new Content();
    originalContent.getShortcuts().add(shortCut);
    shortCut.setOriginalContent(originalContent);
    shortCut.setParent(targetParent);

    save(shortCut);
  }

  /**
   * @see ContentManager#findByContent(Content)
   */

  public List<Content> findByParent(Content parent) {

    String hsql = null;

    if (parent == null) {
      hsql = "from Content c where c.parent is null and c.type='1'";
      return find(hsql);
    } else {
      hsql = "from Content c where c.parent=?";
      return find(hsql, parent);
    }
  }

  /**
   * 移动内容先调用<code>copyContent()</code>方法复制到指定目录中 
   * 第二步调用<code>removeContents()</code>方法将原内容删除
   * 
   * @see ContentManager#moveContent(Content, Content)
   */
  public void moveContent(Content srcContent, Content targetParent) {
    pasteContent(srcContent, targetParent);
    Integer[] contentids = { srcContent.getId() };
    removeContents(contentids);
  }

  /**
   * 删除指定id的所有内容，并删除与每条内容相关联的快捷方式和附件
   * 
   * @see ContentManager#removeContents(java.lang.Integer[])
   */
  public void removeContents(Integer[] contentIds) {
    for (Integer contentId : contentIds) {
      Content content = get(contentId);
      Set<Content> shortCuts = content.getShortcuts();
      if (shortCuts != null) {
        for (Content shortCut : shortCuts) {
          remove(shortCut);
        }
      }
      Set<Attachment> attachments = content.getAttachments();
      if (attachments != null) {
        for (Attachment attachment : attachments) {
          removeObject(attachment);
        }
      }
      remove(content);
    }
  }

  /**
   * (non-Javadoc)
   * 
   * @see ContentManager#findByParent(Content, String)
   */
  public List findByParent(Content parent, String contentType) {
    if (contentType == null) {
      return findByParent(parent);
    }

    if (parent == null) {
      return find("from Content c where c.parent is null and c.type=?",
          contentType);
    } else {
      return find("from Content c where c.parent=? and c.type=?", parent,
          contentType);
    }
  }

  /**
   * (non-Javadoc)
   * 
   * @see ContentManager#getContentsByParentId(Integer,String)
   */
  public List getContentsByParentId(Integer parentId, String contentType) {
    List<Content> contents = null;
    if (parentId == null || parentId.intValue() <= -1) {
      contents = findByParent(null, contentType);
    } else {
      Content parent = get(parentId);
      if (parent != null) {
        contents = findByParent(parent, contentType);
      }
    }

    if (contents == null) {
      return Collections.EMPTY_LIST;
    }
    return contents;
  }

  /**
   * (non-Javadoc)
   * @see ContentManager#saveFolder(Integer, Content)
   */
  public Integer saveFolder(Integer parentId, Content folder) {
    Content parent = null;

    if (parentId != null && parentId.intValue() > 0) {
      parent = get(parentId);
    }

    Date currentTime = new Date();
    // 如果id值为null为说明为新建，反之为编辑
    if (folder.getId() == null) {
      folder.setCreateTime(currentTime);
    }
    folder.setUpdateTime(currentTime);
    folder.setParent(parent);

    if (parent != null) {
      parent.getChildren().add(folder);
    }

    folder.setType(Constants.TYPE_FOLDER);
    save(folder);
    return folder.getId();
  }

  /**
   * (non-Javadoc)
   * @see ContentManager#updateFolder(Content)
   */
  public Integer updateFolder(Content folderForm) {
    if (folderForm.getId() == null) {
      throw new NullPointerException("id.null");
    }

    Content folder = get(folderForm.getId());
    folder.setTitle(folderForm.getTitle());
    folder.setSubtitle(folderForm.getSubtitle());
    folder.setAvailable(folderForm.getAvailable());
    folder.setVisible(folderForm.getVisible());
    folder.setDescn(folderForm.getDescn());
    Date currentTime = new Date();
    folder.setUpdateTime(currentTime);
    folder.setExpireDate(folderForm.getExpireDate());

    save(folder);

    return folder.getId();
  }

  /**
   * @see ContentManager#query(Map, Map, int, int)
   */
  public Page query(Map filter, String[] sort, int pageSize, int pageNo) {

    DetachedCriteria criteria = DetachedCriteria.forClass(Content.class);
    if (filter != null) {
      // 如果查询条件中没有parent字段，则表示不考虑
      if (filter.containsKey("parent")) {
        // 父目录，可以是ID或Content实例
        if (filter.get("parent") instanceof Content) {
          Content parent = (Content) filter.get("parent");
          criteria.createCriteria("parent").add(
              Restrictions.eq("id", parent.getId()));
        } else if (filter.get("parent") == null) { // 根目录
          criteria.add(Restrictions.isNull("parent"));
        }
      }

      // 作者
      if (filter.containsKey("author") && filter.get("author") != null) {
        if (filter.get("author") instanceof User) {
          User user = (User) filter.get("author");
          criteria.createCriteria("author").add(
              Restrictions.eq("id", user.getId()));
        } else {
          criteria.createCriteria("author").add(
              Restrictions.eq("id", (Integer) filter.get("author")));
        }
      }
      // 更新作者
      if (filter.containsKey("updater") && filter.get("updater") != null) {
        if (filter.get("updater") instanceof User) {
          User user = (User) filter.get("updater");
          criteria.createCriteria("updater").add(
              Restrictions.eq("id", user.getId()));
        } else {
          criteria.createCriteria("updater").add(
              Restrictions.eq("id", (Integer) filter.get("updater")));
        }
      }
      // 类别
      if (filter.containsKey("type") && filter.get("type") != null
          && !StringUtils.isBlank(filter.get("type").toString())) {
        criteria.add(Restrictions.eq("type", (String) filter.get("type")));
      }
      // 创建时间
      if (filter.containsKey("createTime") 
          && filter.get("createTime") != null) {
        criteria.add(Restrictions.lt("createTime", (Date) filter
            .get("createTime")));
      }
      // 更新时间
      if (filter.containsKey("updateTime") 
          && filter.get("updateTime") != null) {
        criteria.add(Restrictions.lt("updateTime", (Date) filter
            .get("updateTime")));
      }

      // 标题
      if (filter.containsKey("title") && filter.get("title") != null
          && !StringUtils.isBlank(filter.get("title").toString())) {
        criteria.add(Restrictions.like("title", filter.get("title").toString(),
            MatchMode.ANYWHERE));
      }
    }

    if (sort != null && sort.length == 2) {
      if ("asc".equalsIgnoreCase(sort[1])) {
        criteria.addOrder(Order.asc(sort[0]));
      } else if ("desc".equalsIgnoreCase(sort[1])) {
        criteria.addOrder(Order.desc(sort[0]));
      }
    }

    return pagedQuery(criteria, pageNo, pageSize);
  }

  public UserManager getUserManager() {
    return userManager;
  }

  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  /**
   * @see {@link ContentManager#saveContent(Content, Integer)}
   */
  public Content saveContent(Content content, Integer parentId) {
    assert (content != null);
    Content toSave = new Content();
    // 如果id != null,表示更新操作
    if (content.getId() != null) {
      toSave = get(content.getId()); // 不考虑乐观并发异常了！！！
    }

    ReflectUtil.copyProperties(toSave, content, new String[] { "title",
        "subTitle", "summary", "expireDate", "available", "body", "isDraft",
        "visible" });

    Date now = new Date();
    // 创建时间，如果以前没有创建时间（新建的时候），则重新设置
    if (toSave.getCreateTime() == null) {
      toSave.setCreateTime(now);
    }
    toSave.setUpdateTime(now); // 更新时间
    toSave.setType(Constants.TYPE_RICHTEXT); // 类别
    // 创建人员和更新人员以后再说

    // 如果是新建目录，则设置父目录
    if (content.getId() == null && parentId != null) {
      Content parent = get(parentId);
      if (parent != null) {
        toSave.setParent(parent);
        parent.getChildren().add(toSave);
      }
    }

    save(toSave);

    return toSave;
  }
  
  /**
   * (non-Javadoc)
   * @see ContentManager#getPath(java.lang.Integer, java.util.List)
   */
  public List getPath(Integer id, List path) {
    if (path == null) {
      path = new ArrayList();
    }
    Content content = get(id);
    if (content != null) {
      path.add(content.getTitle());
    }
    if (content.getParent() != null) {
      path = getPath(content.getParent().getId(), path);
    }

    return path;
  }

  /**
   * (non-Javadoc)
   * @see ContentManager#getAttachments(java.lang.Integer)
   */
  public List getAttachments(Integer contentId) {
	  return find("from  Attachment a where a.content.id=?", contentId);
  }

  /**
   * (non-Javadoc)
   * @see ContentManager#prepareRemoveAttachment(java.lang.Integer)
   */
  public String prepareRemoveAttachment(Integer attachmentId) {
//	List<Attachment> attachments = getAttachments(contentId);
//	for (Attachment attachment : attachments) {
//		attachment.setIsDel(Constants.IS_DEL);
//		saveObject(attachment);
//	}
    String hql = "from Attachment a where a.id=?";
	Attachment attachment = (Attachment) find(hql, attachmentId).get(0);
	attachment.setIsDel(Constants.IS_DEL);
	saveObject(attachment);
	
	return attachment.getPath();
  }

  /**
   * (non-Javadoc)
   * @see ContentManager#removeAttachments(java.lang.Integer)
   */
  public void removeAttachments(Content content) {
	//Content content = this.get(contentId);
	String hql = "from Attachment a where a.content=? and a.isDel=?";
	List<Attachment> attachments = find(hql, content, Constants.IS_DEL);
	
	for (int i = 0; i < attachments.size(); i++) {
		Attachment attachment = attachments.get(i);
		content.getAttachments().remove(attachment);
		attachment.setContent(null);
		this.saveObject(attachment);
		this.save(content);
		this.removeObject(attachment);
	}		
  }

  
}
