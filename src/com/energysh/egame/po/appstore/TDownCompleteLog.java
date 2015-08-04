package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * TDownCompleteLog entity. @author MyEclipse Persistence Tools
 */

public class TDownCompleteLog implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mac;
	private String appId;
	private String batchId;
	private String language;
	private String ver;
	private String appType;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TDownCompleteLog() {
	}

	/** full constructor */
	public TDownCompleteLog(String mac, String appId, String batchId, String language, String ver, Date ctime) {
		this.mac = mac;
		this.appId = appId;
		this.batchId = batchId;
		this.language = language;
		this.ver = ver;
		this.ctime = ctime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getVer() {
		return this.ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}