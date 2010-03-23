package com.systop.cms.webapp.taglibs;

import com.systop.cms.model.Content;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * 
 * @author Systop_Guo
 */
public class MoietyArticle extends BaseCMSFreeMarkerTagSupport {

	
	/**打算要显示文章的ID*/
	private Integer articleId;
	
	/**　要显示的字数　*/
	private Integer wordCount = Integer.valueOf("250");
	
	/** 要显示的图片	 */
	private String imgSrc; 
	
	/** 图片宽度 */
	private String imgWidth = "150";
	
	/** 图片高度 */
	private String imgHeight = "150";
	
	/**
	 */
	@Override
	protected String getDefaultTemplate() {
		return "moietyArticle";
	}

	/**
	 */
	@Override
	protected void setTemplateParameters(TemplateContext ctx) {
		assert (getDao() != null);
		Content content = getDao().getObject(Content.class, articleId);
		
		ctx.addParameter("content", content);
		
		if (content != null) { //防止发生空指针
			ctx.addParameter("author" , content.getAuthor() == null 
					?	"" : content.getAuthor().getName());
		} else {
			ctx.addParameter("author" , "");
		}
		
		ctx.addParameter("imgSrc", imgSrc);
		ctx.addParameter("imgWidth", imgWidth);
		ctx.addParameter("imgHeight", imgHeight);
		ctx.addParameter("wordCount", wordCount);
		
		
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

}
