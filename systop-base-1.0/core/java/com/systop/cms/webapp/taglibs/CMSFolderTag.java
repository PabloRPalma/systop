package com.systop.cms.webapp.taglibs;

import java.util.List;

import com.systop.cms.Constants;
import com.systop.cms.model.Content;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * 用于显示某个Folder中排列在前n位的内容.以更新时间排序.
 * @author Sam Lee
 * 
 */
public class CMSFolderTag extends BaseCMSFreeMarkerTagSupport {
  /**
   * 目录Id
   */
  private Integer folderId;

  /**
   * 目录名字，相当与Content.title.
   */
  private String folderName;

  /**
   * 显示多少行
   */
  private Integer displayRows = DEFAULT_DISPLAY_ROWS;

  /**
   * 指定网页打开的目标
   */
  private String target = "_blank";
  
  /**
   * 打开查看网页的地址页面
   */
  private String openPage;
  
  /**
   * @return target
   */
  public String getTarget() {
		return target;
	}
  
  /**
   * set target
   * @param target
   */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
   * @return the displayRows
   */
  public Integer getDisplayRows() {
    return displayRows;
  }

  /**
   * @param displayRows the displayRows to set
   */
  public void setDisplayRows(Integer displayRows) {
    this.displayRows = displayRows;
  }

  /**
   * @return the folderId
   */
  public Integer getFolderId() {
    return folderId;
  }

  /**
   * @param folderId the folderId to set
   */
  public void setFolderId(Integer folderId) {
    this.folderId = folderId;
  }

  /**
   * @return the folderName
   */
  public String getFolderName() {
    return folderName;
  }

  /**
   * @param folderName the folderName to set
   */
  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  /**
   * 取得指定folder下的content，前displayRows条，安装updateTime降序排列
   */
  @Override
  protected void setTemplateParameters(TemplateContext ctx) {
    
  	if (folderId == null && folderName == null) {
      log.error("Folder Id is null and Folder name too!");
      return;
    }
    assert (getDao() != null);

    Content folder = null;

    if (folderId != null) { // 根据Id取得Folder
      folder = getDao().getObject(Content.class, folderId);
    } else { // 根据名字取得Folder
      List<Content> list = 
        getDao().findByLike(Content.class, "title", folderName);
      if (list == null || list.size() < 1) {
        log.error("You will provide a valid folderId or folderName");
        return;
      }
      folder = list.get(0);
    }
    // 检查folder
    if (folder == null || folder.getType() == null
        || !folder.getType().equals(Constants.TYPE_FOLDER)) {
      log.error("The indicated folder is not a folder id");
      return;
    }

    this.setHTMaxResults(this.displayRows);
    List data = getDao().find("from Content c where c.type='" 
        + Constants.TYPE_RICHTEXT
        + "' and c.parent = ? order by c.createTime desc", folder);    
    
    ctx.addParameter(com.systop.common.Constants.DEFAULT_LIST_NAME, data);
    ctx.addParameter("folderId", folderId);
    ctx.addParameter("title", folder.getTitle());
    ctx.addParameter("openPage", this.openPage);
    ctx.addParameter("target", this.target);
  }

  @Override
  protected String getDefaultTemplate() {
    return "folder";
  }

  /**
   * @see BaseTemplateTagSupport#release()
   */
  @Override
  public void release() {
    this.folderId = null;
    this.displayRows = DEFAULT_DISPLAY_ROWS;
    this.folderName = null;
    super.release();
  }
  
  /**
   * @return openPage
   */
	public String getOpenPage() {
		return openPage;
	}

	/**
	 * set openPage
	 * @param openPage
	 */
	public void setOpenPage(String openPage) {
		this.openPage = openPage;
	}

}
