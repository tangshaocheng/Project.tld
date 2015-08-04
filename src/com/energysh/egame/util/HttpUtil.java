package com.energysh.egame.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtil {

	private final static Log log = LogFactory.getLog(HttpUtil.class);

	/**
	 * 打包请求数据
	 * 
	 * @param paras
	 * @param xmlString
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static Map<String, Object> packageRequest(final Map<String, String> paras, final String xmlString, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		Map<String, Object> rmap = Collections.synchronizedMap(new HashMap<String, Object>());
		try {
			String servletPath = "logs.htm";
			String synUrl = (String) request.getAttribute("synUrl");
			if (request.getAttribute("mt") != null)
				servletPath = (StringUtils.endsWith(synUrl, "/") ? "mt" : "/mt") + servletPath;
			else if (request.getAttribute("mo") != null)
				servletPath = (StringUtils.endsWith(synUrl, "/") ? "mo" : "/mo") + servletPath;
			else if (request.getAttribute("mr") != null)
				servletPath = (StringUtils.endsWith(synUrl, "/") ? "mr" : "/mr") + servletPath;
			String queryString = "";
			if ("GET".equalsIgnoreCase(request.getMethod())) {
				queryString = StringUtils.isBlank(request.getQueryString()) ? "" : ("?" + request.getQueryString());
			}
			final String target = request.getAttribute("synUrl") + servletPath + queryString;
			log.info("execute, target is " + target);
			log.info("response commit state: " + response.isCommitted());
			if (StringUtils.isBlank(target)) {
				log.error("The target address is not given. Please provide a target address.");
				return rmap;
			}
			log.info("checking url");
			final URL url;
			try {
				url = new URL(target);
			} catch (MalformedURLException e) {
				log.error("The provided target url is not valid.", e);
				return rmap;
			}
			log.info("seting up the host configuration");
			final HostConfiguration config = new HostConfiguration();
			ProxyHost proxyHost = getUseProxyServer((String) request.getAttribute("use-proxy"));
			if (proxyHost != null)
				config.setProxyHost(proxyHost);
			final int port = url.getPort() != -1 ? url.getPort() : url.getDefaultPort();
			config.setHost(url.getHost(), port, "http");
			log.info("config is " + config.toString());
			final HttpMethod method = setupProxyRequest(url, paras, xmlString, request);
			method.setRequestHeader("synSpIp", request.getRemoteAddr());
			if (method == null) {
				log.error("Unsupported request method found: " + request.getMethod());
				return rmap;
			}
			rmap.put("config", config);
			rmap.put("method", method);
		} catch (Exception e) {
			log.error("send httpRequest failed.", e);
			return rmap;
		}
		return rmap;
	}

	/**
	 * 发送代理请求(注：仅仅多发送一个请求，不返回相应数据)
	 * 
	 * @param config
	 * @param method
	 */
	public static void sendRequest(final HostConfiguration config, final HttpMethod method) {
		try {
			// perform the reqeust to the target server
			final HttpClient client = new HttpClient(new SimpleHttpConnectionManager());
			if (log.isInfoEnabled()) {
				log.info("client state" + client.getState());
				log.info("client params" + client.getParams().toString());
				log.info("executeMethod / fetching data ...");
			}
			final int result = client.executeMethod(config, method);
			log.info("set up response, result code was " + result + " result body was " + method.getResponseBodyAsString());
		} catch (Exception e) {
			log.error("send httpRequest failed.", e);
		}
	}

	/**
	 * 设置代理服务器
	 * 
	 * @param useProxyServer
	 * @return
	 */
	public static ProxyHost getUseProxyServer(String useProxyServer) {
		ProxyHost proxyHost = null;
		if (useProxyServer != null) {
			String proxyHostStr = useProxyServer;
			int colonIdx = proxyHostStr.indexOf(':');
			if (colonIdx != -1) {
				proxyHostStr = proxyHostStr.substring(0, colonIdx);
				String proxyPortStr = useProxyServer.substring(colonIdx + 1);
				if (proxyPortStr != null && proxyPortStr.length() > 0 && proxyPortStr.matches("[0-9]+")) {
					int proxyPort = Integer.parseInt(proxyPortStr);
					proxyHost = new ProxyHost(proxyHostStr, proxyPort);
				} else {
					proxyHost = new ProxyHost(proxyHostStr);
				}
			} else {
				proxyHost = new ProxyHost(proxyHostStr);
			}
		}
		return proxyHost;
	}

	/**
	 * 设置代理请求
	 * 
	 * @param url
	 * @param paras
	 * @param xmlString
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static HttpMethod setupProxyRequest(final URL url, final Map<String, String> paras, final String xmlString, final HttpServletRequest request) throws IOException {
		final String methodName = request.getMethod();
		final HttpMethod method;
		if ("POST".equalsIgnoreCase(methodName)) {
			PostMethod postMethod = new PostMethod();
			if (xmlString == null) {
				for (String key : paras.keySet()) {
					postMethod.setParameter(key, paras.get(key));
				}
			} else {
				StringRequestEntity entity = new StringRequestEntity(xmlString, "application/xml,charset=utf-8", "UTF-8");
				postMethod.setRequestEntity(entity);
			}
			method = postMethod;
		} else if ("GET".equalsIgnoreCase(methodName)) {
			method = new GetMethod();
		} else {
			log.warn("Unsupported HTTP method requested: " + request.getMethod());
			return null;
		}
		method.setFollowRedirects(false);
		method.setPath(url.getPath());
		method.setQueryString(url.getQuery());
		Enumeration e = request.getHeaderNames();
		if (e != null) {
			while (e.hasMoreElements()) {
				String headerName = (String) e.nextElement();
				if ("host".equalsIgnoreCase(headerName)) {
					// the host value is set by the http client
					continue;
				} else if ("content-length".equalsIgnoreCase(headerName)) {
					// the content-length is managed by the http client
					continue;
				} else if ("accept-encoding".equalsIgnoreCase(headerName)) {
					// the accepted encoding should only be those accepted by
					// the http client.
					// The response stream should (afaik) be deflated. If our
					// http client does not support
					// gzip then the response can not be unzipped and is
					// delivered wrong.
					continue;
				} else if (headerName.toLowerCase().startsWith("cookie")) {
					// fixme : don't set any cookies in the proxied request,
					// this needs a cleaner solution
					continue;
				}
				Enumeration values = request.getHeaders(headerName);
				while (values.hasMoreElements()) {
					String headerValue = (String) values.nextElement();
					log.info("setting proxy request parameter:" + headerName + ", value: " + headerValue);
					method.addRequestHeader(headerName, headerValue);
				}
			}
		}
		log.info("proxy query string " + method.getQueryString());
		return method;
	}

}
