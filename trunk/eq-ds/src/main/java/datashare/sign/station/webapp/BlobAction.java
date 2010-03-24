package datashare.sign.station.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;

import datashare.DataType;
import datashare.admin.ds.service.DataSourceManager;
import datashare.base.dao.ibatis.SqlMapClientTemplateFactory;
import datashare.base.webapp.AbstractQueryAction;
import datashare.sign.station.model.BlobCriteria;

/**
 * Blob字段类型显示Action
 * @author du
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings("unchecked")
public class BlobAction extends AbstractQueryAction<BlobCriteria> {

  private final static String SQL_BLOBVAL_ID = "qz.queryBlobValInfo"; 
  
  /**
   * 对应页面传来的查询条件，即model
   */
  private BlobCriteria model = new BlobCriteria();

  /**
   * SqlMapClientTemplate工厂类，用于创建SqlMapClientTemplate
   */
  @SuppressWarnings("unused")
  @Autowired(required = true)
  private SqlMapClientTemplateFactory sqlMapClientTemplateFactory;

  /**
   * SqlMapClientTemplate用于查询数据库
   */
  private SqlMapClientTemplate template;
  
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  private String schemaType;
  /**
   * 数据库表
   */
  private String tableName;

  /**
   * 台站ID
   */
  private String stationId;
  /**
   * 台站测点ID
   */
  private String pointId;

  /**
   * 数据列
   */
  private String columnName;

  /**
   * 查询Blob类型数据，包括前兆数据库和测震数据库中的数据
   * 根据前台页面提供的几个参数：tableName、stationId、pointId、columnName
   * 查询具体的某表中某列的数据
   */
  public String queryBlobVal() {
    template = sqlMapClientTemplateFactory.getTemplate(DataType.SIGN);
    if (StringUtils.isNotEmpty(schemaType)) {
      if (schemaType.equals("CZ")) {
        model.setSchema(dataSourceManager.getCzSchema());
      } else {
        model.setSchema(dataSourceManager.getQzSchema());
      }
    }
    logger.debug("schema:" + schemaType + " 表：" + tableName + " 台站：" + stationId + " 测点：" + pointId
        + " 列：" + columnName);
    if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(columnName)) {
      Map map = (Map) template.queryForObject(SQL_BLOBVAL_ID, model);
      Blob blob = (Blob) map.get("BLOBVAL");
      InputStream in = null;
      try {
        if (blob != null && blob.length() > 0) {
          in = blob.getBinaryStream();
          int byteCount = 0;
          byte[] buffer = new byte[4096];
          int bytesRead = -1;
          while ((bytesRead = in.read(buffer)) != -1) {
            getResponse().getOutputStream().write(buffer, 0, bytesRead);
            if (model.getColumnType().equalsIgnoreCase("WORD")) {
              getResponse().setContentType("application/msword");
              getResponse().setContentType("application/x-download");
              getResponse().addHeader("Content-Disposition", 
                  "attachment;filename=\"" + model.getStationId() + "_" + model.getColumnName() + ".doc\"");
            } else {
              getResponse().setContentType("image/jpeg");
            }
            byteCount += bytesRead;
          }
        } else {
          render(getResponse(), "暂无数据", "text/html");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (in != null) {
            in.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
  
  protected DataType getDataType() {
    return DataType.SIGN;
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
   * @return the columnName
   */
  public String getColumnName() {
    return columnName;
  }

  /**
   * @param columnName the columnName to set
   */
  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  /**
   * @return the model
   */
  public BlobCriteria getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(BlobCriteria model) {
    this.model = model;
  }

  /**
   * @return the stationId
   */
  public String getStationId() {
    return stationId;
  }

  /**
   * @param stationId the stationId to set
   */
  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  /**
   * @return the pointId
   */
  public String getPointId() {
    return pointId;
  }

  /**
   * @param pointId the pointId to set
   */
  public void setPointId(String pointId) {
    this.pointId = pointId;
  }

  /**
   * @return the schemaType
   */
  public String getSchemaType() {
    return schemaType;
  }

  /**
   * @param schemaType the schemaType to set
   */
  public void setSchemaType(String schemaType) {
    this.schemaType = schemaType;
  }
}
