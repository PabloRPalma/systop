package com.systop.fsmis.fscase.sendtype.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.systop.core.webapp.dwr.BaseDwrAjaxAction;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.SendType;

@Component
public class SendTypeDwrAction extends BaseDwrAjaxAction {

	@Autowired(required = true)
	private SendTypeManager sendTypeManager;
	
	/**
	 * 进行排序操作
	 */
	public String sort(List<String> items) {
		if (items != null && !items.isEmpty()) {
			SendType stype = null;
			for (String item : items) {
				String[] id_sort = item.split(":");
				if (id_sort.length == 2) {
					stype = sendTypeManager.get(Integer.valueOf(id_sort[0]));
					stype.setSortId(Integer.valueOf(id_sort[1]));
				} else {
					stype = sendTypeManager.get(Integer.valueOf(id_sort[0]));
					stype.setSortId(null);
					logger.warn(item + ",缺少排序号码.");
				}
				sendTypeManager.save(stype);
			}
		}
		return "success";
	}
}
