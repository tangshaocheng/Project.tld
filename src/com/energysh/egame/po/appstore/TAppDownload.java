package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TAppDownload implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer appId;
	private Integer userId;
	private String mac;
	private String batchId;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TAppDownload() {
	}

	/** full constructor */
	public TAppDownload(Integer appId, Integer userId, String mac, String batchId, Date ctime) {
		this.appId = appId;
		this.userId = userId;
		this.mac = mac;
		this.batchId = batchId;
		this.ctime = ctime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
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

}