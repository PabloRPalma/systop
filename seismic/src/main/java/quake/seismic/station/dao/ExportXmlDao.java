package quake.seismic.station.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.station.model.Criteria;

/**
 * 导出台站数据xmlDAO
 * 
 * @author yj
 */
@Repository
@SuppressWarnings("unchecked")
public class ExportXmlDao extends AbstractStationDao {
  /**
   * 台站
   */
  private Map station = null;
  private String schema;

  /**
   * 获得台站
   */
  public void getSta(String id) {
    Criteria c = new Criteria();
    c.setId(id);
    c.setSchema(schema);
    List<Map> stationList = queryStationById(c);
    if (stationList.size() == 1) {
      station = stationList.get(0);
    }
  }

  /**
   * 导出xml
   */
  public StringBuffer queryForXml(String id, String staSchema) {
    schema = staSchema;
    getSta(id);
    StringBuffer sb = new StringBuffer();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    Element e=new Element("response");
    Document d=new Document(e);
    e.setAttribute("Instr_model", "BBVS-60");
    e.setAttribute("Resp_ID", "60.diff");
    sb.append(d.toString());
    return sb;
  }

  /**
   * 根据查询条件执行测震台站数据查询。
   */
  public List<Map> queryStationById(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_STATION_ID, criteria);
  }

  /**
   * 根据查询条件执行通道数据查询。
   */
  public List<Map> queryChannel(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_CHANNEL, criteria);
  }

}
