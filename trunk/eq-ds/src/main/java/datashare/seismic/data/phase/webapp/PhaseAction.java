package datashare.seismic.data.phase.webapp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import datashare.admin.ds.service.DataSourceManager;
import datashare.base.webapp.AbstractQueryAction;
import datashare.seismic.data.phase.dao.impl.ExportPhaseDao;
import datashare.seismic.data.phase.dao.impl.GridPhaseDao;
import datashare.seismic.data.phase.model.PhaseCriteria;

/**
 * 震相数据查询
 * @author wbb
 */
@SuppressWarnings({ "unchecked", "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PhaseAction extends AbstractQueryAction<PhaseCriteria> {
  private PhaseCriteria model = new PhaseCriteria();

  /**
   * 用于表格查询的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("gridPhaseDao")
  private GridPhaseDao gridPhaseDao;
  /**
   * 用于表格导出的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("exportPhaseDao")
  private ExportPhaseDao exportPhaseDao;

  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  /**
   * 地震目录查询返回结果
   */
  public String list() {
    if (StringUtils.isNotBlank(model.getTableName())) {
      try {
        // 测震SCHEMA
        model.setSchema(dataSourceManager.getCzSchema());
        List items = gridPhaseDao.query(model);
        getRequest().setAttribute("items", items);
      } catch (Exception e) {
        logger.error("数据查询错误{}", e.getMessage());
        e.printStackTrace();
        clean();
      }
    }
    return "list";
  }

  /**
   * 导出震相数据
   */
  public String exportData() {
    if (StringUtils.isNotBlank(model.getTableName())) {
      // 测震SCHEMA
      model.setSchema(dataSourceManager.getCzSchema());
      StringBuffer buf = exportPhaseDao.query(model);
      render(getResponse(), buf.toString(), "text/html");
      return null;
    } else {
    }
    return null;
  }

  @Override
  public PhaseCriteria getModel() {
    return model;
  }

  public void setModel(PhaseCriteria model) {
    this.model = model;
  }
}
