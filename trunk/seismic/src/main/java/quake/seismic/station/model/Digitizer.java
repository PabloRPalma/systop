package quake.seismic.station.model;

import quake.base.model.PageSchemaAware;

public class Digitizer extends PageSchemaAware {

  private String instr_model;
  private String resp_id;

  public String getInstr_model() {
    return instr_model;
  }

  public void setInstr_model(String instrModel) {
    instr_model = instrModel;
  }

  public String getResp_id() {
    return resp_id;
  }

  public void setResp_id(String respId) {
    resp_id = respId;
  }
}
