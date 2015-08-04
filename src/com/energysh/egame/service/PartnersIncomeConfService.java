package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.po.appstore.TPartnersIncomeConf;
import com.energysh.egame.util.PageBar;

public interface PartnersIncomeConfService {

	public PageBar query(Map<String, String> para) throws Exception;

	public Map<String, String> up(Map<String, String> para) throws Exception;

	public TPartnersIncomeConf get(Map<String, String> para) throws Exception;

}
