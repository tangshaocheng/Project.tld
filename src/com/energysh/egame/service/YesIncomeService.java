package com.energysh.egame.service;

import java.util.List;
import java.util.Map;

import com.energysh.egame.util.PageBar;

public interface YesIncomeService {

	public PageBar query(Map<String, String> para) throws Exception;

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public List<Map<String, Object>> queryList(Map<String, String> para) throws Exception;

}
