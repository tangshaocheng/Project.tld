package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * TSearchHot entity. @author MyEclipse Persistence Tools
 */

public class TSearchHot implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String keyName;
	private String appType;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TSearchHot() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}