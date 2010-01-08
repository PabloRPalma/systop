package com.systop.fsmis.fscase.sendtype.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.systop.core.webapp.dwr.BaseDwrAjaxAction;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.SendType;

/**
 * 用于派遣类别的排序
 * @author Lunch
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendTypeDwrAction extends BaseDwrAjaxAction {

	@Autowired(required = true)
	private SendTypeManager sendTypeManager;
	
	/**
	 * 进行排序操作
	 */
	public String sort(List<String> items) {
		if (items != null && !items.isEmpty()) {
			SendType stype = null;
			for (String item : items) {//便利所有参数[id:sortNo,id:sortNo]
				String[] idAndSortNo = item.split(":");
				if (idAndSortNo.length == 2) {
					stype = sendTypeManager.get(Integer.valueOf(idAndSortNo[0]));
					stype.setSortId(Integer.valueOf(idAndSortNo[1]));
				} else {//如果没有给出排序ID设置为null
					stype = sendTypeManager.get(Integer.valueOf(idAndSortNo[0]));
					stype.setSortId(null);
				}
				sendTypeManager.save(stype);
			}
		}
		return "success";
	}
}
