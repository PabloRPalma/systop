package com.systop.cms.announce.webapp;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.announce.AnnounceConstants;
import com.systop.cms.announce.service.AnnounceManager;
import com.systop.cms.model.Announces;
import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.core.dao.hibernate.PropertySelectors;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * 网站公告action
 * 
 * @author jun
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AnnounceAction extends DefaultCrudAction<Announces, AnnounceManager> {
  /**
   * 查询类别
   */
  private String kind;

  /**
   * 查询Value
   */
  private String kindValue;

  /**
   * 开始日期
   */
  private Date beginDate;

  /**
   * 结束日期
   */
  private Date endDate;

  /**
   * 栏目id
   */
  private String myCatalogId;
  /**
   * 公告列表
   */
  private List<Announces> announceList = new ArrayList<Announces>();

  /**
   * items
   */
  @SuppressWarnings("unchecked")
  private List items = new ArrayList();

  /**
   * @see com.systop.core.webapp.struts2.action.DefaultCrudAction#pageQuery()
   */
  @Override
  protected Page pageQuery() {
    DetachedCriteria dc = setupDetachedCriteria();
    return getManager().pageQuery(PageUtil.getPage(getPageNo(), getPageSize()), dc);
  }

  /**
   * 具体封装公共的查询条件
   */
  protected DetachedCriteria setupDetachedCriteria() {
    DetachedCriteria deCriteria = DetachedCriteria.forClass(getModel().getClass());
    Announces announces = new Announces();
    if (kind != null) {
      if (kind.equals("title")) {
        deCriteria.add(Restrictions.like("title", "%" + kindValue + "%"));
      }
      if (kind.equals("author")) {
        announces.setAuthor(kindValue);
        deCriteria.add(Example.create(announces).enableLike(MatchMode.ANYWHERE)
            .setPropertySelector(PropertySelectors.EXCLUDE_BLANK_STRING));
      }
      if (kind.equals("creatdate")) {
        deCriteria.add(Restrictions.between("creatDate", beginDate, endDate));
      }
    }

    return setupSort(deCriteria);
  }

  /**
   * 从ec的Order中取得排序信息，并用于设置DetachedCriteria对象
   * 
   * @param criteria 被设置的DetachedCriteria对象.
   * @return 设置之后的DetachedCriteria对象.
   */
  protected DetachedCriteria setupSort(DetachedCriteria criteria) {
    criteria.addOrder(Order.desc("creatDate"));
    return criteria;
  }

  /**
   * 执行查询的Action方法.
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String myPageQuery() {
    List<Announces> list = null;
    String sql = "from Announces a order by creatDate";
    list = getManager().query(sql);
    checkOutDate(list);
    items.addAll(announceList);
    return SUCCESS;
  }

  /**
   * 更新公告状态
   * 
   * @return
   */
  public String updateState() {
    getModel().setIsNew(
        AnnounceConstants.NEW.equals(getModel().getIsNew()) ? AnnounceConstants.OLD
            : AnnounceConstants.NEW);
    getManager().save(getModel());
    return SUCCESS;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public String getKindValue() {
    return kindValue;
  }

  public void setKindValue(String kindValue) {
    this.kindValue = kindValue;
  }

  /**
   * 保存网站公告
   */
  @Override
  public String save() {
    if (getModel().getId() == null) {
      getModel().setCreatDate(DateUtil.getCurrentDate());
    }
    if (StringUtils.isBlank("getModel().getIsNew()")) {
      getModel().setIsNew(AnnounceConstants.OLD);
    } else {
      getModel().setIsNew(AnnounceConstants.NEW);
    }
    return super.save();
  }

  /**
   * 返回是与否Map列表（通用）
   */
  public Map<String, String> getStates() {
    return AnnounceConstants.SHOUTYPE_MAP;
  }

  /**
   * 初始化页面操作
   * 
   * @return
   */
  public String newAnnounce() {
    getModel().setShowType(AnnounceConstants.SHOETYPE);
    getModel().setAuthor(UserUtil.getPrincipal(getRequest()).getUsername());
    getModel().setOutTime(new BigDecimal(0));
    return SUCCESS;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  /**
   * 做url转换编码
   * 
   * @param str
   * @return
   */
  public String urlDecode(String str) {
    try {
      return URLDecoder.decode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getMyCatalogId() {
    return myCatalogId;
  }

  public void setMyCatalogId(String myCatalogId) {
    this.myCatalogId = myCatalogId;
  }

  /**
   * 对List中有效期的验证
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public void checkOutDate(List<Announces> list) {
    for (Iterator itr = list.iterator(); itr.hasNext();) {
      Announces announce = (Announces) itr.next();
      if (announce.getOutTime().intValue() != 0) {
        if (dateCompare(DateUtil.getCurrentDate(), DateUtil.getDate(announce.getCreatDate(),
            announce.getOutTime().intValue()))) {
          announceList.add(announce);
        }
      } else {
        announceList.add(announce);
      }
    }
  }

  /**
   * 比较日期大小
   * 
   * @param dat1,dat2
   * @return
   */
  public static boolean dateCompare(Date dat1, Date dat2) {
    boolean dateComPareFlag = true;
    if (dat2.compareTo(dat1) != 1) {
      dateComPareFlag = false; // 
    }
    return dateComPareFlag;
  }

  @SuppressWarnings("unchecked")
  public List getItems() {
    return items;
  }

  @SuppressWarnings("unchecked")
  public void setItems(List items) {
    this.items = items;
  }
}
