package com.systop.common.webapp.widgets.tree;

import java.io.Serializable;
import java.util.Set;

/**
 * <code>TreeItem</code>用于描述一个tree结构。
 * @author Sam
 *
 */
public interface TreeItem extends Serializable {

  /**
   *  返回所有子节点
   */
  public abstract Set getChildren();

  /**
   *  返回父节点
   */
  public abstract TreeItem getParent();

}
