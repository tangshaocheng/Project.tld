package com.energysh.egame.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * Http与Servlet工具类.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class Servlets {

	// -- Content Type 定义 --//
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String TEXT_TYPE = "text/plain";

	// -- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	// -- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	private Servlets() {
	}

	/**
	 * 设置客户端缓存过期时间 的Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header, set a fix expires date.
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header, set a time after now.
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified
	 *            内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag
	 *            内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			fileName = StringUtils.substringAfterLast(fileName, "/");
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 * 
	 * 比如有m2
	 */
	public static Map<String, String> getPara(ServletRequest request) {
		Map<String, String> paraMap = new HashMap<String, String>();
		java.util.Enumeration<String> pns = request.getParameterNames();
		String key = "";
		char a = 6;
		while (pns.hasMoreElements()) {
			key = pns.nextElement();
			String[] vs = request.getParameterValues(key);
			String value = "";
			if (vs.length == 1)
				value = vs[0].trim();
			else {
				for (String v : vs)
					value += v.trim() + a;
				value = value.substring(0, value.length() - 1);
			}
			// value = Utils.htmEncode(value);// //特殊字符转换
			if (!paraMap.containsKey(key)) {
				paraMap.put(key, value);
			} else {
				paraMap.put(key, paraMap.get(key).toString() + a + value);
			}
		}
		return paraMap;
	}

	public static Map<String, String> getJsonPara(ServletRequest request) throws IOException {
		Map<String, String> paramMap = new HashMap<String, String>();
		byte[] data = FileCopyUtils.copyToByteArray(request.getInputStream());
		String jsonData = new String(data, "UTF-8");
		if (StringUtils.isBlank(jsonData))
			return paramMap;
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		Iterator<String> keys = jsonObject.keys();
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			paramMap.put(key, jsonObject.get(key).toString().trim());
		}
		return paramMap;
	}

	public static Map<String, String> getHttpGetMethodPara(ServletRequest res) {
		HttpServletRequest request = (HttpServletRequest) res;
		Map<String, String> paraMap = new HashMap<String, String>();
		Enumeration<String> pns = request.getParameterNames();
		while (pns.hasMoreElements()) {
			String key = pns.nextElement();
			String value = request.getParameter(key);
			byte b[];
			try {
				b = value.getBytes("ISO-8859-1");
				for (int i = 0; i < b.length; i++) {
					byte b1 = b[i];
					if (b1 == 63)
						break;
					else if (b1 > 0)
						continue;
					else if (b1 < 0) {
						value = new String(b, "UTF-8");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			paraMap.put(key, value);
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("ip");
		}

		paraMap.put("ip", ip);
		return paraMap;

	}

	public static Map<String, String> getHttpGetMethodParaGBK(ServletRequest res) {
		HttpServletRequest request = (HttpServletRequest) res;
		Map<String, String> paraMap = new HashMap<String, String>();
		Enumeration<String> pns = request.getParameterNames();
		while (pns.hasMoreElements()) {
			String key = pns.nextElement();
			String value = request.getParameter(key);
			try {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			paraMap.put(key, value);
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("ip");
		}

		paraMap.put("ip", ip);
		return paraMap;

	}

	public static String getHttpPostContent(ServletRequest request) throws Exception {
		StringBuffer info = new StringBuffer("");
		InputStream in = null;
		BufferedInputStream buf = null;
		try {
			in = request.getInputStream();
			buf = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(new String(buffer, 0, iRead, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buf != null) {
				buf.close();
				buf = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}
		}

		return info.toString();
	}

	public static void setJsonObject(HttpServletResponse response, Object obj) throws Exception {
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter responseStream = response.getWriter();
		responseStream.println(JSONObject.fromObject(obj));
		responseStream.close();
	}

	public static ModelAndView setJsonResult(HttpServletResponse response, Object obj) throws Exception {
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter responseStream = response.getWriter();
		JSONArray fromObject = JSONArray.fromObject(obj);
		responseStream.println(fromObject);
		responseStream.close();
		return null;
	}

	public static ModelAndView setJsonResult(HttpServletResponse response, Object obj, JsonConfig jsonConfig) throws Exception {
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter responseStream = response.getWriter();
		responseStream.println(JSONArray.fromObject(obj, jsonConfig));
		responseStream.close();
		return null;
	}

	public static String errorAjax(HttpServletResponse response, Exception e) {
		e.printStackTrace();
		try {
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter responseStream = response.getWriter();
			Map<String, String> rmap = new HashMap<String, String>();
			if (e instanceof java.sql.SQLException) {
				rmap.put("info", "sqlEx");
			} else if (e instanceof java.io.IOException) {
				rmap.put("info", "fileEx");
			} else {
				rmap.put("info", "otherEx");
			}
			responseStream.println(JSONArray.fromObject(rmap));
			responseStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String errorObjectAjax(HttpServletResponse response, Exception e) {
		e.printStackTrace();
		try {
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter responseStream = response.getWriter();
			Map<String, String> rmap = new HashMap<String, String>();
			if (e instanceof java.sql.SQLException) {
				rmap.put("info", "sqlEx");
			} else if (e instanceof java.io.IOException) {
				rmap.put("info", "fileEx");
			} else {
				rmap.put("info", "otherEx");
			}
			responseStream.println(JSONObject.fromObject(rmap));
			responseStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String error(Exception ex, Model model) {
		ex.printStackTrace();
		Map<String, String> rmap = new HashMap<String, String>();
		if (ex instanceof java.sql.SQLException) {
			rmap.put("info", "sqlEx");
		} else if (ex instanceof java.io.IOException) {
			rmap.put("info", "fileEx");
		} else if (ex instanceof org.apache.commons.fileupload.FileUploadException) {
			rmap.put("info", "fileUploadEx");
		} else {
			rmap.put("info", "otherEx");
		}
		model.addAllAttributes(rmap);
		return "error";
	}

	public static String getCookieValue(String name, HttpServletRequest request) {
		try {
			if (name == null)
				return "";

			String allCookieStr = request.getHeader("Cookie");
			if (allCookieStr != null) {
				name = name + "=";
				int begin = allCookieStr.indexOf(name);
				if (begin >= 0) {
					int end = allCookieStr.indexOf(";", begin);
					if (end >= 0) {
						return allCookieStr.substring(begin + name.length(), end);
					} else {
						return allCookieStr.substring(begin + name.length());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}