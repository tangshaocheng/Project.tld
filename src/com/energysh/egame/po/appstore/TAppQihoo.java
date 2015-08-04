package com.energysh.egame.po.appstore;

import java.sql.Timestamp;

public class TAppQihoo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer qhAppId;
	private String qhPackageName;
	private String qhMinVersion;
	private String qhMinVersionCode;
	private String qhName;
	private String qhCategoryName;
	private String qhDescription;
	private String qhDeveloper;
	private String qhIconUrl;
	private String qhLargeIcon;
	private String qhScreenshotsUrl;
	private String qhIncomeShare;
	private String qhRating;
	private String qhVersionName;
	private String qhVersionCode;
	private String qhPriceInfo;
	private String qhTag;
	private String qhDownloadTimes;
	private String qhDownloadUrl;
	private String qhApkMd5;
	private Integer qhApkSize;
	private Timestamp qhCreateTime;
	private Timestamp qhUpdateTime;
	private String qhSignatureMd5;
	private String qhUpdateInfo;
	private String qhLanguage;
	private String qhBrief;
	private String qhAppPermission;
	private String qhIsAd;
	private Integer type;
	private Timestamp ctime;

	// Constructors

	/** default constructor */
	public TAppQihoo() {
	}

	/** minimal constructor */
	public TAppQihoo(Integer qhAppId) {
		this.qhAppId = qhAppId;
	}

	/** full constructor */
	public TAppQihoo(Integer qhAppId, String qhPackageName, String qhMinVersion, String qhMinVersionCode, String qhName, String qhCategoryName, String qhDescription, String qhDeveloper, String qhIconUrl, String qhLargeIcon, String qhScreenshotsUrl, String qhIncomeShare, String qhRating, String qhVersionName, String qhVersionCode, String qhPriceInfo, String qhTag, String qhDownloadTimes, String qhDownloadUrl, String qhApkMd5, Integer qhApkSize, Timestamp qhCreateTime, Timestamp qhUpdateTime, String qhSignatureMd5, String qhUpdateInfo, String qhLanguage, String qhBrief, String qhAppPermission, String qhIsAd, Integer type, Timestamp ctime) {
		this.qhAppId = qhAppId;
		this.qhPackageName = qhPackageName;
		this.qhMinVersion = qhMinVersion;
		this.qhMinVersionCode = qhMinVersionCode;
		this.qhName = qhName;
		this.qhCategoryName = qhCategoryName;
		this.qhDescription = qhDescription;
		this.qhDeveloper = qhDeveloper;
		this.qhIconUrl = qhIconUrl;
		this.qhLargeIcon = qhLargeIcon;
		this.qhScreenshotsUrl = qhScreenshotsUrl;
		this.qhIncomeShare = qhIncomeShare;
		this.qhRating = qhRating;
		this.qhVersionName = qhVersionName;
		this.qhVersionCode = qhVersionCode;
		this.qhPriceInfo = qhPriceInfo;
		this.qhTag = qhTag;
		this.qhDownloadTimes = qhDownloadTimes;
		this.qhDownloadUrl = qhDownloadUrl;
		this.qhApkMd5 = qhApkMd5;
		this.qhApkSize = qhApkSize;
		this.qhCreateTime = qhCreateTime;
		this.qhUpdateTime = qhUpdateTime;
		this.qhSignatureMd5 = qhSignatureMd5;
		this.qhUpdateInfo = qhUpdateInfo;
		this.qhLanguage = qhLanguage;
		this.qhBrief = qhBrief;
		this.qhAppPermission = qhAppPermission;
		this.qhIsAd = qhIsAd;
		this.type = type;
		this.ctime = ctime;
	}

	// Property accessors

	public Integer getQhAppId() {
		return this.qhAppId;
	}

	public void setQhAppId(Integer qhAppId) {
		this.qhAppId = qhAppId;
	}

	public String getQhPackageName() {
		return this.qhPackageName;
	}

	public void setQhPackageName(String qhPackageName) {
		this.qhPackageName = qhPackageName;
	}

	public String getQhMinVersion() {
		return this.qhMinVersion;
	}

	public void setQhMinVersion(String qhMinVersion) {
		this.qhMinVersion = qhMinVersion;
	}

	public String getQhMinVersionCode() {
		return this.qhMinVersionCode;
	}

	public void setQhMinVersionCode(String qhMinVersionCode) {
		this.qhMinVersionCode = qhMinVersionCode;
	}

	public String getQhName() {
		return this.qhName;
	}

	public void setQhName(String qhName) {
		this.qhName = qhName;
	}

	public String getQhCategoryName() {
		return this.qhCategoryName;
	}

	public void setQhCategoryName(String qhCategoryName) {
		this.qhCategoryName = qhCategoryName;
	}

	public String getQhDescription() {
		return this.qhDescription;
	}

	public void setQhDescription(String qhDescription) {
		this.qhDescription = qhDescription;
	}

	public String getQhDeveloper() {
		return this.qhDeveloper;
	}

	public void setQhDeveloper(String qhDeveloper) {
		this.qhDeveloper = qhDeveloper;
	}

	public String getQhIconUrl() {
		return this.qhIconUrl;
	}

	public void setQhIconUrl(String qhIconUrl) {
		this.qhIconUrl = qhIconUrl;
	}

	public String getQhLargeIcon() {
		return this.qhLargeIcon;
	}

	public void setQhLargeIcon(String qhLargeIcon) {
		this.qhLargeIcon = qhLargeIcon;
	}

	public String getQhScreenshotsUrl() {
		return this.qhScreenshotsUrl;
	}

	public void setQhScreenshotsUrl(String qhScreenshotsUrl) {
		this.qhScreenshotsUrl = qhScreenshotsUrl;
	}

	public String getQhIncomeShare() {
		return this.qhIncomeShare;
	}

	public void setQhIncomeShare(String qhIncomeShare) {
		this.qhIncomeShare = qhIncomeShare;
	}

	public String getQhRating() {
		return this.qhRating;
	}

	public void setQhRating(String qhRating) {
		this.qhRating = qhRating;
	}

	public String getQhVersionName() {
		return this.qhVersionName;
	}

	public void setQhVersionName(String qhVersionName) {
		this.qhVersionName = qhVersionName;
	}

	public String getQhVersionCode() {
		return this.qhVersionCode;
	}

	public void setQhVersionCode(String qhVersionCode) {
		this.qhVersionCode = qhVersionCode;
	}

	public String getQhPriceInfo() {
		return this.qhPriceInfo;
	}

	public void setQhPriceInfo(String qhPriceInfo) {
		this.qhPriceInfo = qhPriceInfo;
	}

	public String getQhTag() {
		return this.qhTag;
	}

	public void setQhTag(String qhTag) {
		this.qhTag = qhTag;
	}

	public String getQhDownloadTimes() {
		return this.qhDownloadTimes;
	}

	public void setQhDownloadTimes(String qhDownloadTimes) {
		this.qhDownloadTimes = qhDownloadTimes;
	}

	public String getQhDownloadUrl() {
		return this.qhDownloadUrl;
	}

	public void setQhDownloadUrl(String qhDownloadUrl) {
		this.qhDownloadUrl = qhDownloadUrl;
	}

	public String getQhApkMd5() {
		return this.qhApkMd5;
	}

	public void setQhApkMd5(String qhApkMd5) {
		this.qhApkMd5 = qhApkMd5;
	}

	public Integer getQhApkSize() {
		return this.qhApkSize;
	}

	public void setQhApkSize(Integer qhApkSize) {
		this.qhApkSize = qhApkSize;
	}

	public Timestamp getQhCreateTime() {
		return this.qhCreateTime;
	}

	public void setQhCreateTime(Timestamp qhCreateTime) {
		this.qhCreateTime = qhCreateTime;
	}

	public Timestamp getQhUpdateTime() {
		return this.qhUpdateTime;
	}

	public void setQhUpdateTime(Timestamp qhUpdateTime) {
		this.qhUpdateTime = qhUpdateTime;
	}

	public String getQhSignatureMd5() {
		return this.qhSignatureMd5;
	}

	public void setQhSignatureMd5(String qhSignatureMd5) {
		this.qhSignatureMd5 = qhSignatureMd5;
	}

	public String getQhUpdateInfo() {
		return this.qhUpdateInfo;
	}

	public void setQhUpdateInfo(String qhUpdateInfo) {
		this.qhUpdateInfo = qhUpdateInfo;
	}

	public String getQhLanguage() {
		return this.qhLanguage;
	}

	public void setQhLanguage(String qhLanguage) {
		this.qhLanguage = qhLanguage;
	}

	public String getQhBrief() {
		return this.qhBrief;
	}

	public void setQhBrief(String qhBrief) {
		this.qhBrief = qhBrief;
	}

	public String getQhAppPermission() {
		return this.qhAppPermission;
	}

	public void setQhAppPermission(String qhAppPermission) {
		this.qhAppPermission = qhAppPermission;
	}

	public String getQhIsAd() {
		return this.qhIsAd;
	}

	public void setQhIsAd(String qhIsAd) {
		this.qhIsAd = qhIsAd;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getCtime() {
		return this.ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

}