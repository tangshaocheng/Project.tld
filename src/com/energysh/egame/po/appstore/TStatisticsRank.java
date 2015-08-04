package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TStatisticsRank implements Serializable {

	private static final long serialVersionUID = 1L;

	private int appId;
	private String appName;
	private Integer realAllCount;
	private Integer baseNum;
	private Integer weekRealCount;
	private Integer weekReviseCount;
	private Integer monthRealCount;
	private Integer monthReviseCount;
	private Date ctime;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getRealAllCount() {
		return realAllCount;
	}

	public void setRealAllCount(Integer realAllCount) {
		this.realAllCount = realAllCount;
	}

	public Integer getBaseNum() {
		return baseNum;
	}

	public void setBaseNum(Integer baseNum) {
		this.baseNum = baseNum;
	}

	public Integer getWeekRealCount() {
		return weekRealCount;
	}

	public void setWeekRealCount(Integer weekRealCount) {
		this.weekRealCount = weekRealCount;
	}

	public Integer getWeekReviseCount() {
		return weekReviseCount;
	}

	public void setWeekReviseCount(Integer weekReviseCount) {
		this.weekReviseCount = weekReviseCount;
	}

	public Integer getMonthRealCount() {
		return monthRealCount;
	}

	public void setMonthRealCount(Integer monthRealCount) {
		this.monthRealCount = monthRealCount;
	}

	public Integer getMonthReviseCount() {
		return monthReviseCount;
	}

	public void setMonthReviseCount(Integer monthReviseCount) {
		this.monthReviseCount = monthReviseCount;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}
