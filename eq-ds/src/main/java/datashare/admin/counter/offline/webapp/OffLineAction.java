package datashare.admin.counter.offline.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.UserConstants;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.counter.offline.model.OffLine;
import datashare.admin.counter.offline.service.OffLineManager;



@SuppressWarnings({"serial","unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OffLineAction extends DefaultCrudAction<OffLine,OffLineManager> {


  /**
   * 离线服务起始时间
   */
    private String beginDate;
    /**
     * 离线服务结束时间
     */
    private String endDate;
    
    /**
     * 新增离线服务
     */
    public String editNew(){
      return "edit";
    }    
    
    /**
     * 根据条件查询离线服务信息
     */
    public String index(){
      StringBuffer hql = new StringBuffer("from OffLine where 1 = 1 ");
      List args = new ArrayList();
      if(StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)){
        hql.append("and (time between ? and ?) ");
        args.add(beginDate);
        args.add(endDate);
      }
      if(StringUtils.isNotBlank(getModel().getSubject())){
        hql.append(" and subject like ? ");
        args.add(MatchMode.ANYWHERE.toMatchString(getModel().getSubject()));
      }
      if(StringUtils.isNotBlank(getModel().getIndustry())){
        hql.append(" and industry = ?");
        args.add(getModel().getIndustry());
      }
      logger.debug("查询用hql:" + hql);
      page = getManager().pageQuery(PageUtil.getPage(getPageNo(), getPageSize()), hql.toString(),args.toArray());
      items = page.getData();
      restorePageData(page);
      return "index";
    }
    
    /**
     * 保存离线服务信息
     */
    public String save(){
      if(StringUtils.isBlank(getModel().getSubject())){
        addActionError("请填写离线数据类型！");
        return "edit";
      }
      if(StringUtils.isBlank(getModel().getTime())){
        addActionError("请填写服务时间！");
        return "edit";
      }
     
      if(StringUtils.isBlank(getModel().getUserName())){
        addActionError("请填写接受服务的用户名！");
        return "edit";
      }
      if(StringUtils.isBlank(getModel().getIndustry())){
        addActionError("请选择被服务人所属行业！");
        return "edit";
      }
      if(getModel().getDataFlow() == null){
        addActionError("请填写数据下载量！");
        return "edit";
      }
      
      getManager().save(getModel());
      return "success";
    }
    
    /**
     * 删除离线服务信息
     */
    public String remove(){
      getManager().remove(getModel().getId());
      return "success";
    }
    
    /**
     * 获取用户行业信息
     * @return
     */
    public Map<String, String> getIndustryMap(){
      return UserConstants.INDUSTRY_MAP;
    }
    
    public String getBeginDate() {
      return beginDate;
    }

    public void setBeginDate(String beginDate) {
      this.beginDate = beginDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }
}
