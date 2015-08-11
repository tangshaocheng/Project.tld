package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TAppSdk implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sdkName;
	private String country;
	private String activeTime;
	private String OnOrOff;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSdkName() {
		return sdkName;
	}
	public void setSdkName(String sdkName) {
		this.sdkName = sdkName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getOnOrOff() {
		return OnOrOff;
	}
	public void setOnOrOff(String onOrOff) {
		OnOrOff = onOrOff;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
