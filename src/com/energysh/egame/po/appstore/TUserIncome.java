package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TUserIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Date cdate;
	private Integer userId;
	private Integer userType;
	private String batchId;
	private Double income;
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

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
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
