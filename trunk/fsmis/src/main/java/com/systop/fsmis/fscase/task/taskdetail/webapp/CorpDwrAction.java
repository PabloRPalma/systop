package com.systop.fsmis.fscase.task.taskdetail.webapp;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.corp.service.CorpManager;
import com.systop.fsmis.model.Corp;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CorpDwrAction extends ExtJsCrudAction<Corp, CorpManager> {
	private List<Corp> corpList;

	@SuppressWarnings("unused")
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private CorpManager corpManager;

	@SuppressWarnings("unchecked")
	public String getCorps() {
		// 得到当前登录用户所在部门的归属机构下的企业
		corpList = corpManager.getDao().query("from Corp c");
		StringBuffer buf = new StringBuffer("[");
		for (Corp c : corpList) {
			buf.append("\"").append(c.getCode());
			buf.append(":").append(c.getName()).append("\"");
			buf.append(",");
		}
		if (new Character(',').equals(buf.charAt(buf.length() - 1))) {
			buf.delete(buf.length() - 1, buf.length());
		}
		buf.append("]");

		return buf.toString();
	}

	public Corp getCorpByCode(String code) {
		Corp corp = corpManager
				.findObject("from Corp c where c.code = ?", code);
		return corp;
	}

	@JSON(name = "corpList")
	public List<Corp> getCorpList() {
		return corpList;
	}

	public void setCorpList(List<Corp> corpList) {
		this.corpList = corpList;
	}
}
