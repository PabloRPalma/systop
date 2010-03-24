package datashare.sign.prequery.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datashare.sign.prequery.model.Node;

/**
 * 主要用于去除重复查询结果中的重复信息。比如台站重复信息，测点重复信息。 一般情况下从数据库查询可以用DISTINCT 关键字去除重复信息。
 * 但是台站和测点都是从多个数据表中查询的到的，所以有很多重复数据，需要筛选过滤，固写此类
 * 
 * @author Lunch
 */
public final class DistUtil {

  /**
   * 获取无重复的台站station列表
   * 
   * @param nodes
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String> distStationsToMap(List<Node> nodes) {
    if (nodes.isEmpty()) {// 为空直接返回EMPTY_LIST
      return Collections.EMPTY_MAP;
    }
    Map<String, String> nMap = new HashMap<String, String>();
    for (Node n : nodes) {
      nMap.put(n.getStationId(), n.getStationName());
    }
    return nMap;
  }

  /**
   * 获取无重复测点point列表
   * 
   * @param nodes
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String> distPointsToMap(List<Node> nodes) {
    if (nodes.isEmpty()) {// 为空直接返回EMPTY_LIST
      return Collections.EMPTY_MAP;
    }
    Map<String, String> nMap = new HashMap<String, String>();
    for (Node n : nodes) {
      // 台站编码和测点编码组合确定唯一性
      nMap.put(n.getStationId() + "_" + n.getPointId(), n.getPointName());
    }
    return nMap;
  }

  /**
   * 将List<Node>转换成Map<stationId_pointId_itemId, node>
   * 
   * @param nodes
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Node> itemsToMap(List<Node> nodes) {
    if (nodes.isEmpty()) {// 为空直接返回EMPTY_LIST
      return Collections.EMPTY_MAP;
    }
    Map<String, Node> nMap = new HashMap<String, Node>();
    for (Node n : nodes) {
      // 台站编码+测点+测项分量编码组合确定唯一性
      nMap.put(n.getStationId() + "_" + n.getPointId() + "_" + n.getItemId(), n);
    }
    return nMap;
  }
}
