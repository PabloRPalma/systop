package com.systop.common.model;

/**
 * 可以编辑的Object，经常用于在表格中选择(check box)数据的时候。
 * @author Sam
 *
 */
public abstract class BaseEditableObject extends BaseObject {
  /**
   * 数据是否已经改变.
   */
  private transient boolean changed = false;

  /**
   * @return the changed
   */
  public final boolean getChanged() {
    return changed;
  }

  /**
   * @param changed the changed to set
   */
  public final void setChanged(boolean changed) {
    this.changed = changed;
  }  

}
