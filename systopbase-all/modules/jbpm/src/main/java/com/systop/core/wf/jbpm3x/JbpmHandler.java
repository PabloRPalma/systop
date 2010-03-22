/*
 * Copyright 2002-2006 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.systop.core.wf.jbpm3x;

/**
 * Deprecated class used for compatibility purposes. It will be removed in a
 * future release.
 * 
 * @author Costin Leau
 * @deprecated
 */
public class JbpmHandler extends JbpmHandlerProxy {

	public String getBeanName() {
		return super.getTargetBean();
	}

	public void setBeanName(String beanName) {
		super.setTargetBean(beanName);
	}
}
