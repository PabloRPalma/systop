package quake.seismic.data.phase.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import quake.seismic.data.phase.dao.AbstractPhaseDao;
import quake.seismic.data.phase.model.PhaseCriteria;

import com.systop.core.util.DateUtil;


/**
 * 导出震相数据
 * @author wbb
 */
@SuppressWarnings("unchecked")
@Repository
public class ExportPhaseDao extends AbstractPhaseDao<StringBuffer> {
  @Override
  public StringBuffer query(PhaseCriteria criteria) {
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    StringBuffer buf = new StringBuffer(100000);
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      buf.append(extractData(row));
    }
    return buf;
  }

  /**
   * 导出震相数据
   * @param row
   * @return
   */
  public String extractData(Map row) {
    String data = MessageFormat.format(
        "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18}\n",
        new Object[] {
            row.get("NET_CODE"),
            row.get("STA_CODE"),
            row.get("CHN_CODE"),
            row.get("REC_TYPE"),
            row.get("PHASE_NAME"),
            DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", (Date) row.get("PHASE_TIME")),
            row.get("PHASE_TIME_FRAC"),
            row.get("AMP_TYPE"),
            row.get("AMP"),
            row.get("PERIOD"),
            row.get("WEIGHT"),
            row.get("CLARITY"),
            row.get("WSIGN"),
            row.get("RESI"),
            row.get("MAG_NAME"),
            row.get("MAG_VAL"),
            row.get("DISTANCE"),
            row.get("AZI"),
            row.get("S_P"), });
    return data;
  }
}
