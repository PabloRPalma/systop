package quake.seismic.station.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import quake.base.model.PageSchemaAware;


/**
 * 仪器类型查询条件类
 * @author DU
 *
 */
public class InstrDic extends PageSchemaAware {

  /**
   * 仪器类型查询条件
   */
  private String instrType;

  /**
   * 仪器类型查询条件,根据数据库中类型字符长度查询
   */
  private Integer typeLen;
  
  /**
   * @return the typeLen
   */
  public Integer getTypeLen() {
    return typeLen;
  }

  /**
   * @param typeLen the typeLen to set
   */
  public void setTypeLen(Integer typeLen) {
    this.typeLen = typeLen;
  }

  /**
   * @return the instrType
   */
  public String getInstrType() {
    return instrType;
  }

  /**
   * @param instrType the instrType to set
   */
  public void setInstrType(String instrType) {
    this.instrType = instrType;
  }
  
  /**
   * 构造测震台站数据表名称
   */
  public String getTableName() {
    return "INSTR_DIC";
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, new String[]{"tableName", "page"});
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, new String[]{"tableName", "page"});
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
