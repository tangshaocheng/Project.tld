package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TDeviceMacInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer mac;
	private String androidId;
	private String deviceId;
	private String serial;
	private String wifiId;
	private String imsi;
	private String batchId;
	private Date ctime;
	private String ip;
	private Integer province;
	private Integer appId;
	private String ver;
	private String osType;

	// Constructors

	/** default constructor */
	public TDeviceMacInfo() {
	}

	/** minimal constructor */
	public TDeviceMacInfo(String batchId) {
		this.batchId = batchId;
	}

	/** full constructor */
	public TDeviceMacInfo(String androidId, String deviceId, String serial, String wifiId, String imsi, String batchId, Date ctime, String ip, Integer province, Integer appId, String ver, String osType) {
		this.androidId = androidId;
		this.deviceId = deviceId;
		this.serial = serial;
		this.wifiId = wifiId;
		this.imsi = imsi;
		this.batchId = batchId;
		this.ctime = ctime;
		this.ip = ip;
		this.province = province;
		this.appId = appId;
		this.ver = ver;
		this.osType = osType;
	}

	// Property accessors

	public Integer getMac() {
		return this.mac;
	}

	public void setMac(Integer mac) {
		this.mac = mac;
	}

	public String getAndroidId() {
		return this.androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getWifiId() {
		return this.wifiId;
	}

	public void setWifiId(String wifiId) {
		this.wifiId = wifiId;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getProvince() {
		return this.province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getVer() {
		return this.ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getOsType() {
		return this.osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

}