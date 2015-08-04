package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TAppBatch implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Integer appId;
	private String batchId;
	private String app;
	private Double appSize;
	private Date ctime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public Double getAppSize() {
		return appSize;
	}

	public void setAppSize(Double appSize) {
		this.appSize = appSize;
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
