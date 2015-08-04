package com.energysh.egame.service;

import java.util.List;
import java.util.Map;

import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.util.PageBar;

public interface AppCategoryService {

	public Map<String, String> add(Map<String, String> para) throws Exception;

	public Map<String, String> del(Map<String, String> para) throws Exception;

	public Map<String, String> up(Map<String, String> para) throws Exception;

	public PageBar query(Map<String, String> para) throws Exception;

	public TAppCategory get(Map<String, String> para) throws Exception;

	public List<Object> getAllCategory() throws Exception;

	public TAppCategory queryById(int categoryId) throws Exception;

	public List<TAppCategory> queryCategoryByFatherId(Map<String, String> para) throws Exception;

	public List<TAppCategory> queryCategoryByLevel(Map<String, String> para) throws Exception;

}
