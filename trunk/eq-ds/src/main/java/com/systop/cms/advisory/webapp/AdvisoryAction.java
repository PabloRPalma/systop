package com.systop.cms.advisory.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.cms.advisory.AdvisoryConstants;
import com.systop.cms.advisory.service.AdvisoryManager;
import com.systop.cms.model.Advisorys;
import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * 咨询建议Action
 * 
 * @author Lunch
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdvisoryAction extends DefaultCrudAction<Advisorys, AdvisoryManager> {

  /**
   * 开始日期
   */
  private Date beginDate;

  /**
   * 结束日期
   */
  private Date endDate;

  /**
   * 网站后台管理看到的咨询建议列表，包括未回复的
   * 
   * @return
   */
  public String adminList() {
    Page page = query("admin");
    restorePageData(page);
    return "list";
  }

  /**
   * 网站前台咨询建议列表，不包括未回复内容
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String frontList() {
    Page page = query("front");
    restorePageData(page);
    return "list";
  }

  /**
   * 具体查询，根据参数区分前台后台
   * 
   * @param type
   * @return
   */
  @SuppressWarnings("unchecked")
  private Page query(String type) {
    StringBuffer hql = new StringBuffer("from Advisorys a where a.title is not null ");
    List args = new ArrayList();
    if ("front".equals(type)) {// 前台列表不包含未回复内容
      hql.append("and a.status = ? ");
      args.add(AdvisoryConstants.ANSWER);
    }
    if (getModel().getTitle() != null) {// 标题不为空
      hql.append("and a.title like ? ");
      args.add("%" + getModel().getTitle() + "%");
    }
    if (getBeginDate() != null && getEndDate() != null) {// 查询日期不为空
      hql.append("and (a.creatDate between ? and ?) ");
      args.add(beginDate);
      args.add(endDate);
    }
    hql.append("order by a.creatDate desc");
    Page page = PageUtil.getPage(getPageNo(), getPageSize());
    return getManager().pageQuery(page, hql.toString(), args.toArray());
  }

  /**
   * 返回回复状态操作列表
   */
  public Map<String, String> getAnswer() {
    return AdvisoryConstants.ANSWER_MAP;
  }

  /**
   * 后台管理员回复信息保存
   */
  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.content", message = "请填写回复内容.")}
  )
  @Override
  public String save() {
    getModel().setReDate(DateUtil.getCurrentDate());
    if (StringUtils.isNotBlank(getModel().getReContent())) {
      getModel().setStatus(AdvisoryConstants.ANSWER);
    }
    return super.save();
  }

  /**
   * 网站前台添加咨询建议
   */
  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.title", message = "请填写标题."),
      @RequiredStringValidator(fieldName = "model.name", message = "请填写姓名"),
      @RequiredStringValidator(fieldName = "model.content", message = "请填写具体反馈内容")}
  )
  public String saveFront() {
    getModel().setCreatDate(DateUtil.getCurrentDate());
    // 未回复
    getModel().setStatus(AdvisoryConstants.UNANSWER);
    return super.save();
  }

  /**
   * @return the beginDate
   */
  public Date getBeginDate() {
    return beginDate;
  }

  /**
   * @param beginDate the beginDate to set
   */
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
