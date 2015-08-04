package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.po.appstore.TAppTheme;
import com.energysh.egame.util.PageBar;

public interface AppThemeService {

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, String> del(Map<String, String> para) throws Exception;

	public Map<String, String> uu(Map<String, String> para) throws Exception;

	public PageBar query(Map<String, String> para) throws Exception;

	public TAppTheme get(Map<String, String> para) throws Exception;

	public Map<String, String> sort(Map<String, String> para) throws Exception;

}
