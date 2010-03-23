package com.systop.common.model;

import java.io.Serializable;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode();
 * 
 * <p>
 * <a href="BaseObject.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public abstract class BaseObject implements Serializable {
  /**
   * @see Object#toString()
   */
  public abstract String toString();
  
  /**
   * @see Object#equals(Object)
   */
  public abstract boolean equals(Object o);
  
  /**
   * @see Object#equals(Object)
   */
  public abstract int hashCode();
  
}
