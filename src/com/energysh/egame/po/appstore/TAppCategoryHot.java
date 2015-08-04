package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * TAppCategoryHot entity. @author MyEclipse Persistence Tools
 */

public class TAppCategoryHot implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer appId;
	private Integer categoryId;
	private Integer count;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TAppCategoryHot() {
	}

	/** minimal constructor */
	public TAppCategoryHot(Integer appId, Integer categoryId) {
		this.appId = appId;
		this.categoryId = categoryId;
	}

	/** full constructor */
	public TAppCategoryHot(Integer appId, Integer categoryId, Integer count, Date ctime) {
		this.appId = appId;
		this.categoryId = categoryId;
		this.count = count;
		this.ctime = ctime;
	}

	// Property accessors

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}