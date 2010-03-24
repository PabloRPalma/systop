package datashare.base.factory;

import org.springframework.beans.factory.FactoryBean;

/**
 * 用于生产<code>java.lang.String</code>对象，方便使用Annotation方式
 * 注入各种常量。
 * @author Sam
 *
 */
@SuppressWarnings("unchecked")
public class StringFactoryBean implements FactoryBean {
  private String string;
  /**
   * @param string the string to set
   */
  public void setString(String string) {
    this.string = string;
  }


  @Override
  public Object getObject() throws Exception {
    return string;
  }

  
  @Override
  public Class getObjectType() {    
    return String.class;
  }

  @Override
  public boolean isSingleton() {    
    return true;
  }

}
