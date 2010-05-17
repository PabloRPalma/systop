package com.systop.fsmis.video.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.springframework.util.Assert;

import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;

import com.systop.fsmis.video.VideoConstants;

public final class VideoUtils {
	private VideoUtils() {
	}

	/**
	 * 根据用户id和给定的Scope,找到对应用户的IClient对象
	 * 
	 * @param scope
	 *            给定的IScope
	 * @param user
	 *            给定的用户id
	 * @see {@link com.googlecode.jtiger.modules.security.user.model.User#getId()}
	 * @return 如果没有找到,返回<code>null</code>
	 */
	public static IClient getClient(IScope scope, User user) {
		Assert.isTrue(user != null);

		Set<IClient> clients = scope.getClients();
		for (IClient client : clients) {
			Object obj = client
					.getAttribute(VideoConstants.USER_CLIENT_ATTR_KEY);
			if (user.getId().equals(obj)) {
				return client;
			}
		}

		return null;
	}

	/**
	 * 得到某个IClient对象关联的用户id
	 * 
	 * @param client
	 * @return
	 */
	public static String getUserIdByClient(IClient client) {
		Assert.notNull(client);
		return (String) client
				.getAttribute(VideoConstants.USER_CLIENT_ATTR_KEY);
	}

	/**
	 * 遍历IScope中的所有IConnection对象,并调用IConnectionCallback的实例,对各个IConnection进行处理
	 * 
	 * @param scope
	 *            给定的IScope
	 * @param handler
	 *            给定的IConnectionCallback对象
	 */
	public static void doWithScopeConnections(IScope scope,
			IConnectionCallback handler) {
		// 这个地方挺奇怪,Collection<Set<IConnection>> 为什么弄这么复杂?
		Collection<Set<IConnection>> c = scope.getConnections();
		for (Set<IConnection> conns : c) {
			for (IConnection conn : conns) {
				handler.doWithConnection(conn);
			}
		}
	}

	/**
	 * 遍历IClient中的所有IConnection对象,并调用IConnectionCallback实例,对各个IConnection进行处理
	 * 
	 * @param client
	 *            给定的IClient
	 * @param handler
	 *            给定的IConnectionCallback实现类实例
	 */
	public static void doWithClientConnections(IClient client,
			IConnectionCallback handler) {
		Set<IConnection> conns = client.getConnections();
		for (IConnection conn : conns) {
			handler.doWithConnection(conn);
		}
	}

	/**
	 * 调用某个客户端的方法
	 * 
	 * @param conn
	 *            给定的IConnection实现类实例(代表某一指定客户端)
	 * @param methodName
	 *            欲调用的客户端的方法
	 * @param params
	 *            传递给被调方法的参数
	 * @param callback
	 *            IPendingServiceCallback实现类的实例<br>
	 *            IPendingServiceCallback接口中的方法会在被调用的方法执行后执行
	 */
	public static void call(IConnection conn, String methodName,
			Object[] params, IPendingServiceCallback callback) {
		Assert.isTrue(conn != null || StringUtils.isNotBlank(methodName));
		// IServiceCapableConnection -->Connection that has options to invoke
		// and handle remote calls
		if (conn instanceof IServiceCapableConnection) {
			IServiceCapableConnection iServiceCapableConnection = (IServiceCapableConnection) conn;
			iServiceCapableConnection.invoke(methodName, params, callback);
		} else {
			throw new IllegalArgumentException(
					"The conn param must be an instance of IServiceCapableConnection");
		}
	}

	/**
	 * 判断用指定分隔符分隔的成员字符串中是否包含指定的用户id
	 * 
	 * @param membersStr
	 *            成员字符串
	 * @param userId
	 *            用户id
	 * @return 是否包含
	 */
	public static boolean hasIt(String membersStr, Integer userId) {
		if (StringUtils.isBlank(membersStr)) {
			return false;
		}
		if (userId == null) {
			return false;
		}

		return membersStr.indexOf(userId.toString()) >= 0;
	}

	/**
	 * 删除分隔符分隔的字符串中的一段
	 * 
	 * @param memberStr
	 *            用分隔符分隔的字符串
	 * @param userId
	 *            要删除的一段
	 * @return 剔除后的字符串
	 */
	public static String removeBlock(String memberStr, Integer userId) {
		if (StringUtils.isBlank(memberStr)) {
			return memberStr;
		}
		if (userId == null) {
			return memberStr;
		}

		String[] members = StringUtils.split(memberStr, VideoConstants.SPLITER);
		if (ArrayUtils.isEmpty(members)) {
			return memberStr;
		}
		List<String> list = new ArrayList<String>(50);
		for (String member : members) {
			if (!userId.equals(Integer.valueOf(member))) {
				list.add(member);
			}
		}

		return org.springframework.util.StringUtils
				.collectionToCommaDelimitedString(list);
	}

	public static User getCurrentUser(UserManager userManager) {
		IConnection conn = Red5.getConnectionLocal();
		String currentUser = getUserIdByClient(conn.getClient());

		return userManager.get(Integer.valueOf(currentUser));
	}

	/**
	 * 得到Scope中可用的最小streamIndex
	 * 
	 * @param scope
	 * @return
	 */
	public static Integer getMinIndex(IScope scope) {
		Integer minIndex = 1;
		List<Integer> indexList = new ArrayList<Integer>();
		//从1开始,0留给自己显示自己视频
		for (int i = 0; i < VideoConstants.MAX_ROOM_MEMBERS; i++) {
			indexList.add(i);
		}
		List<Integer> inUseIndex = new ArrayList<Integer>();
		Set<IClient> clients = scope.getClients();
		for (IClient client : clients) {
			if (client != null
					&& client
							.getAttribute(VideoConstants.STREAM_CLIENT_ATTR_INDEX) != null) {
				inUseIndex.add(Integer.valueOf(client.getAttribute(
						VideoConstants.STREAM_CLIENT_ATTR_INDEX).toString()));
			}
		}
		indexList.removeAll(inUseIndex);
		for (int i = 0; i < indexList.size(); i++) {
			if (indexList.get(i) != null) {
				minIndex = indexList.get(i);
				break;
			}
		}

		return minIndex;
	}
}
