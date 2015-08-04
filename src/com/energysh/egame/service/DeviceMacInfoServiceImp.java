package com.energysh.egame.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.energysh.egame.po.appstore.InstalledApp;
import com.energysh.egame.po.appstore.TApp;
import com.energysh.egame.po.appstore.TAppQihoo;
import com.energysh.egame.po.appstore.TDeviceMacInfo;
import com.energysh.egame.po.appstore.TMacLastBatch;
import com.energysh.egame.po.appstore.TMacLastBatchId;
import com.energysh.egame.po.appstore.TUserAppInfo;
import com.energysh.egame.util.Constants;

@SuppressWarnings("unchecked")
public class DeviceMacInfoServiceImp extends BaseService implements
		DeviceMacInfoService {

	public String add(String androidId, String deviceId, String batchId,
			int province, String ip, Integer appId, String ver, String osType) {
		TDeviceMacInfo info = new TDeviceMacInfo();
		info.setAndroidId(androidId);
		info.setDeviceId(deviceId);
		info.setBatchId(batchId);
		info.setCtime(new Date());
		info.setProvince(province);
		info.setIp(ip);
		info.setAppId(appId);
		info.setVer(ver);
		info.setOsType(osType);
		this.getAppstoreDao().save(info);
		return info.getMac().toString();
	}

	public void upProvince(TDeviceMacInfo info, int province, String ip) {
		info.setProvince(province);
		info.setIp(ip);
		this.getAppstoreDao().update(info);
	}

	public void addMacLastBatch(String mac, int appId, String batch) {
		List<Object> result = this.getAppstoreDao().findList(
				" from TMacLastBatch a where a.id.mac=? and a.id.appId=?",
				new Object[] { mac, appId });
		if (result.size() > 0) {
			TMacLastBatch lastBatch = (TMacLastBatch) result.get(0);
			if (!"".equals(batch) && !batch.contains("P")
					&& !batch.equals(lastBatch.getLastBatchId())) {
				lastBatch.setLastBatchId(batch);
				this.getAppstoreDao().update(lastBatch);
			}
		} else {
			TMacLastBatch lastBatch = new TMacLastBatch();
			TMacLastBatchId id = new TMacLastBatchId();
			id.setAppId(appId);
			id.setMac(mac);
			lastBatch.setId(id);
			lastBatch.setFirstBatchId(batch);
			lastBatch.setLastBatchId(batch);
			lastBatch.setCtime(new Date());
			this.getAppstoreDao().save(lastBatch);
		}
	}

	public TDeviceMacInfo getMac(String androidId, String deviceId) {
		List<Object> result = this.getAppstoreDao().findList(
				" from TDeviceMacInfo where androidId=? and deviceId=?",
				new Object[] { androidId, deviceId });
		if (result == null || result.size() == 0) {
			return null;
		} else {
			TDeviceMacInfo info = (TDeviceMacInfo) result.get(0);
			return info;
		}
	}

	public String findRealBatchId(String mac, String batchId, int appId) {
		String realBatchNo = "";
		// 1.如果批次号不包含P，则不变
		if (StringUtils.isNotBlank(batchId)) {
			if (!batchId.contains("P")) {
				realBatchNo = batchId;
				return realBatchNo;
			}
		}
		if (StringUtils.isBlank(realBatchNo)) {
			List<Object> result = this.getAppstoreDao().findList(
					" from TMacLastBatch a where a.id.mac=? and a.id.appId=?",
					new Object[] { mac, appId });
			if (result.size() > 0) {
				TMacLastBatch lastBatch = (TMacLastBatch) result.get(0);
				realBatchNo = lastBatch.getLastBatchId();
			}
		}
		if (StringUtils.isBlank(realBatchNo)) {
			if (appId == 128)
				realBatchNo = Constants.DEFAULT_BATCHNO;
			else
				realBatchNo = Constants.DEFAULT_BATCHJ + appId;
		}
		return realBatchNo;

	}

	public String getBatchNoByMac(int mac) {
		if (mac == 0) {
			return null;
		}
		TDeviceMacInfo deviceMacInfo = (TDeviceMacInfo) this.getAppstoreDao()
				.get(TDeviceMacInfo.class, Long.valueOf(mac + ""));
		if (deviceMacInfo == null) {
			return null;
		} else {
			return deviceMacInfo.getBatchId();
		}
	}

	public Map<String, InstalledApp> getAppByPackage(String packag) {
		Map<String, InstalledApp> map = new HashMap<String, InstalledApp>();
		TAppQihoo a = (TAppQihoo) this.getAppstoreDao().findObject(
				"from TAppQihoo a where a.qhPackageName=?",
				new Object[] { packag });
		if (a == null)
			return map;
		InstalledApp app = new InstalledApp();
		app.setApp(a.getQhDownloadUrl());
		app.setIcon(a.getQhIconUrl());
		app.setId("" + a.getQhAppId());
		app.setVersionCode(Integer.valueOf(a.getQhVersionCode()));
		app.setName(a.getQhName());
		app.setPackag(a.getQhPackageName());
		app.setSize(a.getQhApkSize());
		app.setVersion(a.getQhVersionName());
		app.setAgeLimit("");
		app.setCategoryId("");
		app.setClassify("");
		app.setCompany(a.getQhDeveloper());
		app.setCompaticity("");
		app.setDeployTime(a.getQhUpdateTime() + "");
		app.setLastestUpdate(a.getQhUpdateTime() + "");
		app.setNewFuture(a.getQhUpdateInfo());
		String[] url = a.getQhScreenshotsUrl().split(",");
		for (int i = 0; i < url.length; i++) {
			switch (i) {
			case 0:
				app.setPic1(url[i]);
			case 1:
				app.setPic2(url[i]);
			case 2:
				app.setPic3(url[i]);
			case 3:
				app.setPic4(url[i]);
			case 4:
				app.setPic5(url[i]);
			}
		}
		app.setRet(4);
		app.setShareContent("");
		app.setSummary(a.getQhDescription());
		app.setSupport("");

		map.put(packag, app);
		return map;
	}

	public Map<String, InstalledApp> getAppByPackag(String packag) {
		Map<String, InstalledApp> map = new HashMap<String, InstalledApp>();
		TApp a = (TApp) this.getAppstoreDao().findObject(
				"from TApp a where a.pakeage=?", new Object[] { packag });
		if (a == null)
			return map;
		InstalledApp app = new InstalledApp();
		app.setApp(a.getApp());
		app.setIcon(a.getIcon());
		app.setId("" + a.getId());
		app.setVersionCode(Integer.valueOf(a.getVersionCode()));
		app.setName(a.getName());
		app.setPackag(a.getPakeage());
		app.setSize(a.getAppSize().intValue());
		app.setVersion(a.getVersion());
		app.setAgeLimit(a.getAgeLimit());
		app.setCategoryId(a.getCategoryId() + "");
		app.setClassify("");
		app.setCompany(a.getDevelope());
		app.setCompaticity("");
		app.setDeployTime(a.getCtime() + "");
		app.setLastestUpdate(a.getUptime() + "");
		app.setNewFuture(a.getUpDesc());
		app.setPic1(a.getPic1());
		app.setPic2(a.getPic2());
		app.setPic3(a.getPic3());
		app.setPic4(a.getPic4());
		app.setPic5(a.getPic5());
		app.setRet(4);
		app.setShareContent("");
		app.setSummary(a.getAppDesc());
		app.setSupport("");

		map.put(packag, app);
		return map;
	}

	@Override
	public Map<String, TUserAppInfo> getAppFromUserInfo(String packageInfo,
			String mac) {
		Map<String, TUserAppInfo> map = new HashMap<String, TUserAppInfo>();
		TUserAppInfo a = (TUserAppInfo) this.getAppstoreDao().findObject(
				"from TUserAppInfo a where a.packageName=? and a.mac=?",
				new Object[] { packageInfo, mac });
		if (a == null)
			return map;
		map.put(packageInfo, a);
		return map;
	}

}