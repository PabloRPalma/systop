package com.systop.fsmis.urgentcase.ucgroup.webapp;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.urgentcase.UcConstants;
import com.systop.fsmis.urgentcase.ucgroup.service.UcGroupManager;

/**
 * 应急指挥组维护action
 * 
 * @author yj
 * 
 */
@SuppressWarnings( { "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UcGroupAction extends
		DefaultCrudAction<UrgentGroup, UcGroupManager> {

	@SuppressWarnings("unchecked")
	public Map getIsPublicList() {
		return FsConstants.YN_MAP;
	}

	@SuppressWarnings("unchecked")
	public Map getSortMap() {
		Map SortMap = new LinkedHashMap();
		SortMap.put(UcConstants.INNER, "内部");
		SortMap.put(UcConstants.OUTER, "外部");
		return SortMap;
	}
}
