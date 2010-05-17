package com.systop.fsmis.video;

import com.systop.common.modules.security.user.model.User;

/**
 * 
 * 用户注销监听器,当用户注销后，{@link SecuritApplicationListener} 会调用
 * <code>SignOutListener</code>的所有的实现类。
 * 
 * @author catstiger@gmail.com
 * 
 */
public interface SignOutListener {
	public void onSignOut(User user);
}
