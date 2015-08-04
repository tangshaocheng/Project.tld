package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.util.PageBar;

public interface AppStatisticsService {

	public PageBar query(Map<String, String> para) throws Exception;

}
