package com.systop.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.systop.cms.Constants;
import com.systop.cms.model.Attachment;
import com.systop.cms.model.Content;
import com.systop.common.dao.DAO;
import com.systop.common.dao.Page;
import com.systop.common.test.BaseTestCase;

/**
 * TestCase of the {@link ContentManager}
 * 
 * @author qian.wang
 */
public class ContentManagerTest extends BaseTestCase {

  /**
   * 测试用常量
   */
  private static final int TEST_TIMES = 6;

  /**
   * @return the instance of the ContentManager
   */
  private ContentManager getContentMgr() {
    return (ContentManager) applicationContext.getBean("contentManager");
  }

  /**
   * test of the {@link ContentManager#addAttachment(Content, Attachment)}
   */
  public void testAddAttachment() {
    Content content = new Content();
    content.setTitle("content's title");
    content.setBody("content's body");
    Attachment attachment = new Attachment();
    attachment.setPath("c:/test.txt");
    this.getContentMgr().addAttachment(content, attachment);
    assertTrue(content.getAttachments().size() > 0);
  }

  /**
   * test of the {@link ContentManager#copyContent(Content, Content)}
   */
  public void testPasteContent() {
    Content targetParent = new Content();
    targetParent.setTitle("targetParent");
    DAO dao = (DAO) applicationContext.getBean("baseDao");
    dao.saveObject(targetParent);

    for (int i = 0; i < TEST_TIMES; i++) {
      Content srcContent = new Content();
      srcContent.setTitle(RandomStringUtils.randomNumeric(TEST_TIMES));
      getContentMgr().save(srcContent);
      getContentMgr().pasteContent(srcContent, targetParent);
    }

    assertEquals(getContentMgr().findByParent(targetParent).size(), TEST_TIMES);
  }

  /**
   * test of the {@link ContentManager#createShortcut(Integer, Content)}
   */
  public void testCreateShortcut() {
    Content targetParent = new Content();
    targetParent.setTitle("testContent");
    DAO dao = (DAO) applicationContext.getBean("baseDao");
    dao.saveObject(targetParent);

    for (int i = 0; i < TEST_TIMES; i++) {
      Content srcContent = new Content();
      srcContent.setTitle(RandomStringUtils.randomNumeric(TEST_TIMES));
      getContentMgr().save(srcContent);
      getContentMgr().createShortcut(srcContent.getId(), targetParent);
    }

    assertEquals(getContentMgr().findByParent(targetParent).size(), TEST_TIMES);
  }

  /**
   * test of the {@link ContentManager#findByParent(Content)}
   */
  public void testFindByParent() {

    Content content = new Content();
    content.setTitle(RandomStringUtils.randomNumeric(TEST_TIMES));

    Content parent = new Content();
    parent.setTitle("parent");
    getContentMgr().save(parent);

    content.setParent(parent);
    parent.getChildren().add(content);
    getContentMgr().save(content);

    // 对parent进行查询结果为>0
    List<Content> contents = getContentMgr().findByParent(parent);

    assertTrue(contents.size() > 0);
  }

  /**
   * 测试parent_id为空的情况
   * 
   */
  public void testFindByParent2() {

    List<Content> contents = getContentMgr().findByParent(null);
    logger.info(contents.size());
    logger.info(contents);

  }

  /**
   * test of the {@link ContentManager#moveContent(Content, Content)}
   */
  public void testMoveContent() {
    Content srcParent = new Content();
    srcParent.setTitle("srcParent");
    getContentMgr().save(srcParent);

    Content targetParent = new Content();
    targetParent.setTitle("targetParent");
    getContentMgr().save(targetParent);

    Integer[] ids = new Integer[TEST_TIMES];
    for (int i = 0; i < TEST_TIMES; i++) {
      Content srcContent = new Content();
      srcContent.setTitle(RandomStringUtils.randomNumeric(TEST_TIMES));
      getContentMgr().pasteContent(srcContent, srcParent);
      ids[i] = srcContent.getId();
    }

    assertEquals(getContentMgr().findByParent(srcParent).size(), TEST_TIMES);
    List<Content> copyContents = getContentMgr().findByParent(srcParent);
    for (Content con : copyContents) {
      getContentMgr().moveContent(con, targetParent);
    }
    assertEquals(getContentMgr().findByParent(srcParent).size(), 0);
    assertEquals(getContentMgr().findByParent(targetParent).size(), TEST_TIMES);
  }

  /**
   * test of the {@link ContentManager#removeContents(Integer[])}
   */
  public void testRemoveContents() {
    Integer[] ids = new Integer[TEST_TIMES];
    for (int i = 0; i < TEST_TIMES; i++) {
      Content content = new Content();
      content.setTitle("titleOfContent");
      getContentMgr().save(content);
      ids[i] = content.getId();
    }

    Content example = new Content();
    example.setTitle("titleOfContent");

    getContentMgr().removeContents(ids);
  }

  /**
   * test of the {@link ContentManager#getContentsByParentId(Integer, String)}
   * 
   */
  public void testGetContentsByParentId() {
    Content root = new Content();
    root.setTitle("root");
    getContentMgr().save(root);
    Content parent = new Content();
    parent.setTitle("parent");
    parent.setParent(root);
    root.getChildren().add(parent);
    getContentMgr().save(parent);
    for (int i = 0; i < TEST_TIMES; i++) {
      Content content = new Content();
      content.setTitle("content's title");
      content.setType(Constants.TYPE_FOLDER);
      content.setParent(parent);
      parent.getChildren().add(content);
      getContentMgr().save(content);
    }
    List<Content> p = getContentMgr().findByParent(root);
    Integer parentId = p.get(0).getId();
    List<Content> folders = getContentMgr().getContentsByParentId(parentId,
        com.systop.cms.Constants.TYPE_FOLDER);

    assertEquals(folders.size(), TEST_TIMES);

  }

