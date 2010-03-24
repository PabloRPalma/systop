package datashare.sign.prequery.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 前兆数据查询，树型条件筛选中的台站节点
 * 
 * @author Lunch
 */
public class Station {

  /** 台站id */
  private String id;

  /** 台站名称 */
  private String name;

  /** 本台站包含的测点 */
  private List<Point> points = new ArrayList<Point>();

  /**
   * 默认构造
   */
  public Station() {
  }

  /**
   * 构造函数
   * 
   * @param id
   * @param name
   */
  public Station(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  /**
   * 增加测点
   * 
   * @param p
   */
  public void addPoint(Point p) {
    points.add(p);
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the points
   */
  public List<Point> getPoints() {
    return points;
  }

  /**
   * @param points the points to set
   */
  public void setPoints(List<Point> points) {
    this.points = points;
  }

}
