package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TSaleBatchRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userId;
	private String batchId;
	private String divideRate;
	private Integer del;
	private Date ctime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getDivideRate() {
		return divideRate;
	}

	public void setDivideRate(String divideRate) {
		this.divideRate = divideRate;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
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
