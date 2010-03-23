package com.systop.common.webapp.taglibs.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.systop.common.Constants;
import com.systop.common.map.EntryUtil;
import com.systop.common.map.model.Entry;
import com.systop.common.util.ReflectUtil;
import com.systop.common.util.ResourceBundleUtil;
import com.systop.common.webapp.taglibs.freemarker.BaseFreeMarkerTagSupport;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * 用于从类别[map]中取得一个&lt;select&gt;\checkbox\radioBox的标签.
 * @author Systop_Guo
 */
public class SystopSelectorTag extends BaseFreeMarkerTagSupport {

	/**
   * name and id of HTML element
   */
  private String name;

  /**
   * HTML element event:onclick
   */
  private String onclick;

  /**
   * HTML element event:onchange
   */
  private String onchange;

  /**
   * HTML element event:onblur
   */
  private String onblur;

  /**
   * CSS class of element
   */
  private String styleClass;

  /**
   * CSS of element
   */
  private String style;

  /**
   * 缺省的被选中的value，通常用于Catalog中没有的引用
   */
  private String defaultValue = "";

  /**
   * 缺省的被选中的lable，通常用于Catalog中没有的引用
   */
  private String defaultLabel = "";

  /**
   * 可否多选
   */
  private Boolean multiLine = Boolean.FALSE;

  /**
   * 所引用的类别.本来此属性应该命名为map更加合适。在2.0版本中类别使用修改了原来的表结构。
   * 但是为了和1.0兼容，仍然保留catalog的命名，这样可以是原来定义的标签不用修改
   */
  private String catalog;

  /**
   * 作为select标签的时候，显示的行数
   */
  private String size;
  
  /**
   * <tt>CatalogUtil</tt>在Spring中定义的BeanId
   */
  private String entryUtilBeanName = "entryUtil";

	/**
	 * @see com.systop.common.webapp.taglibs.freemarker.BaseFreeMarkerTagSupport
	 * 			#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return "catalogSelector";
	}
	
	/**
   * 转化defaultValue和defaultLabel为资源文件中的文本
   * 
   */
  private void convertI18n() {
    if (StringUtils.isBlank(defaultValue) 
        && StringUtils.isBlank(defaultLabel)) {
      return;
    }
    String temp = ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
        defaultValue, "");
    if (!StringUtils.isBlank(temp)) {
      this.defaultValue = temp;
    }
    temp = ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
        defaultLabel, "");
    if (!StringUtils.isBlank(temp)) {
      this.defaultLabel = temp;
    }
  }
	
	/**
	 * @see com.systop.common.webapp.taglibs.freemarker.BaseFreeMarkerTagSupport
	 * #setTemplateParameters(
	 * 				com.systop.common.webapp.taglibs.template.TemplateContext)
	 */
	@Override
	protected void setTemplateParameters(TemplateContext ctx) {
		EntryUtil entryUtil = (EntryUtil) getBean(entryUtilBeanName);
		if (entryUtil == null) {
      return;
    }
		convertI18n(); //将defaultValue和defaultLable转化为I18N数据
		
		List<Entry> entries = entryUtil.getEntriesBySign(catalog);
    // 将Catalog对象，转化为SelectedItem对象，用于简化FTL编程.
    List<SelectableItem> items = new ArrayList();
    Boolean hasSelected = Boolean.FALSE;
    if (!entries.isEmpty()) {
      for (Entry entry : entries) {
        SelectableItem item = new SelectableItem();
        item.setValue(entry.getRefValue());
        item.setLabel(entry.getViewText());
        items.add(item);
      }
    }

    Map map = ReflectUtil.toMap(this, new String[] { "name", "onclick",
        "onchange", "onblur", "style", "styleClass", "defaultValue",
        "defaultLabel", "multiLine", "size" }, true);
    map.put("catalogs", items);
    map.put("hasSelected", hasSelected);
    ctx.addParameters(map);
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getDefaultLabel() {
		return defaultLabel;
	}
	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Boolean getMultiLine() {
		return multiLine;
	}
	public void setMultiLine(Boolean multiLine) {
		this.multiLine = multiLine;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOnblur() {
		return onblur;
	}
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
