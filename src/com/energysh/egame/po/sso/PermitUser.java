package com.energysh.egame.po.sso;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PermitUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String lname;
	private String lps;
	private Date ctime;
	private List<Map<String, Object>> groupList;// 非数据库字段,保存用户组列表
	private List<Map<String, Object>> urlList;// 非数据库字段，保存用户拥有的URL
	private List<Map<String, Object>> projectList;// 非数据库字段，保存用户拥有项目的列表
	private List<String> chekcedUrlList;// 需要校验的URL
	private Integer userType;
	private Integer cpId;

	public Integer getCpId() {
		return cpId;
	}

	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLps() {
		return this.lps;
	}

	public void setLps(String lps) {
		this.lps = lps;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public List<Map<String, Object>> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Map<String, Object>> groupList) {
		this.groupList = groupList;
	}

	public List<Map<String, Object>> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<Map<String, Object>> urlList) {
		this.urlList = urlList;
	}

	public List<Map<String, Object>> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Map<String, Object>> projectList) {
		this.projectList = projectList;
	}

	public List<String> getChekcedUrlList() {
		return chekcedUrlList;
	}

	public void setChekcedUrlList(List<String> chekcedUrlList) {
		this.chekcedUrlList = chekcedUrlList;
	}

}
