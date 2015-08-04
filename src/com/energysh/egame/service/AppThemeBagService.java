package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.po.appstore.TAppThemeBag;
import com.energysh.egame.util.PageBar;

public interface AppThemeBagService {

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, String> del(Map<String, String> para) throws Exception;

	public Map<String, String> up(Map<String, String> para) throws Exception;

	public PageBar query(Map<String, String> para) throws Exception;

	public TAppThemeBag get(Map<String, String> para) throws Exception;

	public TAppThemeBag getCategoryGood(Map<String, String> para) throws Exception;

	public Map<String, String> sort(Map<String, String> para) throws Exception;

	public Map<String, String> upCategoryGood(Map<String, String> para) throws Exception;

}
