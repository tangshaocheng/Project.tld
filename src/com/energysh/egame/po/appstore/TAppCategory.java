package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;

public class TAppCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	/** 父分类id **/
	private Integer fatherId;
	/** 分类名称 **/
	private String name;
	/** 序号 **/
	private Integer seq;
	/** 图标 **/
	private String icon;
	/** 分类描述 **/
	private String remark;
	/** 分类级别：1.一级,2.二级,3.三级标签 **/
	private Integer level;
	/** 运营配置类别:0默认分类,1.小便精选,2.轻应用 **/
	private Integer isOpex;
	/** 发布状态:1.发布，0.不发布 **/
	private Integer pubStatus;
	/** 分类添加时间 **/
	private Date ctime;
	/** 更新时间 **/
	private Date uptime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsOpex() {
		return isOpex;
	}

	public void setIsOpex(Integer isOpex) {
		this.isOpex = isOpex;
	}

	public Integer getPubStatus() {
		return pubStatus;
	}

	public void setPubStatus(Integer pubStatus) {
		this.pubStatus = pubStatus;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

}