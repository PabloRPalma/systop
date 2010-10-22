package quake.base.factory;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.FactoryBean;

/**
 * 用于生产<code>Map</code>对象.方便使用Annotation方式
 * 注入各种常量。
 * @author SAM
 *
 */
@SuppressWarnings("unchecked")
public class MapFactoryBean implements FactoryBean {
  
  private LinkedHashMap map = new LinkedHashMap();

  @Override
  public Object getObject() throws Exception {
    return map;
  }

  @Override
  public Class getObjectType() {
    return LinkedHashMap.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  /**
   * @param map the map to set
   */
  public void setMap(LinkedHashMap map) {
    this.map = map;
  }

}
