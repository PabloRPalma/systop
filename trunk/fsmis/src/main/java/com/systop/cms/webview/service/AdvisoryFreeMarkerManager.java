package com.systop.cms.webview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.advisory.AdvisoryConstants;
import com.systop.cms.model.Advisorys;
import com.systop.core.service.BaseGenericsManager;;

@Service
public class AdvisoryFreeMarkerManager extends BaseGenericsManager<Advisorys>{
	 /**
	   * 政务参评列表
	   * @param size 显示分页条数
	   * @return
	   */
	  public List<Advisorys> getAdvisorys(int size) {
	    String hql = "from Advisorys where status = ? order by creatDate";
	    List<Advisorys> list = query(hql, 1, size, new Object[]{AdvisoryConstants.ANSWER});
	    return list;
	  }

}
