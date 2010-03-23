package com.systop.common.webapp.menu.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.systop.common.webapp.menu.MenuItem;

/**
 * Implements the MenuItem interface.
 * @author Sam
 * 
 */
public final class MenuItemImpl implements MenuItem {
  /**
   * 菜单级别
   */
  private int level = 1;
  
  /**
   * 是否是分割线
   */
  private boolean isSplit;
  
  /**
   * 菜单图标
   */
  private String icon;
  
  /**
   * 菜单标题
   */
  private String title;

  /**
   * 菜单描述
   */
  private String description;
  
  /**
   * 指向的URL
   */
  private String url;
  
  /**
   * URL目标
   */
  private String target;
  
  /**
   * 子菜单
   */
  private final List<MenuItem> children = new ArrayList();
  
  /**
   * Default Constructor
   */
  public MenuItemImpl() {
  }
  
  /**
   * 初始化菜单级别
   */
  public MenuItemImpl(int level) {
    this.level = level;
  }
 
  public void setDescription(String description) {
    this.description = description;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setSplit(boolean split) {
    this.isSplit = split;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getLevel() {
    return level;
  }

  public Iterator<MenuItem> getChildren() {
    return children.iterator();
  }

  /**
   * Add child item
   */
  public final void addChild(MenuItem child) {
    synchronized (children) {
      children.add(child);
    }
  }

  public boolean isSplit() {
    return isSplit;
  }

  public String getIcon() {
    return icon;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getUrl() {
    return url;
  }

  public String getTarget() {
    return target;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
