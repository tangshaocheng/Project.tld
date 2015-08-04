package com.energysh.egame.service;

import java.util.List;
import java.util.Map;

import com.energysh.egame.po.appstore.TDeviceBatch;
import com.energysh.egame.util.PageBar;

public interface GenBatchService {

	public PageBar query(Map<String, String> para) throws Exception;

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public String nextBatchId() throws Exception;

	public List<TDeviceBatch> findAll() throws Exception;

}
