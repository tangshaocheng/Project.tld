package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TPartnersIncomeConf implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Double cpaPrice;
	private Double cpsDivideRatio;
	private Double cpaPriceNextDay;
	private Double cpsDivideRatioNextDay;
	private Date ctime;

	/** 扩展字段 **/
	private String nickname;

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

	public Double getCpaPrice() {
		return cpaPrice;
	}

	public void setCpaPrice(Double cpaPrice) {
		this.cpaPrice = cpaPrice;
	}

	public Double getCpsDivideRatio() {
		return cpsDivideRatio;
	}

	public void setCpsDivideRatio(Double cpsDivideRatio) {
		this.cpsDivideRatio = cpsDivideRatio;
	}

	public Double getCpaPriceNextDay() {
		return cpaPriceNextDay;
	}

	public void setCpaPriceNextDay(Double cpaPriceNextDay) {
		this.cpaPriceNextDay = cpaPriceNextDay;
	}

	public Double getCpsDivideRatioNextDay() {
		return cpsDivideRatioNextDay;
	}

	public void setCpsDivideRatioNextDay(Double cpsDivideRatioNextDay) {
		this.cpsDivideRatioNextDay = cpsDivideRatioNextDay;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
