package com.systop.cms.service;

import com.systop.cms.model.Attachment;
import com.systop.cms.model.Content;
import com.systop.common.service.Manager;

/**
 * 附件管理接口
 * @author Sam
 *
 */
public interface AttachmentManager extends Manager<Attachment> {
  /**
   * 为指定的<code>Content</code>添加一个附件（<code>Attachment</code>）
   * @param content 指定的<code>Content</code>
   * @param attachment 添加的附件
   */
  void addAttachment(Content content, Attachment attachment);
  
  /**
   * 删除多个附件，注意，同时要删除附件物理文件
   * @param attachmentIds 被删除附件的id
   */
  void removeAttachments(Integer []attachmentIds);
}
