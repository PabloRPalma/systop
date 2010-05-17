package com.systop.fsmis.video;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

/**
 * 覆盖{@link org.red5.server.Context#setApplicationContext(ApplicationContext)}
 * 方法， 直接注入Spring的ApplicationContext，摒弃Red5采用的特殊的部属方式。使得Red5的部属像普通
 * 基于Spring的Web应用程序一样简单。
 * 
 * @author catstiger@gmail.com
 * 
 */
public class Context extends org.red5.server.Context {
	@Override
	@Autowired
	// org.red5.server.Context的本方法中config = "red5.xml";,局限
	//本类从org.red5.server.Context继承,即本类也成为了一个org.red5.server.Context
	//通过在applicationContext-red5.xml中注册本类,来替代org.red5.server.Context
	public void setApplicationContext(ApplicationContext context) {
		//通过org.springframework.util.ReflectionUtils的查找Field方法,得到本类的名为"applicationContext"的field
		Field field = ReflectionUtils.findField(Context.class,
				"applicationContext");
		//设置这个Field为可以访问
		ReflectionUtils.makeAccessible(field);
		//为red5的上下文注入spring的上下文,即:将整个应用程序的上下文环境(所有配置文件)让red5的上下文了解
		ReflectionUtils.setField(field, this, context);
	}
}
