package quake.seismic.data.seed.model;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.xwork.builder.ToStringBuilder;

import com.systop.core.model.BaseModel;
import com.systop.core.util.DateUtil;

/**
 * Rec# Sta Cha Net Loc Start Time End Time Sample Rate Tot Samples
 */
@Entity
@Table(name = "station_seeds", uniqueConstraints = {})
public class StationSeed extends BaseModel {
  private Integer id;
  private String seed;
  private String sta;
  private String cha;
  private String net;
  private String loc;
  private Date startTime;
  private Date endTime;
  private String sampleRate;
  private String totSamples;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID", nullable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSeed() {
    return seed;
  }

  public void setSeed(String seed) {
    this.seed = seed;
  }

  public String getSta() {
    return sta;
  }

  public void setSta(String sta) {
    this.sta = sta;
  }

  public String getCha() {
    return cha;
  }

  public void setCha(String cha) {
    this.cha = cha;
  }

  public String getNet() {
    return net;
  }

  public void setNet(String net) {
    this.net = net;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }
  @Column(name = "start_time")
  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Column(name = "end_time")
  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getSampleRate() {
    return sampleRate;
  }

  public void setSampleRate(String sampleRate) {
    this.sampleRate = sampleRate;
  }

  public String getTotSamples() {
    return totSamples;
  }

  public void setTotSamples(String totSamples) {
    this.totSamples = totSamples;
  }
  
  public StationSeed() {    
  }
  /**
   * 根据参数，构建StationSeed
   * @param args :Rec# Sta Cha Net Loc Start Time End Time Sample Rate Tot Samples
   * @return
   */
  public StationSeed(String []args, String seed) {
    this.seed = seed;
    this.sta = args[1];
    this.cha = args[2];
    this.net = args[3];
    this.loc = args[4];
    this.startTime = convert(args[5]);
    this.endTime = convert(args[6]);
    this.sampleRate = args[7];
    this.totSamples = args[8];
  }
  
  private static Date convert(String st) {
    if(StringUtils.isNotBlank(st)) {
      String time = st.substring(0, st.lastIndexOf("."));
      try {
        return DateUtil.convertStringToDate("yyyy,DDD,HH:mm:ss", time);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
