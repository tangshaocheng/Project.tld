package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * MDevXgToken entity. @author MyEclipse Persistence Tools
 */

public class MDevXgToken implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String token;
	private String mac;
	private String batchId;
	private Date ctime;

	// Constructors

	/** default constructor */
	public MDevXgToken() {
	}

	/** full constructor */
	public MDevXgToken(String token, String mac, String batchId, Date ctime) {
		this.token = token;
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

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
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