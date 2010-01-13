package com.systop.common.modules.security.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 用户登录历史信息
 * 
 * @author yj
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USER_LOGIN_HISTORY", schema = "FSMIS")
public class UserLoginHistory extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 姓名 */
	private String userName;
	
	/** 部门 */
	private String loginDept;

	/** 登录时间 */
	private Date loginTime;

	/** 登录IP */
	private String loginIp;

	/** 默认构造 */
	public UserLoginHistory() {

	}

	/** 构造 */
	public UserLoginHistory(String userName, Date loginTime, String loginIp,String loginDept) {
		this.userName = userName;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.loginDept = loginDept;
	}

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "LOGIN_TIME")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "LOGIN_IP")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserLoginHistory other = (UserLoginHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Column(name = "LOGIN_DEPT")
	public String getLoginDept() {
		return loginDept;
	}

	public void setLoginDept(String loginDept) {
		this.loginDept = loginDept;
	}

}
