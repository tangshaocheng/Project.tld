package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TCpsIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Date cdate;
	private Integer appId;
	private Integer userId;
	private Integer userType;
	private String batchId;
	private Double downNum;
	private Double cpsIncome;
	private Byte pubStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Double getDownNum() {
		return downNum;
	}

	public void setDownNum(Double downNum) {
		this.downNum = downNum;
	}

	public Double getCpsIncome() {
		return cpsIncome;
	}

	public void setCpsIncome(Double cpsIncome) {
		this.cpsIncome = cpsIncome;
	}

	public Byte getPubStatus() {
		return pubStatus;
	}

	public void setPubStatus(Byte pubStatus) {
		this.pubStatus = pubStatus;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
