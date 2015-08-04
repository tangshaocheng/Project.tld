package com.energysh.egame.service;

import java.util.List;
import java.util.Map;

import com.energysh.egame.util.PageBar;

public interface AccountService {

	public PageBar query(Map<String, String> para) throws Exception;

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, Object> get(Map<String, String> para) throws Exception;

	public Map<String, String> up(Map<String, String> para) throws Exception;

	public List<Map<String, Object>> queryBatch(Map<String, String> para) throws Exception;

	public List<String> getList(Map<String, String> para) throws Exception;

}