  /**
   * test of the {@link ContentManager#updateFolder(Content)}
   * 
   */
  public void testUpdateFolder() {
    Content folder1 = new Content();
    folder1.setTitle("contentTitle");
    folder1.setSubtitle("subtitle");
    getContentMgr().save(folder1);

    List<Content> l = getContentMgr().query("from Content c where c.title=?",
        "contentTitle");
    Content folderForm = l.get(0);
    Content folder = getContentMgr().get(folderForm.getId());
    folder.setTitle(folderForm.getTitle());
    folder.setSubtitle(folderForm.getSubtitle());
    getContentMgr().save(folder);

    assertEquals(folder.getTitle(), folder1.getTitle());

  }

  /**
   * {@link ContentManager#query(java.util.Map, String[], int, int)}
   * 
   */
  public void testQuery() {
    Page page = getContentMgr().query(null, null, times, 1);

    Content parent = new Content();
    parent.setTitle("CCCC");
    parent.setType(Constants.TYPE_FOLDER);
    getContentMgr().save(parent);

    for (int i = 0; i < times; i++) {
      Content c = new Content();
      c.setTitle("TTTTT" + i);
      c.setSubtitle("SSSSS" + i);
      c.setParent(parent);
      parent.getChildren().add(c);
      getContentMgr().save(c);
    }
    Map filter = new HashMap();
    filter.put("title", "C");
    page = getContentMgr().query(filter, null, times, 1);
    assertTrue(page.getData().size() != 0);

    filter = new HashMap();
    filter.put("parent", parent.getId());
    page = getContentMgr()
        .query(filter, new String[] { "title", "asc" }, times, 1);
    assertTrue(page.getData().size() != 0);
  }

  /**
   * 
   * @see ContentManager#saveContent(Content, Integer)
   */
  public void testSaveContent() {
    Content parent = new Content();
    parent.setTitle("");
    parent.setType(Constants.TYPE_FOLDER);
    getContentMgr().save(parent);

    Content c = new Content();
    c.setTitle("");
    c.setType(Constants.TYPE_RICHTEXT);

    Content result = getContentMgr().saveContent(c, parent.getId());
    assertNotNull(result);

    c.setTitle("X");
    result = getContentMgr().saveContent(c, null);
    assertNotNull(result);
  }

  /**
   * @see ContentManager#removeCascade(Integer)
   */
  public void testRemoveCascade() {
    Content parent = new Content();
    parent.setTitle("");
    parent.setType(Constants.TYPE_FOLDER);
    getContentMgr().save(parent);

    for (int i = 0; i < times; i++) {
      Content c = new Content();
      c.setTitle("");
      c.setType(Constants.TYPE_RICHTEXT);
      c.setParent(parent);
      parent.getChildren().add(c);
      getContentMgr().save(c);
  
    getContentMgr().removeCascade(parent.getId());
    }
  }
  /**
   * @see ContentManager#getAttachments(Integer)
   */
  public void testGetAttachments() {
	  Content content = new Content();
	  content.setTitle("aaaaaa");
	  for (int i = 0; i < TEST_TIMES; i++) {
		  Attachment attachment = new Attachment();
		  attachment.setPath("attachment" + i);
		  attachment.setContent(content);
//		  DAO dao = (DAO) applicationContext.getBean("baseDao");
//		  dao.saveObject(attachment);
		  content.getAttachments().add(attachment);		  
	  }
	  getContentMgr().save(content);
	  
	  List attachments = getContentMgr().getAttachments(content.getId());
	  logger.debug(attachments.size());
	  assertEquals(attachments.size(), TEST_TIMES);
  }
  
  /**
   * @see ContentManager#prepareRemoveAttachment(Integer)
   */
  public void testPrepareRemoveAttachment() {
	  Content content = new Content();
	  content.setTitle("aaaaaa");
	  
	  Attachment attachment = new Attachment();
	  attachment.setPath("attachment");
	  attachment.setContent(content);
	  content.getAttachments().add(attachment);	
	  
	  getContentMgr().save(content);

	  Integer attachmentId = attachment.getId();	  
	  assertEquals(attachment.getIsDel(), null);
	  
	  getContentMgr().prepareRemoveAttachment(attachmentId);
	  
      assertEquals(attachment.getIsDel(), Constants.IS_DEL);	  
  }
  
  /**
   * @see ContentManager#removeAttachments(Integer)
   */
  public void testRemoveAttachments() {
	  Content content = new Content();
	  content.setTitle("aaaaaa");
	  for (int i = 0; i < TEST_TIMES; i++) {
		  Attachment attachment = new Attachment();
		  attachment.setPath("attachment" + i);
		  attachment.setContent(content);
		  content.getAttachments().add(attachment);		  
	  }
	  getContentMgr().save(content);
	  Integer contentId = content.getId();
	  List<Attachment> attachments = getContentMgr().getAttachments(contentId);
		for (Attachment attachment : attachments) {
		  attachment.setIsDel(Constants.IS_DEL);
		  DAO dao = (DAO) applicationContext.getBean("baseDao");
		  dao.saveObject(attachment);
		  assertEquals(attachment.getIsDel(), Constants.IS_DEL);
		  logger.debug(attachment.getId());
	    }	  
      logger.debug(attachments.size());
	  getContentMgr().removeAttachments(content);
	  List<Attachment> attachments2 = getContentMgr().getAttachments(contentId);
	  assertEquals(attachments2.size(), 0);
	  logger.debug(attachments2.size());
  }
}
