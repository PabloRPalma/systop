package quake.seismic.data.seed.service;

import org.springframework.beans.factory.annotation.Autowired;

import quake.seismic.data.seed.service.StationSeedQuartzService;

import com.systop.core.test.BaseTestCase;

public class StationSeedQuartzServiceTest extends BaseTestCase {
  @Autowired
  private StationSeedQuartzService action;
  
  public void testParseStationSeed() {
    action.parseStationSeed();
  }
}
