package com.energysh.egame.service;

import java.util.Map;

import com.energysh.egame.po.appstore.InstalledApp;
import com.energysh.egame.po.appstore.TDeviceMacInfo;
import com.energysh.egame.po.appstore.TUserAppInfo;

public interface DeviceMacInfoService {

	public String add(String androidId, String deviceId, String batchId,
			int province, String ip, Integer appId, String ver, String osType);

	public void upProvince(TDeviceMacInfo info, int province, String ip);

	public void addMacLastBatch(String mac, int appId, String batch);

	public TDeviceMacInfo getMac(String androidId, String deviceId);

	public String findRealBatchId(String mac, String batchId, int appId);

	public String getBatchNoByMac(int mac);

	public Map<String, InstalledApp> getAppByPackage(String packag);

	public Map<String, InstalledApp> getAppByPackag(String packag);

	public Map<String, TUserAppInfo> getAppFromUserInfo(String packageInfo,
			String mac);

}
