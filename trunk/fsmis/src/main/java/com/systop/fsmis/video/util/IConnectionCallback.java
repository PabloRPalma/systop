package com.systop.fsmis.video.util;

import org.red5.server.api.IConnection;

/**
 * 专门用于处理IConnection对象的接口。
 * 
 * @author Sam
 * 
 */
public interface IConnectionCallback {
	/**
	 * 对单个IConnection进行处理。
	 * 
	 * @param conn 给定的IConnection对象
	 */
	public void doWithConnection(IConnection conn);
}