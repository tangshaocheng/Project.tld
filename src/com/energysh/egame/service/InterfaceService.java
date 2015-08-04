package com.energysh.egame.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InterfaceService {

	public String appInstalledListSync(Map<String, String> para);

	public String checkPlatFormVersion(Map<String, String> para) throws Exception;

	public Map<String, Object> queryRecommendList(Map<String, String> para) throws Exception;

	public Map<String, Object> queryAppList(Map<String, String> para) throws Exception;

	public Map<String, Object> querySxjpList(Map<String, String> para) throws Exception;

	public Map<String, Object> getSxAppList(Map<String, String> para) throws Exception;

	public Map<String, Object> queryThemePicList(Map<String, String> para) throws Exception;

	public Map<String, Object> queryCommentList(Map<String, String> para) throws Exception;

	public Map<String, Object> discoveryList(Map<String, String> para) throws Exception;

	public Map<String, Object> addComment(Map<String, String> para) throws Exception;

	public Map<String, Object> getRank(Map<String, String> para) throws Exception;

	public Map<String, Object> nearmeApp(Map<String, String> para) throws Exception;

	public Map<String, Object> searchApp(Map<String, String> para) throws Exception;

	public Map<String, Object> getCategory(Map<String, String> para) throws Exception;

	public Map<String, Object> gameCenterAppList(Map<String, String> para) throws Exception;

	public boolean downloadApp(final Map<String, String> para, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public Map<String, Object> getCategoryInfo(Map<String, String> para) throws Exception;

	public Map<String, Object> getRelationAppList(Map<String, String> para) throws Exception;

	public String putXgPushToken(Map<String, String> para) throws Exception;

	public String downAppComplete(Map<String, String> para) throws Exception;

	public Map<String, Object> getAdConf(Map<String, String> para) throws Exception;

	public String AppInstallComplete(Map<String, String> para) throws Exception;

	public Map<String, Object> hotSearchApp(Map<String, String> para) throws Exception;
	
	public Map<String, Object> adAppList(Map<String, String> para) throws Exception;
	
	public Map<String, Object> preDownAppList(Map<String, String> para) throws Exception;
	
	public Map<String, Object> jmSwitch(Map<String, String> para) throws Exception;
}
