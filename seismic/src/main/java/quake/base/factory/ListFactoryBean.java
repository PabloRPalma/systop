package quake.base.factory;

import java.util.ArrayList;

import org.springframework.beans.factory.FactoryBean;
/**
 * 用于生产<code>java.util.List</code>对象.方便使用Annotation方式
 * 注入各种常量。
 * @author SAM
 *
 */
@SuppressWarnings("unchecked")
public class ListFactoryBean implements FactoryBean {
  
  private ArrayList<Object> list = new ArrayList<Object>();
  
  @Override
  public Object getObject() throws Exception {
    return list;
  }

  @Override
  public Class getObjectType() {
    
    return ArrayList.class;
  }

  @Override
  public boolean isSingleton() {
    
    return true;
  }

  /**
   * @param list the list to set
   */
  public void setList(ArrayList<Object> list) {
    this.list = list;
  }

}
