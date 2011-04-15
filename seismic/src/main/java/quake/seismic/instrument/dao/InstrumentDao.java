package quake.seismic.instrument.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.instrument.model.Instrument;

import com.systop.core.dao.support.Page;

/**
 * 仪器查询DAO，进行数据查询。
 * @author DU
 *
 */
@Repository
public class InstrumentDao extends AbstractInstrDao<Page>{
  
  /**
   * 仪器查询
   * @param instrument
   */
  @SuppressWarnings("unchecked")
  @Override
  public Page query(Instrument instrument) {
    Assert.notNull(instrument, "instrument is null.");
    logger.info("查询仪器....");
    int count = (Integer) (getTemplate().queryForObject(SQL_COUNT_ID, instrument));
    List<Map> rows = getTemplate().queryForList(SQL_ID, instrument, instrument.getPage()
        .getStartIndex(), instrument.getPage().getPageSize());
    instrument.getPage().setData(setStationOfInstr(rows, instrument));
    instrument.getPage().setRows(count);
        
    return instrument.getPage();
  }
  
  /**
   * 仪器所在台站
   * @param rows
   * @param instrument
   */
  @SuppressWarnings("unchecked")
  public List<Map> setStationOfInstr(List<Map> rows, Instrument instrument) {
    for(Map instr : rows) {
      String netCode = (String)instr.get("Organization_code");
      //仪器序列号
      String instrSn = (String)instr.get("Instru_sn");
      instrument.setNetCode(netCode);
      instrument.setInstrModel(instrSn);
      if(StringUtils.isNotEmpty(instrSn)) {
        //logger.info("仪器所在台站：{}", getTemplate().queryForObject(SQL_STA_INSTR_ID, instrument));
        List<String> staList = getTemplate().queryForList(SQL_STA_INSTR_ID, instrument);
        //logger.debug("仪器：{}, 所在台站数量：{}", instrument.getInstrModel(), staList.size());
        StringBuffer staName = new StringBuffer();
        for(String sta : staList) {
          //logger.debug("所在台站名称：{}", sta);
          staName.append(sta);
          staName.append(" ");
        }
        instr.put("staName", staName.toString());
      }
    }
    
    return rows;
  }
}
