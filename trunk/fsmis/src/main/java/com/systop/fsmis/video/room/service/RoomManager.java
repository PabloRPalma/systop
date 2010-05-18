package com.systop.fsmis.video.room.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.video.VideoConstants;
import com.systop.fsmis.video.room.model.Room;
import com.systop.fsmis.video.util.VideoUtils;

@Service
public class RoomManager extends BaseGenericsManager<Room> {
	@Autowired
	private UserManager userManager;

	/**
	 * 创建一个视频房间,默认以房间的创建者作为房间的主人
	 * 
	 * @param roomName
	 * @param user
	 */
	@Transactional
	public Room create(String roomName, User user, String membersStr,
			String roomRemark) {

		Room room = new Room();
		room.setName(roomName);// 视频房间名称,惟一的id
		room.setMaster(user.getId());// 视频房间的主人
		// room.setMembers(user.getId().toString());// 房间成员
		if (membersStr != null) {
			room.setMembers(membersStr);// 房间成员
		} else {
			// room.setMembers(user.getId().toString());// 房间成员
		}

		room.setMembersCount(1);// 房间内成员数量,由于是新创建房间,默认为1
		room.setRemark(roomRemark);
		room.setCreateTime(new Date());
		create(room);
		// 更新当前加入用户的视频状态为"等待"
		userManager.setVideoOnline(user, VideoConstants.USER_WAITING);
		logger.info("用户{}创建了房间{}", user.getName(), roomName);

		return room;
	}

	/**
	 * 设定房间主人<br>
	 * 
	 * @param roomName
	 *            房间名称
	 * @param user
	 *            要设定的房主
	 */
	@Transactional
	public void setMaster(String roomName, User user) {
		Room room = get(roomName);
		if (room != null && user != null) {
			room.setMaster(user.getId());
		}
	}

	/**
	 * 删除一个房间<br>
	 * 
	 * @param room
	 *            要删除的房间实体实例
	 */
	@Transactional
	public void remove(String roomName) {
		Room room = get(roomName);
		Assert.notNull(room);
		List<User> members = getMembers(room);
		// 设定所有该房间成员的视频在线状态为"空闲"
		for (User member : members) {
			userManager.setVideoOnline(member, VideoConstants.USER_IDLE);
			// 删除房间
			getDao().delete(Room.class, room.getName());
		}
	}

	/**
	 * 得到指定房间的所有成员的User的List ,如果room中没有人,则返回empty List
	 * 
	 * @param room
	 *            指定房间
	 * @return 房间成员User的List
	 */
	public List<User> getMembers(Room room) {
		List<User> members = new ArrayList<User>();
		if (room == null) {
			return members;
		}
		// 得到以room成员id和逗号分隔符组成的字符串
		String membersStr = room.getMembers();
		// 遍历各个room成员id,得到User实体实例
		if (StringUtils.isNotBlank(membersStr)) {
			String[] membersIdAry = StringUtils.split(membersStr,
					VideoConstants.SPLITER);
			if (!ArrayUtils.isEmpty(membersIdAry)) {
				for (String memberId : membersIdAry) {
					User user = userManager.get(Integer.valueOf(memberId));
					members.add(user);
				}
			}
		}

		return members;
	}

	/**
	 * 房间中加入一个成员<br>
	 * <ul>
	 * <li>如果房间不存在,则创建房间,来着就是房间主人设定其视频状态(video_online)为"等待"
	 * <li>如果房间存在,则来着为客人,设定视频状态为"等待"
	 * </ul>
	 * 
	 * @param room
	 *            房间实体实例
	 * @param user
	 *            用户实体实例
	 */
	@Transactional
	public Room join(String roomName, User user) {

		// 根据房间名称得到room
		Room room = get(roomName);
		// 如果房间不存在,则该用户作为房主创建房间
		if (room == null) {
			/*
			 * room = create(roomName, user, membersStr, roomRemark);
			 * logger.info("用户{}创建名称为{}的新房间!!!", user.getName(),
			 * room.getName()); // 设定用户状态为等待 userManager.setVideoOnline(user,
			 * VideoConstants.USER_WAITING);
			 */
			// 如果房间存在但当前用户没有加入,则将当前用户加入房间
			logger.error("房间{}不存在!!!", roomName);
		} else if (room.getMembers() != null
				&& room.getMembers().indexOf(user.getId().toString()) != -1
				|| user.getId().equals(room.getMaster())) {
			// 拼接members字符串
			// String members = new StringBuffer(room.getMembers()).append(
			// VideoConstants.SPLITER).append(user.getId()).toString();
			// room.setMembers(members);
			room.setMembersCount(room.getMembersCount() + 1);// 累加房间人数
			getDao().merge(room);

			// 更新当前加入用户的视频状态为"等待"
			userManager.setVideoOnline(user, VideoConstants.USER_WAITING);
			logger.info("用户{}加入{}房间!!!", user.getName(), room.getName());
			logger.info("room{}加入一个成员{}", roomName, user.getId());
		} else {
			logger.info("用户{}已经在Room{}中", user.getId(), roomName);
		}

		return room;
	}

