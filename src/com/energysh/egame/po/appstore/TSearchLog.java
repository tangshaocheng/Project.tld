package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * TSearchLog entity. @author MyEclipse Persistence Tools
 */

public class TSearchLog implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mac;
	private String keyName;
	private String batchId;
	private String language;
	private String ver;
	private String appType;

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	private Date ctime;

	// Constructors

	/** default constructor */
	public TSearchLog() {
	}

	/** full constructor */
	public TSearchLog(String mac, String keyName, String batchId, String language, String ver, Date ctime) {
		this.mac = mac;
		this.keyName = keyName;
		this.batchId = batchId;
		this.language = language;
		this.ver = ver;
		this.ctime = ctime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
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

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
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