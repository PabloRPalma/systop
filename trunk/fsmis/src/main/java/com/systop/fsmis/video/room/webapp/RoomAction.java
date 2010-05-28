package com.systop.fsmis.video.room.webapp;


import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.video.VideoConstants;
import com.systop.fsmis.video.room.model.Room;
import com.systop.fsmis.video.room.service.RoomManager;


/**
 * 历史会议
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoomAction extends ExtJsCrudAction<Room, RoomManager>{

	@Autowired
	private LoginUserService loginUserService;
	
	/**
	 * 显示历史会议方法
	 * 
	 * @return
	 */
	public String index(){
		if (loginUserService == null
				|| loginUserService.getLoginUser(getRequest()) == null) {
			addActionError("请先登录!");
			return INDEX;
		}
		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		StringBuffer hql = new StringBuffer("from Room ");
		page = getManager().pageQuery(page, hql.toString());

		restorePageData(page);

		items = page.getData();

		return INDEX;
	}
	
	
	  /**
	   * 会议状态列表返回页面
	   */
	  public Map getRoomMap() {
	    Map RoomMap = new LinkedHashMap();
	    RoomMap.putAll(VideoConstants.ROOM_MAP);

	    return RoomMap;
	  }
}
