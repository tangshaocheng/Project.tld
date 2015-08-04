package com.energysh.egame.util;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class CustomedContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		WebApplicationContext context = (WebApplicationContext) event.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		SpringBeanProxy.setApplicationContext(context);
	}

	public static Object getBaseDao(String beanName) {
		return SpringBeanProxy.getBean(beanName);
	}
}
