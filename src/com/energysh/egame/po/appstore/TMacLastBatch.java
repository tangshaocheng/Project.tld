package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TMacLastBatch implements Serializable {

	private static final long serialVersionUID = 1L;
	private TMacLastBatchId id;
	private String firstBatchId;
	private String lastBatchId;
	private Date ctime;

	// Constructors

	/** default constructor */
	public TMacLastBatch() {
	}

	/** minimal constructor */
	public TMacLastBatch(TMacLastBatchId id) {
		this.id = id;
	}

	/** full constructor */
	public TMacLastBatch(TMacLastBatchId id, String firstBatchId, String lastBatchId, Date ctime) {
		this.id = id;
		this.firstBatchId = firstBatchId;
		this.lastBatchId = lastBatchId;
		this.ctime = ctime;
	}

	// Property accessors

	public TMacLastBatchId getId() {
		return this.id;
	}

	public void setId(TMacLastBatchId id) {
		this.id = id;
	}

	public String getFirstBatchId() {
		return this.firstBatchId;
	}

	public void setFirstBatchId(String firstBatchId) {
		this.firstBatchId = firstBatchId;
	}

	public String getLastBatchId() {
		return this.lastBatchId;
	}

	public void setLastBatchId(String lastBatchId) {
		this.lastBatchId = lastBatchId;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}