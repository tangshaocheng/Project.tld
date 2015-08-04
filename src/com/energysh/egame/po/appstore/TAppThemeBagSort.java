package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TAppThemeBagSort implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer themeBagId;
	private Integer sort;
	private Date ctime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getThemeBagId() {
		return themeBagId;
	}

	public void setThemeBagId(Integer themeBagId) {
		this.themeBagId = themeBagId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
