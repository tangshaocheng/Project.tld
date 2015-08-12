package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TAppSdk implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sdkName;
	private String country;
	private String activeTime;
	private String onOrOff;
	private Date createTime;
	private List<Map<String, Object>> list;
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
		return onOrOff;
	}
	public void setOnOrOff(String onOrOff) {
		this.onOrOff = onOrOff;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TAppSdk [id=" + id + ", sdkName=" + sdkName + ", country="
				+ country + ", activeTime=" + activeTime + ", onOrOff="
				+ onOrOff + ", createTime=" + createTime + ", list=" + list
				+ "]";
	}
	
	
}
