package datashare.sign.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.ecside.core.ECSideConstants;

import com.systop.core.ApplicationException;
import com.systop.core.util.StringUtil;

import datashare.sign.data.SignDataConstants;
import datashare.sign.data.model.DataSeries;
import datashare.sign.data.model.GridResult;

/**
 * 前兆数据动态拼装工具类
 * 
 * @author Lunch
 */
public final class SignDataUtil {

  /**
   * 获得所有GridResult中dataSeries的size 最大值
   * 
   * @param list
   * @return
   */
  @SuppressWarnings( { "unchecked" })
  private static int getMaxSize(List<GridResult> list) {
    int maxSize = 0;
    for (GridResult gridResult : list) {
      if (CollectionUtils.isNotEmpty(gridResult.getDataSeries())) {
        if (maxSize < gridResult.getDataSeries().size()) {
          maxSize = gridResult.getDataSeries().size();
        }
      }
    }
    return maxSize;
  }

  /**
   * 获取dataSeries size 最大的GridResult
   * @param list
   * @return
   */
  public static GridResult getMaxGridResult(List<GridResult> list) {
    int maxSize = getMaxSize(list);
    GridResult grst = null;
    for (GridResult gridResult : list) {
      if (maxSize == gridResult.getDataSeries().size()) {
        grst = gridResult;
        break;
      }
    }
    return grst;
  }

  /**
   * 将多个GridResult中dataSeries的数据，横向组合成一个list.单条数据封装入Map。 根据参数最大尺寸(maxSize)对数据进行封装,不足的部分用空字符串代替
   * 
   * @param list
   * @param maxSize
   * @return
   */
  @SuppressWarnings("unchecked")
  public static List<Map> convertData(List<GridResult> list) {
    int maxSize = getMaxSize(list);
    if (maxSize == 0) {// 如果size最大值为零代表全部都是空数据
      return nullListMaps();
    }
    List<Map> mapDatas = new ArrayList();
    // 安装list最大size值进行遍历，size较小的list数据用空补齐
    for (int i = 0; i < maxSize; i++) {
      Map map = new HashMap();
      int index = 0;
      for (GridResult gridResult : list) {
        List<DataSeries> dataSeries = gridResult.getDataSeries();
        // 最终体现形式[time0 value0, time1 value1, time2 value2]
        if (i < dataSeries.size()) {
          map.put(SignDataConstants.KEY_TIME + index, dataSeries.get(i).getStrTime());
          map.put(SignDataConstants.KEY_VALUE + index, dataSeries.get(i).getValue());
        } else {
          map.put(SignDataConstants.KEY_TIME + index, StringUtil.EMPTY_STRING);
          map.put(SignDataConstants.KEY_VALUE + index, StringUtil.EMPTY_STRING);
        }
        index++;
      }
      mapDatas.add(map);
    }
    return mapDatas;
  }

  /**
   * 获得EcSide动态列所需要的属性，包括titles[], propertys[], widths[].
   * 
   * @param map
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String[]> getColumnInfo(List<Map> mapList) {

    // 如果为nullListMaps()方法返回值，返回空数据列信息
    if (CollectionUtils.isNotEmpty(mapList)
        && mapList.get(0).get(SignDataConstants.NULL_DATA) != null) {
      return nullDataColumnInfo();
    }

    // 存放动态列正确显示所需信息
    Map<String, String[]> columnInfoMap = new HashMap();
    // 获得Map中keySet的个数，即页面中显示的列数
    int keySize = mapList.get(0).keySet().size();
    String[] titles = new String[keySize];// 表头，标题
    List<String> propertys = new ArrayList();// 属性，从Map中取值的key
    String[] widths = new String[keySize]; // 每列的宽度

    for (int i = 0; i < keySize; i++) {
      if (i % 2 == 0) {// 页面中第一列为[时间],同时循环又是从‘0’开始的，所以判断偶数为时间标题
        titles[i] = SignDataConstants.TITLE_TIME;
      } else {// 奇数列为观测值标题
        titles[i] = SignDataConstants.TITLE_VLAUE;
      }
      if (i < keySize / 2) {
        // 设置property,在这里就是从Map取值的key.仔细考虑一下就明白了eg:[time0 value0, time1 value1, time2 value2]
        // 因为此处赋值运算的特殊性,不能采取数组，而是采用了list更加灵活的方式.在后面还有判断propertys的size.确保数据正确
        if (i % 2 == 0) {
          propertys.add(SignDataConstants.KEY_TIME + i);
          propertys.add(SignDataConstants.KEY_VALUE + i);
        } else {
          propertys.add(SignDataConstants.KEY_TIME + i);
          propertys.add(SignDataConstants.KEY_VALUE + i);
        }
      }
      // 设置每列的宽度
      widths[i] = SignDataConstants.WIDTH;
    }

    columnInfoMap.put(SignDataConstants.COL_INFO_TITLES, titles);
    columnInfoMap.put(SignDataConstants.COL_INFO_WIDTHS, widths);
    // 判断propertys's size 是否和keySize相等。只有两者相等在页面取值才会正确，否则抛出异常
    // 同时将propertys转换成String[]
    if (propertys.size() == keySize) {
      columnInfoMap.put(SignDataConstants.COL_INFO_PROPERTYS, propertys
          .toArray(new String[propertys.size()]));
    } else {
      throw new ApplicationException(ECSideConstants.TABLE_WIDTHS_KEY
          + "处理失败,长度于实际数据不一致.propertys:" + propertys.size() + " and columns:" + keySize);
    }

    return columnInfoMap;
  }

  /**
   * 当查询没有数据时返回的动态列信息
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  private static Map<String, String[]> nullDataColumnInfo() {
    Map nullDataColumnInfo = new HashMap();
    nullDataColumnInfo.put(SignDataConstants.COL_INFO_TITLES, new String[] { "提示信息" });
    nullDataColumnInfo.put(SignDataConstants.COL_INFO_WIDTHS, new String[] { "20%" });
    nullDataColumnInfo.put(SignDataConstants.COL_INFO_PROPERTYS,
        new String[] { SignDataConstants.NULL_DATA });
    return nullDataColumnInfo;

  }

  /**
   * 当查询数据为空时，表格给出提示信息
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  private static List<Map> nullListMaps() {
    List nullListMaps = new ArrayList();
    Map m = new HashMap();
    m.put(SignDataConstants.NULL_DATA, "查询无数据");
    nullListMaps.add(m);
    return nullListMaps;
  }

}
