package com.energysh.egame.service;

import java.util.Map;

public interface LoginService {

	public Map<String, Object> queryUserBms(Map<String, String> para) throws Exception;

	public Map<String, Object> getUser(Map<String, String> para) throws Exception;

	public Map<String, String> upPassWord(Map<String, String> para) throws Exception;

	public Map<String, Object> queryUser(Map<String, String> para) throws Exception;

	public void addLogs(Map<String, String> para) throws Exception;

}
