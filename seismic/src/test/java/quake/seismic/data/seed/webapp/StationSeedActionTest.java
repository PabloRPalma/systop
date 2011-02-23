package quake.seismic.data.seed.webapp;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import quake.seismic.data.seed.model.StationSeed;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.core.util.DateUtil;

public class StationSeedActionTest extends BaseTransactionalTestCase {
  @Autowired
  private StationSeedAction action;

  public void testExport() {
    action.setChannels(new String[] {"BHN", "BHE"});
    action.setFormat("5");
    StationSeed model = new StationSeed();
    model.setSta("XIL");
    model.setNet("HE");
    try {
      model.setStartTime(DateUtil.convertStringToDate("yyyy-MM-dd", "2010-09-21"));
      model.setEndTime(DateUtil.convertStringToDate("yyyy-MM-dd", "2010-10-10"));
    } catch (ParseException e) {
      e.printStackTrace();
      fail("时间格式错误");
    }
    action.setModel(model);
    try {
      action.prepare();
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
    action.init();
    action.export();
    
    
  }
  
  

}
