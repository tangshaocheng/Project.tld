package com.energysh.egame.util;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.energysh.egame.exception.AppBizException;
import com.energysh.egame.exception.AppExcCodes;

public class ParaUtils {

	public static int checkNumberAndGet(String str, String paraName) throws AppBizException {
		if (StringUtils.isBlank(str) || !StringUtils.isNumeric(str)) {
			throw new AppBizException(AppExcCodes.E_INVALID_PARA, "invalid parameter;  " + paraName + ":" + str);
		}
		return Integer.parseInt(str);
	}

	public static String checkStringAndGet(String str, String paraName) throws AppBizException {
		if (StringUtils.isBlank(str)) {
			throw new AppBizException(AppExcCodes.E_INVALID_PARA, "invalid parameter;  " + paraName + ":" + str);
		}
		return str;
	}

	public static String checkPicUri(Object uri) {
		if (uri == null)
			return "";
		if (StringUtils.isBlank(uri.toString()))
			return "";
		if (StringUtils.startsWith(uri.toString(), "http://")) {
			return uri.toString();
		} else {
			uri = MyConfigurer.getEvn("resource_domain")+uri;
			uri = URLEncoder.encode(uri.toString());
		}
		return MyConfigurer.getEvn("interface_domain") + "/downloadPic.htm?picUrl=" + uri.toString();
	}

	// public static String checkAppUri(Object uri) {
	// if (uri == null)
	// return "";
	// if (StringUtils.isBlank(uri.toString()))
	// return "";
	// if (StringUtils.startsWith(uri.toString(), "http://"))
	// return uri.toString();
	// return MyConfigurer.getEvn("interface_domain") + "/downloadApp.htm?appUrl=" + uri.toString();
	// }
	//	
	@SuppressWarnings("deprecation")
	public static String checkAppUri(Object uri, String batchId, String mac, String userId, String appId) {
		if (uri == null || "null".equalsIgnoreCase(uri.toString()))
			return "";
		if (StringUtils.isBlank(uri.toString()))
			return "";
		if (StringUtils.startsWith(uri.toString(), "http://")) {
			uri = URLEncoder.encode(uri.toString());
		} else {
			uri = MyConfigurer.getEvn("resource_domain")+uri;
			uri = URLEncoder.encode(uri.toString());
		}
		return MyConfigurer.getEvn("interface_domain") + "/downloadApp.htm?appUrl=" + uri.toString() + "&batchId=" + batchId + "&mac=" + mac + "&userId=" + userId + "&appId=" + appId;
	}

}
