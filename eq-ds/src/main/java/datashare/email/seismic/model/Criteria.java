package datashare.email.seismic.model;


import com.systop.common.modules.security.user.model.User;

import datashare.base.model.PageSchemaAware;

public class Criteria extends PageSchemaAware{
  
  private String startTime;
  
  private String endTime;
  
  private User subscriber = new User();
  
  private String industry;

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public User getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(User subscriber) {
    this.subscriber = subscriber;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

}
