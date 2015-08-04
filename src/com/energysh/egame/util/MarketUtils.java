package com.energysh.egame.util;

import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.energysh.egame.exception.AppExcCodes;

public class MarketUtils {

	/*
	 * 所有接口条用处理失败时返回错误信息，result=0|1 result：0-处理失败；1-处理成功； innerText：错误详细信息
	 */
	public static String getErrorCodeJson(String excCode) {
		StringBuffer buf = new StringBuffer();
		buf.append("{\"result\":0,\"msg\":\"");
		buf.append(AppExcCodes.getInstance().getCodePrompt(excCode));
		buf.append("\"}");
		return buf.toString();
	}

	public static String getErrorJson(String excCode) {
		StringBuffer buf = new StringBuffer();
		buf.append("{\"result\":0,\"msg\":\"");
		buf.append(excCode);
		buf.append("\"}");
		return buf.toString();
	}

	/*
	 * 所有接口条用处理成功时返回错误信息，result=0|1 result：0-处理失败；1-处理成功； innerText：错误详细信息
	 */
	public static String getResJson(String desc) {
		StringBuffer buf = new StringBuffer();
		buf.append("{\"result\":1,\"data\":");
		buf.append(desc);
		buf.append("}");
		return buf.toString();
	}

	/**
	 * 获取所有请求信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getAllRequestParam(HttpServletRequest request) {
		StringBuffer buf = new StringBuffer();
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String paraValue = request.getParameter(paraName);
			buf.append(paraName);
			buf.append("=");
			buf.append(paraValue);
			buf.append("&");
		}
		String ret = buf.toString();
		return ret.equals("") ? "" : ret.substring(0, ret.length() - 1);
	}

	/**
	 * 获取IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static void main(String[] args) {
		String ret = getResJson("[{name:\"jason\"},{id:11}]");
		System.out.println(ret);
	}
}
