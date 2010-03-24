package datashare.sign.prequery.model;

/**
 * 前兆数据查询，树型条件筛选中的测项分量节点
 * 
 * @author Lunch
 */
public class Item {

  /**
   * 不单纯是测项分量ID,它是一个组合ID。表现形式:[StationId_PointId_ItemId]
   */
  private String id;

  /** 测项分量名称 */
  private String name;

  /** 数据开始时间 */
  private String startDate;

  /** 数据截至时间 */
  private String endDate;

  /** 本测项分量对应查询的表名 */
  private String tableName;

  /** 本测项分量所属的测点 */
  private Point point;

  /**
   * 构造函数
   * 
   * @param id 测项分量编码
   * @param n 数据库查询获得记录信息[包含台站信息，测点信息，测项分量信息]
   */
  public Item(String id, Node n) {
    this.id = id;
    this.name = n.getItemName();
    this.startDate = n.getStartDate();
    this.endDate = n.getEndDate();
    this.tableName = n.getTableName();
  }

  /**
   * @return the itemId
   */
  public String getId() {
    return id;
  }

  /**
   * @param itemId the itemId to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the itemName
   */
  public String getName() {
    return name;
  }

  /**
   * @param itemName the itemName to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @param tableName the tableName to set
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * @return the point
   */
  public Point getPoint() {
    return point;
  }

  /**
   * @param point the point to set
   */
  public void setPoint(Point point) {
    this.point = point;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

}
