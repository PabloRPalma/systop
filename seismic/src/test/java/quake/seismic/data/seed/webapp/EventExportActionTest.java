package quake.seismic.data.seed.webapp;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;

public class EventExportActionTest extends BaseTransactionalTestCase {
  @Autowired
  private EventExportAction action;
  
  public void testExport() {
    action.setStations(new String[]{"HBE"});
    action.setChannels(new String[]{"BHE"});
    action.setSeed("HE.200912312225.0001.seed");
    action.setFormat("5");
    try {
      action.prepare();
    } catch (Exception e) {
      e.printStackTrace();
    }
    action.export();
  }
}
