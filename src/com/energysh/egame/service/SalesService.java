package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.util.PageBar;

public interface SalesService {

	public PageBar query(Map<String, String> para) throws Exception;

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, String> del(Map<String, String> para) throws Exception;

}
