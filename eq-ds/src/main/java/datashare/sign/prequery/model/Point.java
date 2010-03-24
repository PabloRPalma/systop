package datashare.sign.prequery.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 前兆数据查询，树型条件筛选中的测点
 * 
 * @author Lunch
 */
public class Point {

  /** 测点id */
  private String id;

  /** 测点名称 */
  private String name;

  /** 本测点对应的测项分量 */
  private List<Item> items = new ArrayList<Item>();

  /** 默认构造 */
  public Point() {
  }

  /**
   * 构造函数
   * 
   * @param id 测点编号
   * @param name 测点名称
   */
  public Point(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  /**
   * 增加测项分量
   * 
   * @param item
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * @return the items
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * @param items the items to set
   */
  public void setItems(List<Item> items) {
    this.items = items;
  }

  /**
   * @return the pointId
   */
  public String getId() {
    return id;
  }

  /**
   * @param pointId the pointId to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the pointName
   */
  public String getName() {
    return name;
  }

  /**
   * @param pointName the pointName to set
   */
  public void setName(String name) {
    this.name = name;
  }
}
