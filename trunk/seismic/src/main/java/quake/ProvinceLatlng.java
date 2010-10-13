package quake;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProvinceLatlng{
  @Autowired(required=true)
  private JdbcTemplate jdbcTemplate;

  
  private HashMap<String, Map<String, String>> provinceMap;
  
  public ProvinceLatlng(){
    provinceMap = new HashMap<String, Map<String, String>>();
    
    provinceMap.put("BJ", proMap("北京市","40.0952873,116.4341559"));
    provinceMap.put("TJ", proMap("天津市","39.1549372,117.2022294"));
    provinceMap.put("HE", proMap("河北省","39.0537887,115.8621167"));
    provinceMap.put("SX", proMap("山西省","37.6148498,112.3666615"));
    provinceMap.put("NM", proMap("内蒙古","40.84231,111.7488469"));
    provinceMap.put("LN", proMap("辽宁省","41.8073212,123.4304496"));
    provinceMap.put("JL", proMap("吉林省","43.9018227,125.3263554"));
    provinceMap.put("HL", proMap("黑龙江省","46.8801639,127.5078048"));
    provinceMap.put("SH", proMap("上海市","31.2422733,121.4226511"));
    provinceMap.put("JS", proMap("江苏省","32.9356691,119.8426651"));
    provinceMap.put("ZJ", proMap("浙江省","29.3058935,120.0746238"));
    provinceMap.put("AH", proMap("安徽省","31.8657794,117.2869834"));
    provinceMap.put("FJ", proMap("福建省","26.1702403,118.1904673"));
    provinceMap.put("JX", proMap("江西省","27.3178687,115.4414774"));
    provinceMap.put("SD", proMap("山东省","36.4947756,117.8616796"));
    provinceMap.put("HA", proMap("河南省","34.0254401,113.8227049"));
    provinceMap.put("HN", proMap("湖南省","27.6925421,111.664316"));
    provinceMap.put("GD", proMap("广东省","23.5484038,113.5863651"));
    provinceMap.put("HB", proMap("湖北省","30.52,114.31"));
    provinceMap.put("GX", proMap("广西壮族自治区","23.5972461,109.0928764"));
    provinceMap.put("HI", proMap("海南省","19.3668298,110.1022839"));
    provinceMap.put("SC", proMap("四川省","30.6599297,104.1019364"));
    provinceMap.put("GZ", proMap("贵州省","26.6459309,106.633375"));
    provinceMap.put("YN", proMap("云南省","25.0386434,102.6645155"));
    provinceMap.put("XZ", proMap("西藏藏族自治区","29.6531265,91.1385929"));
    provinceMap.put("CQ", proMap("重庆市","29.7031109,107.3897309"));
    provinceMap.put("SN", proMap("陕西省","34.2929162,108.9468613"));
    provinceMap.put("GS", proMap("甘肃省","36.0612548,103.8343765"));
    provinceMap.put("QH", proMap("青海省","36.2992271,98.0899916"));
    provinceMap.put("NX", proMap("宁夏回族自治区","36.9789575,105.9152421"));
    provinceMap.put("XJ", proMap("新疆维吾尔自治区","41.9853298,86.6252271"));
    provinceMap.put("HK", proMap("香港","43.9676119,87.652168"));
    provinceMap.put("MK", proMap("澳门","43.9676119,87.652168"));
    provinceMap.put("TW", proMap("台湾","43.9676119,87.652168"));
  }
  
  private HashMap<String, String> proMap(String name, String latlng){
    HashMap<String, String> m = new HashMap<String, String>();
    m.put("name", name);
    m.put("latlng", latlng);
    return m;
  }

  /**
   * 获取当前省的经纬度
   * */
  @SuppressWarnings("unchecked")
  public HashMap getCurrentProvince(){
    List<Map<String, Object>> list = jdbcTemplate.queryForList( "SELECT * FROM site_cfg");
    String cms_code = null;
    HashMap currentProvince = null;
    if(list != null && list.size() > 0){
      cms_code = list.get(0).get("cms_code").toString();
      currentProvince = (HashMap)provinceMap.get(cms_code);
    }    
    if(currentProvince == null){
      currentProvince = new HashMap<String, String>();
      currentProvince.put("name", "");
      currentProvince.put("latlng", "");
    }
    return currentProvince;
  }
  
  /**
   * 根据省代码获取省经纬度
   * */
  @SuppressWarnings("unchecked")
  public HashMap<String, String> getProvinceByCode(String netCode){
    HashMap<String, String> province = (HashMap)provinceMap.get(netCode);
    if(province == null){
      province = new HashMap<String, String>();
      province.put("name", "");
      province.put("latlng", "");
    }
    return province;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}