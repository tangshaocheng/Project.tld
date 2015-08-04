package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TApp implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 应用ID **/
	private Integer id;
	/** 版本 **/
	private String version;
	/** 分类ID **/
	private Integer categoryId;
	/** 专题ID **/
	private Integer subjectId;
	/** 应用名称 **/
	private String name;
	/** 包名 **/
	private String pakeage;
	/** 主类名 **/
	private String mainClass;
	/** 作者 **/
	private String develope;
	/** 热度 **/
	private Integer heat;
	/** 添加的时间 **/
	private Date ctime;
	/** 版本更新介绍 **/
	private String upDesc;
	/** 应用描述 **/
	private String appDesc;
	/** 修改应用 **/
	private Double appSize;
	/** 下载数量 **/
	private Integer downCnt;
	/** 图标 **/
	private String icon;
	/** apk程序 **/
	private String app;
	/** 新游戏push通知消息 **/
	private String notify;
	/** 是否是新游戏 **/
	private Integer notifyStatus;
	/** 主图片1 **/
	private String mainPic;
	/** 主图片2 **/
	private String mainPic2;
	/** 截图1 **/
	private String pic1;
	/** 截图2 **/
	private String pic2;
	/** 截图3 **/
	private String pic3;
	/** 截图4 **/
	private String pic4;
	/** 截图5 **/
	private String pic5;
	/** 更新时间 */
	private Date uptime;
	/** 专题名 **/
	private String categoryName;
	/** 分类名 **/
	private volatile String subjectName;
	/** 内置应用包名 **/
	private String embededPakeage;
	/** 内置应主类名 **/
	private String embededMainClass;
	/** 内置应用版本 **/
	private String embededVersion;
	/** 内置应用APK下载目录 **/
	private String embededApp;
	/** 内置应用大小 **/
	private Double embededAppSize;
	/** 应用分享内容 **/
	private String shareContent;
	/** 应用网站来源 **/
	private Integer appSource;
	/** app状态 **/
	private Integer appStatus;
	/** 硬件支持(ipad/iphone) **/
	private String support;
	/** 最低支持系统版本 **/
	private String osVersionMin;
	/** 热门标签 **/
	private String appTag;
	/** app简单描述 **/
	private String singleWord;
	/** 版本 **/
	private Integer versionCode;
	/** 来源URL **/
	private String sourceUrl;
	/** 年龄限制 **/
	private String ageLimit;
	/** 应用和批次关联列表 **/
	private List<TAppBatch> appBatchList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPakeage() {
		return pakeage;
	}

	public void setPakeage(String pakeage) {
		this.pakeage = pakeage;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public String getDevelope() {
		return develope;
	}

	public void setDevelope(String develope) {
		this.develope = develope;
	}

	public Integer getHeat() {
		return heat;
	}

	public void setHeat(Integer heat) {
		this.heat = heat;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getUpDesc() {
		return upDesc;
	}

	public void setUpDesc(String upDesc) {
		this.upDesc = upDesc;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Double getAppSize() {
		return appSize;
	}

	public void setAppSize(Double appSize) {
		this.appSize = appSize;
	}

	public Integer getDownCnt() {
		return downCnt;
	}

	public void setDownCnt(Integer downCnt) {
		this.downCnt = downCnt;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public Integer getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public String getMainPic2() {
		return mainPic2;
	}

	public void setMainPic2(String mainPic2) {
		this.mainPic2 = mainPic2;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	public String getPic5() {
		return pic5;
	}

	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getEmbededPakeage() {
		return embededPakeage;
	}

	public void setEmbededPakeage(String embededPakeage) {
		this.embededPakeage = embededPakeage;
	}

	public String getEmbededMainClass() {
		return embededMainClass;
	}

	public void setEmbededMainClass(String embededMainClass) {
		this.embededMainClass = embededMainClass;
	}

	public String getEmbededVersion() {
		return embededVersion;
	}

	public void setEmbededVersion(String embededVersion) {
		this.embededVersion = embededVersion;
	}

	public String getEmbededApp() {
		return embededApp;
	}

	public void setEmbededApp(String embededApp) {
		this.embededApp = embededApp;
	}

	public Double getEmbededAppSize() {
		return embededAppSize;
	}

	public void setEmbededAppSize(Double embededAppSize) {
		this.embededAppSize = embededAppSize;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public Integer getAppSource() {
		return appSource;
	}

	public void setAppSource(Integer appSource) {
		this.appSource = appSource;
	}

	public Integer getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getOsVersionMin() {
		return osVersionMin;
	}

	public void setOsVersionMin(String osVersionMin) {
		this.osVersionMin = osVersionMin;
	}

	public String getAppTag() {
		return appTag;
	}

	public void setAppTag(String appTag) {
		this.appTag = appTag;
	}

	public String getSingleWord() {
		return singleWord;
	}

	public void setSingleWord(String singleWord) {
		this.singleWord = singleWord;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}

	public List<TAppBatch> getAppBatchList() {
		return appBatchList;
	}

	public void setAppBatchList(List<TAppBatch> appBatchList) {
		this.appBatchList = appBatchList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}