package com.systop.fsmis.member.webapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.member.service.MemberManager;
import com.systop.fsmis.model.Member;

/**
 * 委员会成员管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MemberAction extends ExtJsCrudAction<Member, MemberManager> {

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 保存委员会成员信息
	 */
	@Override
	public String save() {
		getModel().setCounty(loginUserService.getLoginUserCounty(getRequest()));
		return super.save();
	}
	
	/**
	 * 企业信息查询列表
	 */
	@SuppressWarnings("unchecked")
  @Override
	public String index() {
		StringBuffer hql = new StringBuffer("from Member m where 1=1 ");
		List args = new ArrayList();
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			hql.append(" and m.county.id = ?");
			args.add(county.getId());
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql.append(" and m.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		if (StringUtils.isNotBlank(getModel().getDept())) {
			hql.append(" and m.dept like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getDept()));
		}
		hql.append(" order by m.name desc");
		items = getManager().query(hql.toString(), args.toArray());
		generatMobileNum(items);
		return INDEX;
	}
	
	/**
	 * 取得委员会成员的手机号码
	 * @param items
	 */
	private void generatMobileNum(Collection<Member> items) {
		StringBuffer nums = new StringBuffer();
		int hasNum = 0;
		int noNum = 0;
		for (Member member : items ) {
			if (StringUtils.isNotEmpty(member.getMobile())) {
				nums.append(member.getMobile()).append(";");
				hasNum ++;
				if (hasNum % 5 == 0) {
					nums.append("<br/>");
				}
			} else {
				noNum ++;
			}
		}
		getRequest().setAttribute("mobileNums", nums.toString());
		getRequest().setAttribute("hasNum", hasNum);
		getRequest().setAttribute("noNum", noNum);
	}
}
