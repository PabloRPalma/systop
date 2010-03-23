package com.systop.common.catalog;

import java.util.Date;
import java.util.List;

import com.systop.common.map.EntryUtil;

/**
 * Catalog的工具类用于查找类别数据，并存入缓存。
 * 此类为1.0版本所以，原来是查询catalog表。在2.0版本中被maps 和 entries两个表
 * 所替换。但考虑兼容性，保留次类，但内部方法则调用的是EntryUtil中的方法，查询的是
 * maps 和 entries　两个表
 * @author Sam Systop_Guo
 */
public class CatalogUtil {
	
	/**
	 * 细目查询工具类
	 */
	 private EntryUtil entryUtil;

  /**
   * 返回某个类别的全部引用
   * @param catalog 类别名称
   * @return 包含<code>Catalog</code>对象的List，如果指定类别不存在，返回空List
   * @deprecated
   * replaced by <code>EntryUtil.getEntriesBySign(String mapSign)</code>
   * or <code>EntryUtil.getEntriesByTitle(String mapTitle)</code>
   */
  public List getCatalogs(String catalog) {
    return null;
  }

  /**
   * 根据类别和参考值，得到相应的String类型的描述值
   * @param catalog 指定的类别
   * @param referenceValue 参考值
   * @return 描述值
   * replaced by 
   * <code>EntryUtil.getViewTextBySign(String mapSign, String refValue)</code>
   * or <code>EntryUtil.getViewTextBySign
   * (String mapSign, String refValue)</code>
   */
  public String getString(String catalog, String referenceValue) {
    return getEntryUtil().
					getViewTextBySign(catalog, referenceValue); 
  }

  /**
   * 根据指定的类别名称和String类型描述值，返回该描述值对应的参考值
   * @param catalog 类别名称
   * @param stringDesc 描述值
   * @return 参考值
   * replaced by 
   * <code>EntryUtil.getViewTextBySign(String mapSign, String refValue)</code>
   * or <code>EntryUtil.getRefValueBySign
   * (String mapSign, String refValue)</code>
   */
  public String getValueByString(String catalog, String stringDesc) {
    return this.getEntryUtil().getRefValueBySign(catalog, stringDesc);
  }
  
  /**
   * 根据指定的类别和参考值，返回相应的Integer类型的描述值
   * @param catalog 指定类别
   * @param referenceValue 参考值
   * @return 描述值
   * @deprecated
   */
  public Integer getInt(String catalog, String referenceValue) {
  	throw new UnsupportedOperationException("此方法暂不支持...");
  }

  
  /**
   * 根据指定的类别和参考值，返回相应的Float类型的描述值
   * @param catalog 指定类别
   * @param referenceValue 参考值
   * @return 描述值
   * @deprecated
   */
  public Float getFloat(String catalog, String referenceValue) {
  	throw new UnsupportedOperationException("此方法暂不支持...");
  }

  /**
   * 根据指定的类别和参考值，返回相应的Double类型的描述值
   * @param catalog 指定类别
   * @param referenceValue 参考值
   * @return 描述值
   * @deprecated
   */
  public Double getDouble(String catalog, String referenceValue) {
  	throw new UnsupportedOperationException("此方法暂不支持...");
  }
  
  /**
   * 根据指定的类别和参考值，返回相应的Date类型的描述值
   * @param catalog 指定类别
   * @param referenceValue 参考值
   * @return 描述值
   * @deprecated
   */
  public Date getDate(String catalog, String referenceValue) {
  	throw new UnsupportedOperationException("此方法暂不支持...");
  }
  
  /**
   * 根据指定的类别名称和Integer类型描述值，返回该描述值对应的参考值
   * @param catalog 类别名称
   * @param intDesc 描述值
   * @return 参考值
   * @deprecated
   */
  public String getValueByInt(String catalog, Integer intDesc) {
  	throw new UnsupportedOperationException("此方法暂不支持...");
  }

  /**
   * 根据指定的类别名称和Float类型描述值，返回该描述值对应的参考值
   * @param catalog 类别名称
   * @param floatDesc 描述值
   * @return 参考值
   * @deprecated
   */
  public String getValueByFloat(String catalog, Float floatDesc) {
    throw new UnsupportedOperationException("此方法暂不支持...");
  }

  /**
   * 根据指定的类别名称和Double类型描述值，返回该描述值对应的参考值
   * @param catalog 类别名称
   * @param doubleDesc 描述值
   * @return 参考值
   * @deprecated
   */
  public String getValueByDouble(String catalog, Double doubleDesc) {
    throw new UnsupportedOperationException("此方法暂不支持...");
  }

  /**
   * 根据指定的类别名称和Date类型描述值，返回该描述值对应的参考值
   * @param catalog 类别名称
   * @param dateDesc 描述值
   * @return 参考值
   * @deprecated
   */
  public String getValueByDate(String catalog, Date dateDesc) {
    throw new UnsupportedOperationException("此方法暂不支持...");
  }

	public EntryUtil getEntryUtil() {
		return entryUtil;
	}

	public void setEntryUtil(EntryUtil entryUtil) {
		this.entryUtil = entryUtil;
	}
}