	/**
	 * 修改房间信息方法
	 * 
	 * @param roomName
	 * @param user
	 * @param membersStr
	 * @param roomRemark
	 * @return
	 */
	@Transactional
	public Room saveRoom(String roomName, User user, String membersStr,
			String roomRemark) {
		if (membersStr != null) {
			membersStr = VideoUtils.removeBlock(membersStr, user.getId());
		}
		Room room = get(roomName);
		// 如果房间不存在,则新创建房间
		if (room == null) {
			return create(roomName, user, membersStr, roomRemark);
		}
		// 否则修改房间信息
		room.setMaster(user.getId());
		room.setMembers(membersStr);
		room.setRemark(roomRemark);

		getDao().merge(room);

		return room;
	}

	/**
	 * 判断指定的用户是否是指定房间的主人
	 * 
	 * @param roomName
	 *            房间名称
	 * @param user
	 *            用户
	 * @return 是否是房主
	 */
	public boolean isMaster(String roomName, User user) {
		// 根据房间名称得到房间,如果为null则返回false
		Room room = get(roomName);
		if (room == null) {
			return false;
		}

		// 根据房间的主人(Integer的Id) 是否和指定的用户的id qurals来判断当前用户是否是当前房间的主人
		return room.getMaster().equals(user.getId());
	}

	/**
	 * 用户离开房间
	 * 
	 * @param roomName
	 *            房间名称
	 * @param user
	 *            用户实体实例
	 */
	@Transactional
	public void leave(String roomName, User user) {
		Room room = get(roomName);
		if (room == null) {
			logger.warn("房间{}不存在!", roomName);
			return;
		}
		// 如果用户存在于房间中,则将其id从mebers字符串中剔除
		if (VideoUtils.hasIt(room.getMembers(), user.getId())
				|| room.getMaster().equals(user.getId())) {
			// 问题:房主是否可以提前离开房间?,即:房主如果离开了,其他客人怎样?
			// room.setMembers(VideoUtils.removeBlock(room.getMembers(), user
			// .getId()));
			// 房间人数减1
			if (room.getMembersCount() >= 1) {
				room.setMembersCount(room.getMembersCount() - 1);
				getDao().merge(room);
			}
			logger.info("用户{}离开了,房间{}", user.getName(), roomName);
			logger.info("房间{}当前人数:{}", roomName, room.getMembersCount());
		} else {
			logger.info("用户{}不在room{}内", user.getId(), roomName);
		}

	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Room> list() {
		String hql = "from Room r order by r.createTime desc";
		List<Room> rooms = new ArrayList<Room>();
		rooms = getDao().query(hql);

		if (CollectionUtils.isNotEmpty(rooms)) {
			for (Room room : rooms) {
				User u = (User) getDao().get(User.class, room.getMaster());
				if (u != null) {
					room.setMasterName(u.getName());
				}
			}
		}

		return rooms;
	}

	/**
	 * 保存会议记录,每次有会议成员发文字消息,以累加的方式将文字消息保存到数据库中
	 * 
	 * @param roomName
	 *            房间名称
	 * @param message
	 *            用户累加的会议记录
	 */
	@Transactional
	public void saveMeetingRecord(String roomName, String message) {
		Room room = get(roomName);
		StringBuffer buf = new StringBuffer(room.getMeetingRecord());
		buf.append(message);
		room.setMeetingRecord(buf.toString());

		getDao().merge(room);
	}
}
