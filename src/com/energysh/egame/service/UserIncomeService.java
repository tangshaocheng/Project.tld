package com.energysh.egame.service;

import java.util.List;
import java.util.Map;

public interface UserIncomeService {

	public List<Map<String, Object>> queryList(Map<String, String> para) throws Exception;

	public Map<String, String> add(Map<String, String> para) throws Exception;

}
