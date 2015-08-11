package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TDeviceBatch implements Serializable {

	private static final long serialVersionUID = 1L;

	private String batchId;
	private String name;
	private String remark;
	private Date ctime;
	private String sdkSwitch;
	private Integer sdkId;
	public Integer getSdkId() {
		return sdkId;
	}

	public void setSdkId(Integer sdkId) {
		this.sdkId = sdkId;
	}

	public String getSdkSwitch() {
		return sdkSwitch;
	}

	public void setSdkSwitch(String sdkSwitch) {
		this.sdkSwitch = sdkSwitch;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
