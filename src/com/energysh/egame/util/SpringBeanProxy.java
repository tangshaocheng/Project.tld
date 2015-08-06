package com.energysh.egame.util;

import org.springframework.context.ApplicationContext;

public class SpringBeanProxy {

	private static ApplicationContext applicationContext;

	public synchronized static void setApplicationContext(ApplicationContext applicationContext) {
		SpringBeanProxy.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
