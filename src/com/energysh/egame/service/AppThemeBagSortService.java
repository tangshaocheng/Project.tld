package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.po.appstore.TAppThemeBagSort;

public interface AppThemeBagSortService {

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, String> del(Map<String, String> para) throws Exception;

	public Map<String, String> up(Map<String, String> para) throws Exception;

	public Map<String, Object> query(Map<String, String> para) throws Exception;

	public TAppThemeBagSort get(Map<String, String> para) throws Exception;

	public Map<String, String> sort(Map<String, String> para) throws Exception;

}
