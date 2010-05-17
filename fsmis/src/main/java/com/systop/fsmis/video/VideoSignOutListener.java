package com.systop.fsmis.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;

public class VideoSignOutListener implements SignOutListener {
	private static Logger logger = LoggerFactory
			.getLogger(VideoSignOutListener.class);
	@Autowired
	private UserManager userManager;

	@Override
	public void onSignOut(User user) {
		if (user != null) {
			logger.info("----------video---------->" + "{}退出登录,从而退出视频在线状态",
					user.getId());
			userManager.setVideoOnline(user, VideoConstants.USER_IDLE);
		}
	}

}
