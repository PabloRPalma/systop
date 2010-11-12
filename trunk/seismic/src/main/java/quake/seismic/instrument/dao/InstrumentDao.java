package quake.seismic.instrument.dao;

import java.util.List;
import java.util.Map;

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
    //int count = (Integer) (getTemplate().queryForObject(SQL_COUNT_ID, instrument));
    List<Map> rows = getTemplate().queryForList(SQL_ID, instrument, instrument.getPage()
        .getStartIndex(), instrument.getPage().getPageSize());
    instrument.getPage().setData(rows);
    instrument.getPage().setRows(4);
    return instrument.getPage();
  }
}
