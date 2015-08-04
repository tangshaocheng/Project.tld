package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.po.appstore.TApp;
import com.energysh.egame.util.PageBar;

public interface AppService {

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, String> del(Map<String, String> para) throws Exception;

	public Map<String, String> up(Map<String, String> para) throws Exception;

	public PageBar query(Map<String, String> para) throws Exception;

	public TApp get(Map<String, String> para) throws Exception;

}
