package com.systop.fsmis.video;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ResourceExistException;
import org.red5.server.api.stream.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.fsmis.video.room.model.Room;
import com.systop.fsmis.video.room.service.RoomManager;
import com.systop.fsmis.video.util.IConnectionCallback;
import com.systop.fsmis.video.util.VideoUtils;

/**
 * 这是RED5的核心类，按照Red5规范进行配置，所有的逻辑和功能都在这一个类里面
 * 
 * @author WorkShopers@gmail.com
 * 
 */

public class Application extends MultiThreadedApplicationAdapter implements
		IPendingServiceCallback {

	protected static final Logger logger = LoggerFactory
			.getLogger(Application.class);

	/**
	 * 用于管理{@link User}
	 */
	@Autowired
	private UserManager userManager;
	@Autowired
	private RoomManager roomManager;
	@Autowired
	private TemplateRender templateRender;

	/**
	 * Red5的顶级Scope,用于遍历Red5的Scope树
	 */
	private IScope appScope;

	/**
	 * appStart方法在Red5应用启动启动时会被调用一次<br>
	 * 可以通过在自己的类中覆盖这个方法来完成自己想添加的功能.
	 */
	@Override
	public boolean appStart(IScope app) {
		logger.info("--appStart--------video---------->"
				+ "Red5视频服务启动...SCOPE:{}", app.getClass());
		appScope = app;
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean appConnect(IConnection conn, Object[] params) {
		logger.info("--appConnect--------video---------->" + "用户连接scope:{}",
				conn.getScope().getParent());
		// logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		String userId = null;
		if (params != null && params.length > 0 && params[0] != null) {
			if (params[0] instanceof String) {
				userId = params[0].toString();
			} else if (params[0] instanceof ArrayList) {
				List paramList = (List) params[0];
				userId = paramList.get(0).toString();// 房主id

			}
		}
		// 验证参数是否合法
		if (ArrayUtils.isEmpty(params) || params[0] == null
				|| StringUtils.isBlank(params[0].toString())) {
			logger.info("参数不合法");
			// 本类实现了IPendingServiceCallback,(实现了resultReceived)即可以作为参数传递给call方法
			VideoUtils.call(conn, VideoConstants.CLIENT_METHOD_NOT_LOGIN, null,
					this);
			return false;
		}
		// 验证用户是否登录
		if (!isAlreadyLogin(userId)) {
			logger.info("未登录");
			VideoUtils.call(conn, VideoConstants.CLIENT_METHOD_NOT_LOGIN, null,
					this);
			return false;
		}
		// 如果是assistant登录，----------------------??是否启用??
		/*
		 * if (isVideoOnline(params[0].toString())) {
		 * logger.info("--2--------video---------->" + "用户{}试图重复连接视频",
		 * params[0].toString()); VideoUtils.call(conn,
		 * VideoConstants.CLIENT_METHOD_DUPLE_CONNECT, null, this); return
		 * false; }
		 */

		/*
		 * logger.info("---3-------video---------->" + "客户端连入(appConect){}",
		 * params[0]);
		 */
		// Object[] param = new Object[] { userId };
		return super.appConnect(conn, params);

	}

	/**
	 * 断开视频连接
	 */
	@Override
	public void appDisconnect(IConnection conn) {
		/*
		 * logger.info("--appDisconnect--------video---------->" + "用户{}断开连接",
		 * conn.getAttribute(VideoConstants.USER_CLIENT_ATTR_KEY));
		 */
		super.appDisconnect(conn);
	}

	/**
	 * 加入视频
	 */
	@Override
	public boolean appJoin(IClient client, IScope app) {
		/*
		 * logger.info("--appJoin--------video---------->" + "用户{}加入视频", client
		 * .getAttribute(VideoConstants.USER_CLIENT_ATTR_KEY));
		 */
		return super.appJoin(client, app);
	}

	/**
	 * 离开视频
	 */
	@Override
	public void appLeave(IClient client, IScope app) {
		/*
		 * logger.info("--appLeave--------video---------->" + "用户{}", client
		 * .getAttribute(VideoConstants.USER_CLIENT_ATTR_KEY));
		 */
		super.appLeave(client, app);
	}

	/**
	 * 停止应用?
	 */
	@Override
	public void appStop(IScope app) {
		logger.info("--appStop--------video---------->" + "appStop");
		super.appStop(app);
	}

	/**
	 * 连接房间将用户id和Client对象进行关联
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		String userId = null;
		if (params != null && params.length > 0 && params[0] != null) {
			if (params[0] instanceof String) {
				userId = params[0].toString();
			} else if (params[0] instanceof ArrayList) {
				List paramList = (List) params[0];
				userId = paramList.get(0).toString();
				String roomName = paramList.get(1).toString();// 房间名称
				String roomRemark = paramList.get(2).toString();// 房间备注
				String members = paramList.get(3).toString();// 成员id

				// String actionType = paramList.get(4).toString();//
				// 操作类型(新建/修改)

				User user = userManager.get(Integer.valueOf(userId));

				roomManager.saveRoom(roomName, user, members, roomRemark);

				VideoUtils.doWithScopeConnections(appScope,
						new IConnectionCallback() {
							@Override
							public void doWithConnection(IConnection conn) {
								VideoUtils
										.call(
												conn,
												VideoConstants.CLIENT_METHOD_ON_ROOM_CHANGE,
												new Object[] {}, null);
							}
						});

			}
		}
		conn.getClient().setAttribute(VideoConstants.USER_CLIENT_ATTR_KEY,
				userId);

		/*
		 * logger.info("--roomConnect--------video---------->" +
		 * "客户连入(roomConnect){}", params[0]);
		 */
		return super.roomConnect(conn, params);
	}

	/**
	 * 开启房间
	 */

	public boolean roomCreate(IScope scope) {
		return false;
	}

	/**
	 * 进入房间,调用客户端方法提示欢迎信息
	 */
	@Override
	public boolean roomJoin(IClient client, final IScope room) {

		// 检查房间人数是否超限
		Room r = roomManager.getByName(room.getName());
		if (r != null && r.getMembersCount() >= VideoConstants.MAX_ROOM_MEMBERS) {
			// 显示房间已经满了
			VideoUtils.doWithClientConnections(client,
					new IConnectionCallback() {

						@Override
						public void doWithConnection(IConnection conn) {
							VideoUtils
									.call(
											conn,
											VideoConstants.CLIENT_METHOD_ON_ROOM_IS_FULL,
											new Object[] { room.getName() },
											null);
						}
					});
			return true;
		}
		if (!super.roomJoin(client, room)) {
			return false;
		}

		String id = VideoUtils.getUserIdByClient(client);
		/*
		 * logger.info("--roomJoin--------video---------->" + "{}进入房间{}", id,
		 * room .getName());
		 */

		final User user = userManager.get(Integer.valueOf(id));
		final Integer minIndex = VideoUtils.getMinIndex(room);
		/**
		 * 如果room为null或者room名称为null,则仅仅是作为连接,不作room的任何操作 如果room不为null(有意义的值)
		 * 如果房间确实存着,则加入房间 如果房间不存着,则创建房间
		 */
		Room rom = null;
		// 如果room为null或者房间名称为null,则无理取闹(null)--登录大厅...,不予理睬
		if (room == null || "null".equals(room.getName())) {
			/* logger.info("用户{}登录大厅!!!", user.getName()); */
			return true;
		} else {// 如果room不为null并且房间名称为有意义的值(加入/或者创建)
			// 更新room成员,修改成员的在线状态
			// 如果是修改房间

			rom = roomManager.join(room.getName(), user);
			if (client.getAttribute(VideoConstants.STREAM_CLIENT_ATTR_INDEX) == null) {
				// 将当前加入房间的客户的视频index设置为所在房间最小空闲index
				client.setAttribute(VideoConstants.STREAM_CLIENT_ATTR_INDEX,
						minIndex);
			}

			/*
			 * logger.info("-------->用户{}进入房间,获得视频索引{}", user.getName(),
			 * minIndex);
			 */
		}
		final String roomName = rom.getName();
		final User master = userManager.get(rom.getMaster());
		// 通知所有同房间成员,新用户加入
		VideoUtils.doWithScopeConnections(room, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_JOIN,
						new Object[] { user.getName(), roomName }, null);
			}
		});
		// 显示连接及房间信息
		VideoUtils.doWithClientConnections(client, new IConnectionCallback() {

			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_SHOW_CONN_STATUS,
						new Object[] { roomName, master.getName(),
								user.getName(), minIndex }, null);
			}
		});

		VideoUtils.doWithClientConnections(client, new IConnectionCallback() {

			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_JOIN,
						new Object[] { user.getName(), roomName }, null);

				// ------------------------------------------------------------------------------------------------------

				// 如果当前房间有视频广播,则邀请新来的用户加入
				Set<IClient> clients = room.getClients();
				for (IClient client : clients) {
					String publishedName = (String) client
							.getAttribute(VideoConstants.STREAM_CLIENT_ATTR_KEY);
					User user = userManager.get(Integer.valueOf(VideoUtils
							.getUserIdByClient(client)));
					Integer currentIndex = (Integer) client
							.getAttribute(VideoConstants.STREAM_CLIENT_ATTR_INDEX);
					/* logger.info("%%%%%%%%%%%%%%%%%%%%%%" + currentIndex); */
					if (StringUtils.isNotBlank(publishedName)) {
						/*
						 * logger.info("----------video---------->" +
						 * "恢复视频通话{}", publishedName);
						 */
						/*
						 * logger.info("----->房间成员{}视频index:{}", user.getName(),
						 * currentIndex);
						 */
						VideoUtils
								.call(
										conn,
										VideoConstants.CLIENT_METHOD_ON_SUBSCRIBE_RESUME,
										new Object[] { publishedName,
												user.getName(), currentIndex },
										null);
					}
				}
			}

		});

		return true;
	}

	/**
	 * 当客户端和房间失去连接时被调用
	 * 
	 * @param conn
	 */
	@Override
	public void roomDisconnect(IConnection conn) {
		logger.info("--roomDisconnect--------video---------->"
				+ "room Disconnect");
		super.roomDisconnect(conn);
	}

	/**
	 * 用户离开房间
	 */
	public void roomLeave(IClient client, final IScope room) {
		super.roomLeave(client, room);
		String id = VideoUtils.getUserIdByClient(client);
		final User user = userManager.get(Integer.valueOf(id));
		// 更新room成员,同时更新房间人员人数
		roomManager.leave(room.getName(), user);

		logger.info("用户{}离开房间,并且归还了房间{}", user.getName(), room.getName());
		final Integer currentIndex = Integer.valueOf(client.getAttribute(
				VideoConstants.STREAM_CLIENT_ATTR_INDEX).toString());
		VideoUtils.doWithScopeConnections(room, new IConnectionCallback() {

			@Override
			public void doWithConnection(IConnection conn) {

				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_LEAVE,
						new Object[] { user.getName(),
								roomManager.isMaster(room.getName(), user),
								currentIndex }, null);
			}
		});
		/*
		 * logger.info("--roomLeave--------video---------->" + "用户{}离开了房间{}",
		 * user .getName(), room.getName());
		 */
	}

	/**
	 * Handler method. Called when room scope is stopped. 停止一个房间.
	 * 
	 */
	@Override
	public void roomStop(IScope room) {
		/*
		 * logger.info("--roomStop--------video---------->" + "room{}stop", room
		 * .getName());
		 */
		room.removeAttributes();
		if (roomManager.getDao().exists(room, "name")) {
			roomManager.closeRoom(room.getName());
		}
		super.roomStop(room);
	}

	/**
	 * 会议主人强制关闭会议
	 * 
	 * @param roomName
	 * @param userId
	 */
	public void closeRoom(String roomName) {
		roomManager.closeRoom(roomName);
		//如果在Scope树中有该会议(因为数据库中的rooms和Scope有时候是不同步的),则通知会议中的各个客户端,会议关闭
		if (appScope.hasChildScope(roomName)) {
			IScope scope = appScope.getScope(roomName);
			VideoUtils.doWithScopeConnections(scope, new IConnectionCallback() {

				@Override
				public void doWithConnection(IConnection conn) {

					VideoUtils.call(conn,
							VideoConstants.CLIENT_METHOD_ON_CLOSE_ROOM,
							new Object[] {}, null);
				}
			});
		}
		//通知应用中的所有客户端,房间的状态改变
		VideoUtils.doWithScopeConnections(appScope, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_CHANGE,
						new Object[] {}, null);
			}
		});

	}

	/**
	 * 视频广播.
	 */
	@Override
	public void streamBroadcastStart(final IBroadcastStream stream) {
		super.streamBroadcastStart(stream);

		// 当前连接的user,即视频的发送者
		final User user = VideoUtils.getCurrentUser(userManager);
		/*
		 * logger.info("--streamBroadcastStart--------video---------->" +
		 * "视频广播开始: name{},Scope{},publishedName{}", new Object[] {
		 * stream.getName(), stream.getScope().getName(),
		 * stream.getPublishedName() });
		 */
		VideoUtils.doWithScopeConnections(stream.getScope(),
				new IConnectionCallback() {
					// 回调方法
					@Override
					public void doWithConnection(IConnection conn) {
						// 得到新连接的用户,即视频的接收者
						String clientUser = VideoUtils.getUserIdByClient(conn
								.getClient());

						/*
						 * logger.info("----------video---------->" +
						 * "视频发送者{},接收者{}", user.getName(), client .getName());
						 */
						// 向其他人(需要排除自己)发送接收视频的提示,在系统指定位置的播放器中播放视频
						if (StringUtils.isNotBlank(clientUser)
								&& !StringUtils.equals(clientUser, user.getId()
										.toString())) {
							final Integer currentIndex = Integer
									.valueOf(Red5
											.getConnectionLocal()
											.getClient()
											.getAttribute(
													VideoConstants.STREAM_CLIENT_ATTR_INDEX)
											.toString());
							logger
									.info("%%%%%%%%%%%%%%%%%%%%%%------------------>"
											+ currentIndex);
							VideoUtils
									.call(
											conn,
											VideoConstants.CLIENT_METHOD_ON_STREAM_RECEIVED,
											new Object[] {
													stream.getPublishedName(),
													user.getName(),
													currentIndex }, null);
						}
						// 将视频广播的publishedName存入相应的IClient
						conn.getClient().setAttribute(
								VideoConstants.STREAM_CLIENT_ATTR_KEY,
								stream.getPublishedName());
					}

				});
	}

	/**
	 * 关闭视频广播，通知其他客户端
	 */
	@Override
	public void streamBroadcastClose(final IBroadcastStream stream) {
		final User user = VideoUtils.getCurrentUser(userManager);
		super.streamBroadcastClose(stream);
		/*
		 * logger.info("--streamBroadcastClose--------video---------->" +
		 * "视频广播结束 name {},Scope {},publishedName{}", new Object[] {
		 * stream.getName(), stream.getScope().getName(),
		 * stream.getPublishedName() });
		 */
		// 删除当前scope中的接收视频的客户保存在IClient中的publishedName
		Set<IClient> clients = stream.getScope().getClients();
		for (IClient client : clients) {
			Object obj = client
					.getAttribute(VideoConstants.STREAM_CLIENT_ATTR_KEY);
			if (stream.getPublishedName().equals(obj)) {
				/*
				 * logger.info("----------video---------->" +
				 * "remove stream published name.{}", stream
				 * .getPublishedName());
				 */
				client.removeAttribute(VideoConstants.STREAM_CLIENT_ATTR_KEY);
				break;
			}
		}
		// 停止客户端播放
		VideoUtils.doWithScopeConnections(stream.getScope(),
				new IConnectionCallback() {

					@Override
					public void doWithConnection(IConnection conn) {
						String clientUser = VideoUtils.getUserIdByClient(conn
								.getClient());

						/*
						 * logger.info("----------video---------->" +
						 * "视频关闭:视频发送者{},接收者{}", user.getLoginId(), clientUser);
						 */

						// 向其他人发送停止接收视频的提示
						if (StringUtils.isNotBlank(clientUser)
								&& !StringUtils.equals(clientUser, user.getId()
										.toString())) {
							final Integer currentIndex = Integer
									.valueOf(Red5
											.getConnectionLocal()
											.getClient()
											.getAttribute(
													VideoConstants.STREAM_CLIENT_ATTR_INDEX)
											.toString());
							/* logger.info("视频index{}", currentIndex); */
							VideoUtils
									.call(
											conn,
											VideoConstants.CLIENT_METHOD_ON_SUBSCRIBE_OVER,
											new Object[] { user.getName(),
													currentIndex }, null);
						}
					}

				});
	}

	/**
	 * 客户端发送文字信息方法 客户端调用方法,<code>onSendMsg</code>方法,把信息广播给同一房间所有用户
	 * 
	 * @param msg
	 *            文字信息
	 */
	public void onSendMsg(final String msg) {
		// 得到当前用户(聊天信息发送者)
		final User user = VideoUtils.getCurrentUser(userManager);
		/* logger.info("&&&&&&&&&&&&&&&&&&&" + user.getName()); */
		// 得到当前用户所在房间
		IScope scope = Red5.getConnectionLocal().getScope();
		VideoUtils.doWithScopeConnections(scope, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn, VideoConstants.CLIENT_METHOD_RECEIVE_MSG,
						new Object[] { msg, user.getName() }, null);
			}
		});
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		StringBuffer buf = new StringBuffer("[").append(user.getName()).append("]").append(sf.format(new Date())).append("<br>");
		buf.append(msg);
		roomManager.saveMeetingRecord(scope.getName(), buf.toString());
	}

	/**
	 * 客户端调用方法,房主切换指定用户的麦克风
	 * 
	 * @param index
	 * @param gain
	 */
	public void switchSound(int index, int gain) {
		final Integer currentIndex = index;
		final Integer currentGain = gain;
		// 得到当前用户(聊天信息发送者)
		// final User user = VideoUtils.getCurrentUser(userManager);
		/* logger.info("&&&&&&&&&&&&&&&&&&&" + user.getName()); */
		// 得到当前用户所在房间
		IScope scope = Red5.getConnectionLocal().getScope();
		VideoUtils.doWithScopeConnections(scope, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				IClient client = conn.getClient();
				if (client
						.getAttribute(VideoConstants.STREAM_CLIENT_ATTR_INDEX)
						.toString().equals(currentIndex.toString())) {
					/*
					 * final Integer currentIndex = Integer.valueOf(Red5
					 * .getConnectionLocal().getClient().getAttribute(
					 * VideoConstants.STREAM_CLIENT_ATTR_INDEX) .toString());
					 */
					/*
					 * logger.info("%%%%%%%%%%%%%%%%%%%%%%------------------>" +
					 * currentIndex);
					 */
					/*
					 * logger.info("设定成员{}麦克风音量为{}", VideoUtils
					 * .getUserIdByClient(client), currentGain);
					 */
					VideoUtils.call(conn, VideoConstants.SWITCH_CLIENT_MIC,
							new Object[] { currentGain }, null);
				}

			}
		});
	}

	/**
	 * 客户端调用,创建房间<br>
	 * 由于早就建立了连接,在用户点击"新建房间"按钮时创建房间,所以<br>
	 * 只能是通过调用的方式来进行房间的创建.
	 * 
	 * @return
	 */
	public void createRoom(final String userId, final String roomName,
			final String members, final String roomRemark) {

		// final User user = VideoUtils.getCurrentUser(userManager);
		User user = userManager.get(Integer.valueOf(userId));
		/* logger.info("user{}创建名称为{}的房间", user.getName(), roomName); */

		roomManager.saveRoom(roomName, user, members, roomRemark);
		VideoUtils.doWithScopeConnections(appScope, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_CHANGE,
						new Object[] {}, null);
			}
		});
		// 调用刷新房间列表方法--是否可行?????tmd好像不行啊!!!!
		// refreshRooms();
		/*
		 * //得到当前连接 IConnection conn = Red5.getConnectionLocal(); //调用客户端函数,重新刷新
		 * VideoUtils.call(conn, VideoConstants.CLIENT_METHOD_NOT_LOGIN, null,
		 * this);
		 */
	}

	/**
	 * 客户端调用方法,房间主人强制删除一个房间(所有房客被踢出).
	 * 
	 * @param roomName
	 */
	public void removeRoom(final String roomName, final String userId) {
		/* logger.info("------------------------------->" + roomName); */
		Assert.notNull(roomName);
		Assert.notNull(userId);
		final Room room = roomManager.getByName(roomName);
		// final User user = VideoUtils.getCurrentUser(userManager);
		/* final User user = userManager.get(Integer.valueOf(userId)); */
		if (room != null) {
			/* logger.info("user{}删除名称为{}的房间", user.getName(), room.getName()); */
			roomManager.remove(room);
		}
		IScope scope = appScope.getScope(roomName);
		// room和IScope有时候不同步,程序退出等情况
		if (scope != null) {
			roomStop(scope);
			// 从Scope树中删除指定的房间
			appScope.removeChildScope(scope);
		}
		VideoUtils.doWithScopeConnections(appScope, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_CHANGE,
						new Object[] {}, null);
			}
		});
	}

	/**
	 * 根据用户选择的房间得到房间详细信息
	 * 
	 * @param roomName
	 *            房间名称
	 * @return 保存房间信息的xml文本
	 */
	public String getRoomInfoByName(final String roomName) {
		Assert.notNull(roomName);
		/* logger.info(roomName); */
		final Room room = roomManager.getByName(roomName);

		if (room != null) {
			String members = room.getMembers();

			List<User> memberList = new ArrayList<User>();
			/*
			 * String masterHql =
			 * "select u from User u left join fetch u.dept where u.id = ? ";
			 * User master = (User) roomManager.getDao().query(masterHql,
			 * Integer.valueOf(room.getMaster())).get(0);
			 */
			// User master = roomManager.getDao().get(User.class,
			// Integer.valueOf(room.getMaster()));
			// memberList.add(master);
			if (StringUtils.isNotBlank(members)) {
				String[] membersId = members.split(VideoConstants.SPLITER);
				if (membersId.length > 0) {
					for (String memberId : membersId) {
						if (StringUtils.isBlank(memberId)) {
							continue;
						}
						String memberHql = "select u from User u left join fetch u.dept where u.id = ? ";
						User user = (User) roomManager.getDao().query(
								memberHql, Integer.valueOf(memberId)).get(0);

						// User user = roomManager.getDao().get(User.class,
						// Integer.valueOf(memberId));
						memberList.add(user);
					}
				}
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("room", room);
			parameters.put("members", memberList);

			Template template = new Template("roomInfoXml");
			TemplateContext templateContext = new TemplateContext();
			templateContext.setTemplate(template);
			templateContext.addParameters(parameters);
			StringWriter writer = new StringWriter();
			templateContext.setWriter(writer);
			try {
				templateRender.renderTemplate(templateContext);
			} catch (Exception e) {
				logger.error("An error has occurs. {}", e.getMessage());
				e.printStackTrace();
			}
			/*
			 * logger.info("返回XML数据供客户端刷新房间信息及成员列表");
			 * logger.info(writer.toString());
			 */

			return writer.toString();

		} else {
			return "";
		}
	}

	/**
	 * 客户端调用<br>
	 * 得到已当前人员所在区县人员列表,以填充房间信息中人员列表备选
	 * 
	 * @return
	 */

	public String refreshUsers(String userId, String currentRoomMembersStr) {
		Assert.notNull(userId);

		/*
		 * final List<User> users = userManager.getDao().query(
		 * "select u from User u left join fetch u.dept where u.id <> ?", new
		 * Object[] { Integer.valueOf(userId) });
		 */
		User user = userManager.findObject(
				"select u from User u left join fetch u.dept where u.id = ? ",
				Integer.valueOf(userId));
		Dept dept = roomManager.getCountyByUser(user);
		final List<User> users = roomManager.getUsersByCounty(dept);
		// 得到房间已有成员
		List<User> currentRoomMembers = new ArrayList<User>();
		if (StringUtils.isNotBlank(currentRoomMembersStr)) {
			String[] ids = currentRoomMembersStr.split(VideoConstants.SPLITER);
			for (String id : ids) {
				currentRoomMembers.add(userManager.get(Integer.valueOf(id)));
			}
		}

		// 从人员列表中剔除已有的成员
		users.removeAll(currentRoomMembers);
		users.remove(user);// 在用户列表中排除当前用户自己
		// users.add(userManager.get(Integer.valueOf(userId)));
		/* logger.info("总共{}个用户", users.size()); */
		// 用模板生成xml
		Template template = new Template("userListXml");
		TemplateContext templateCtx = new TemplateContext();
		templateCtx.setTemplate(template);
		templateCtx.addParameter("users", users);
		StringWriter writer = new StringWriter();
		templateCtx.setWriter(writer);
		try {
			templateRender.renderTemplate(templateCtx);
		} catch (Exception e) {
			logger.error("An error has occurs. {}", e.getMessage());
			e.printStackTrace();
		}
		/*
		 * logger.info("返回XML数据供客户端刷新登录人员列表"); logger.info(writer.toString());
		 */
		return writer.toString();
	}

	/**
	 * 得到房间列表,根据当前用户所在区县,列出特定区县下的会议(房间) 1.如果当前用户为区县级用户,即其所在部门为区县
	 * (所在部门为county,user.getDept eq getCountyByUser(user)), 则列出其区县会议和上一级区县的会议
	 * 2.如果当前用户不为区县级用户(某职能部门), (所在部门不为county,user.getDept ne
	 * getCountyByUser(user)) 则只列出其所在区县的会议
	 * 
	 * @return
	 */
	public String refreshRooms(String userId) {
		List<Room> rooms = roomManager.listRooms(userId);

		/* logger.info("总共{}个房间", rooms.size()); */
		// 用模板生成xml
		Template template = new Template("roomListXml");
		TemplateContext templateCtx = new TemplateContext();
		templateCtx.setTemplate(template);
		templateCtx.addParameter("rooms", rooms);
		StringWriter writer = new StringWriter();
		templateCtx.setWriter(writer);
		try {
			templateRender.renderTemplate(templateCtx);
		} catch (Exception e) {
			logger.error("An error has occurs. {}", e.getMessage());
			e.printStackTrace();
		}

		// logger.info("返回XML数据供客户端刷新房间列表"); logger.info(writer.toString());

		return writer.toString();
	}

	/**
	 * 视频录制
	 */
	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		StringBuffer buf = new StringBuffer();
		/* logger.info("视频录制开始"); */
		User user = VideoUtils.getCurrentUser(userManager);
		if (user != null) {
			Calendar c = Calendar.getInstance();
			buf.append(stream.getSaveFilename()).append("_").append(
					c.get(Calendar.YEAR)).append(c.get(Calendar.MONTH)).append(
					c.get(Calendar.DATE)).append(c.get(Calendar.HOUR)).append(
					c.get(Calendar.MINUTE)).append(c.get(Calendar.SECOND));
			try {
				stream.saveAs(buf.toString(), false);
			} catch (IOException e) {
				logger.warn("视频录制IO异常,{}", e.getMessage());
				e.printStackTrace();
			} catch (ResourceNotFoundException e) {
				logger.warn("视频录制，资源未找到。{}", e.getMessage());
				e.printStackTrace();
			} catch (ResourceExistException e) {
				logger.warn("视频录制，资源已经存在{}。", e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 列出当前用户的录制列表
	 * 
	 * @return 返回XML格式的列表。
	 */
	/**
	 * 客户端调用，获取房间视频录制状态
	 */
	/**
	 * 客户端调用，设置房间视频录制状态
	 */
	/**
	 * 验证一个用户是否视频在线
	 * 
	 * @param userId
	 *            要验证的用户的id
	 * @return 是否视频在线
	 */
	@SuppressWarnings("unused")
	private boolean isVideoOnline(String userId) {
		User user = userManager.get(Integer.valueOf(userId));
		return userManager.isVideoOnLine(user);
	}

	/**
	 * 验证指定的用户id是否已经登录
	 * 
	 * @param userId
	 *            指定用户id
	 * @return 是否已经登录
	 */
	private boolean isAlreadyLogin(String userId) {
		User user = userManager.get(Integer.valueOf(userId));
		return userManager.isUserOnline(user);
	}

	/**
	 * IPendingServiceCallback接口所要求实现的方法<br>
	 * Callback that will be executed when the result of a pending service call
	 * has been received.
	 */
	@Override
	public void resultReceived(IPendingServiceCall iPendingServiceCall) {
		logger.info("----------video---------->"
				+ iPendingServiceCall.getServiceMethodName());
		logger.info("----------video---------->"
				+ iPendingServiceCall.getServiceName());
	}

	/**
	 * 切换会议房间的状态
	 * 
	 * @param roomName
	 */
	public void switchRoomStatus(String roomName) {
		roomManager.switchRoomStatus(roomName);

		VideoUtils.doWithScopeConnections(appScope, new IConnectionCallback() {
			@Override
			public void doWithConnection(IConnection conn) {
				VideoUtils.call(conn,
						VideoConstants.CLIENT_METHOD_ON_ROOM_CHANGE,
						new Object[] {}, null);
			}
		});
	}

	/**
	 * 检查同名房间是否存在
	 * 
	 * @param roomName
	 * @return
	 */
	public String checkSameNameRoom(final String roomName) {
		Assert.notNull(roomName);
		Room room = roomManager.getByName(roomName);
		if (room != null) {
			return "TRUE";
		} else {
			return "FALSE";
		}
	}

	public IScope getAppScope() {
		return appScope;
	}

	public void setAppScope(IScope appScope) {
		this.appScope = appScope;
	}

}
