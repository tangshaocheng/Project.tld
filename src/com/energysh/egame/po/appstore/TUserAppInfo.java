package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TUserAppInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mac;
	private String packageName;
	private String appName;
	private String appVersion;
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
