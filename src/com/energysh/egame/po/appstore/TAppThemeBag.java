package com.energysh.egame.po.appstore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TAppThemeBag implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Integer bagType;
	private Date ctime;

	/** 前台展示用 **/
	private List<Map<String, Object>> list;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBagType() {
		return bagType;
	}

	public void setBagType(Integer bagType) {
		this.bagType = bagType;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
