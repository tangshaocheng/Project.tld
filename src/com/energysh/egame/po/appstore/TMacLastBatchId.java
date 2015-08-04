package com.energysh.egame.po.appstore;

import java.io.Serializable;

public class TMacLastBatchId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mac;
	private Integer appId;

	// Constructors

	/** default constructor */
	public TMacLastBatchId() {
	}

	/** full constructor */
	public TMacLastBatchId(String mac, Integer appId) {
		this.mac = mac;
		this.appId = appId;
	}

	// Property accessors

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMacLastBatchId))
			return false;
		TMacLastBatchId castOther = (TMacLastBatchId) other;

		return ((this.getMac() == castOther.getMac()) || (this.getMac() != null && castOther.getMac() != null && this.getMac().equals(castOther.getMac()))) && ((this.getAppId() == castOther.getAppId()) || (this.getAppId() != null && castOther.getAppId() != null && this.getAppId().equals(castOther.getAppId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMac() == null ? 0 : this.getMac().hashCode());
		result = 37 * result + (getAppId() == null ? 0 : this.getAppId().hashCode());
		return result;
	}

}