package com.energysh.egame.web;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.energysh.egame.po.sso.PermitUser;
import com.energysh.egame.service.LoginService;
import com.energysh.sso.service.PermitService;
import com.energysh.sso.util.SSOUtil;

public class PermitFilter implements Filter {

	private Log log = LogFactory.getLog(PermitFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		this.uncheckUrls = filterConfig.getInitParameter("uncheckUrls");
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) {
		try {
			HttpServletRequest httpReq = (HttpServletRequest) req;
			String requestURL = httpReq.getRequestURL().toString();
			String[] uncheckUrlArray = StringUtils.isNotBlank(uncheckUrls) ? uncheckUrls
					.split(";") : new String[0];
			for (String uncheckUrl : uncheckUrlArray) {
				if (requestURL.indexOf(uncheckUrl) > -1) {
					chain.doFilter(req, resp);
					return;
				}
			}
			Object energyUser = httpReq.getSession().getAttribute(
					"energyUserBms");
			if (energyUser == null) {
				httpReq.getRequestDispatcher("/WEB-INF/jsp/dispatcher.jsp")
						.forward(req, resp);
				return;
			}
			PermitUser user = (PermitUser) energyUser;
			// 添加操作记录
			Map<String, String> para = SSOUtil.getInstance().getPara(httpReq);
			para.put("url", requestURL);
			para.put("energyUserId", user.getUserId().toString());
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(httpReq.getSession()
							.getServletContext());
			if (webApplicationContext.containsBean("loginService")) {
				LoginService loginService = (LoginService) webApplicationContext
						.getBean("loginService");
				loginService.addLogs(para);
			}
			List<String> chekcedUrlList = user.getChekcedUrlList();
			boolean chekced = false;
			for (String cu : chekcedUrlList) {
				if (cu.equals(""))
					continue;
				if (requestURL.indexOf(cu) > -1) {
					chekced = true;
					break;
				}
			}
			// 不需要校验的URL
			if (!chekced) {
				chain.doFilter(req, resp);
				return;
			}
			boolean permit = false;
			List<Map<String, Object>> userUrlList = user.getUrlList();
			for (Map<String, Object> userUrl : userUrlList) {
				if (null == userUrl.get("url"))
					continue;
				if (requestURL.indexOf(userUrl.get("url").toString()) < 0)
					continue;// 无权限
				permit = true;
				break;
			}
			if (permit) {// 有权限
				chain.doFilter(req, resp);
				return;
			} else {
				httpReq.getRequestDispatcher("/error.jsp").forward(req, resp);
				return;
			}
		} catch (Exception e) {
			log.error("login error.", e);
		}
	}

	public void destroy() {
	}

	private String uncheckUrls;
	private static PermitService ps;

	public static PermitService getPermitService() {
		if (null == ps) {
			ApplicationContext contex = new ClassPathXmlApplicationContext(
					"classpath:com/energysh/sso/conf/spring-sso-conf.xml");
			ps = (PermitService) contex.getBean("permitService");
		}
		return ps;
	}

}
