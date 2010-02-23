package com.systop.fsmis.fscase.sendtype.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.SendType;

/**
 * 针对SendType的操作和显示
 * 
 * @author Lunch
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendTypeAction extends
		DefaultCrudAction<SendType, SendTypeManager> {
	/** 食品安全事件id */
	private Integer caseId;

	/**
	 * 显示列表
	 */
	@Override
	public String index() {
		items = getManager().orderSendType();
		return INDEX;
	}

	/**
	 * 删除，进行简单判断
	 */
	@Override
	public String remove() {
		if (getModel().getId() != null) {
			if (getManager().hasCountySendTypes(getModel().getId())){
				this.addActionError(getModel().getName() + ",存在关联的部门派遣方式,删除失败!");
				return ERROR;
			}
			if (getManager().hasFsCase(getModel().getId())){
				this.addActionError(getModel().getName() + ",存在关联的事件,删除失败!");
				return ERROR;
			}
		}
		return super.remove();
	}

	/**
	 * 选择派遣类别
	 * 
	 * @return
	 */
	public String chooseSendType() {
		// 得到派遣类别集合,以在界面中列举展示
		items = getManager().orderSendType();
		return "chooseSendType";
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
}
