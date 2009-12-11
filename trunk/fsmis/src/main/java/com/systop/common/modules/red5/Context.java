package com.systop.common.modules.red5;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

public class Context extends org.red5.server.Context {

	@Override
	@Autowired
	public void setApplicationContext(ApplicationContext context) {
		Field field = ReflectionUtils.findField(Context.class, "applicationContext");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, this, context);
	}

}
