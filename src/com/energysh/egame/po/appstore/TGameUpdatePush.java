package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * TGameUpdatePush entity. @author MyEclipse Persistence Tools
 */

public class TGameUpdatePush implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer appId;
	private String mac;
	private Date date;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TGameUpdatePush() {
	}

	/** minimal constructor */
	public TGameUpdatePush(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public TGameUpdatePush(Integer id, Integer appId, String mac, Date date, Date ctime) {
		this.id = id;
		this.appId = appId;
		this.mac = mac;
		this.date = date;
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

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}