package com.energysh.egame.po.appstore;

import java.util.Date;

/**
 * TAppCategoryGood entity. @author MyEclipse Persistence Tools
 */

public class TAppCategoryGood implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer categoryId;
	private Integer appId;
	private Integer sort;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TAppCategoryGood() {
	}

	/** minimal constructor */
	public TAppCategoryGood(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/** full constructor */
	public TAppCategoryGood(Integer categoryId, Integer appId, Integer sort, Date ctime) {
		this.categoryId = categoryId;
		this.appId = appId;
		this.sort = sort;
		this.ctime = ctime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}