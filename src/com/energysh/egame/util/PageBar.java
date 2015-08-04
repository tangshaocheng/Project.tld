package com.energysh.egame.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

public class PageBar implements Serializable {

	private static final long serialVersionUID = 1L;
	private int totalPageNum = 0;// 共有页面数目
	private int currentPageNum = 1;// 当前现实的页面数
	private List<Object> resultList = new ArrayList<Object>();// 结果
	private int everyPageNum = 10;// 每页显示数据的条
	private int totalNum = 0;// 共有数据数目
	private Map<String, String> paraMap = null;
	private Object otherObject;
	private String url = "";

	public PageBar(Map<String, String> para) {
		this.setCurrentPageNum(para.get("currentPageNum"));
		this.setEveryPageNum(para.get("everyPageNum"));
	}

	public void initPara(Map<String, String> para) {
		this.setCurrentPageNum(para.get("currentPageNum"));
		this.setEveryPageNum(para.get("everyPageNum"));
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(String currentPageNum) {
		if (MyUtil.getInstance().isBlank(currentPageNum))
			currentPageNum = "1";
		this.currentPageNum = NumberUtils.toInt(currentPageNum);
	}

	public int getEveryPageNum() {
		return everyPageNum;
	}

	public void setEveryPageNum(String everyPageNum) {
		if (MyUtil.getInstance().isBlank(everyPageNum))
			everyPageNum = "10";
		this.everyPageNum = NumberUtils.toInt(everyPageNum);
		;
	}

	public List<Object> getResultList() {
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
		this.totalPageNum = totalNum / everyPageNum < (double) totalNum / everyPageNum ? ((totalNum / everyPageNum) + 1) : totalNum / everyPageNum;
	}

	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getOtherObject() {
		return otherObject;
	}

	public void setOtherObject(Object otherObject) {
		this.otherObject = otherObject;
	}
}