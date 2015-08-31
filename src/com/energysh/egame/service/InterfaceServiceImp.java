package com.energysh.egame.service;

import java.io.IOException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.energysh.egame.dao.BaseDao;
import com.energysh.egame.exception.AppBizException;
import com.energysh.egame.exception.AppExcCodes;
import com.energysh.egame.po.appstore.InstalledApp;
import com.energysh.egame.po.appstore.MDevXgToken;
import com.energysh.egame.po.appstore.TApp;
import com.energysh.egame.po.appstore.TAppCategoryHot;
import com.energysh.egame.po.appstore.TAppComment;
import com.energysh.egame.po.appstore.TAppDownload;
import com.energysh.egame.po.appstore.TDeviceMacInfo;
import com.energysh.egame.po.appstore.TDownCompleteLog;
import com.energysh.egame.po.appstore.TGameUpdatePush;
import com.energysh.egame.po.appstore.TInstallCompleteLog;
import com.energysh.egame.po.appstore.TSearchLog;
import com.energysh.egame.po.appstore.TUserAppInfo;
import com.energysh.egame.util.Constants;
import com.energysh.egame.util.EnvConfigurer;
import com.energysh.egame.util.MarketUtils;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;
import com.energysh.egame.util.ParaUtils;
import com.energysh.egame.web.rs.InterfaceController;
import com.energysh.egame.xg.XgUtil;

@SuppressWarnings("unchecked")
public class InterfaceServiceImp extends BaseService implements InterfaceService {

	private ExecutorService executorService = Executors.newCachedThreadPool();
	private static final Logger LOGGER = Logger.getLogger(InterfaceController.class);

	public String appInstalledListSync(Map<String, String> para) {
		String mac = para.get("mac");
		if (StringUtils.isBlank(para.get("postContent"))) {
			return MarketUtils.getErrorJson("post content is empty");
		}
		JSONObject postContentJSON = JSONObject.fromObject(para.get("postContent"));
		List<Map<String, Object>> packageList = (List<Map<String, Object>>) postContentJSON.get("list");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rList = new ArrayList<Map<String, Object>>();
		StringBuilder updateDes = new StringBuilder("");
		List<String> newList = new ArrayList<String>();
		List<String> oldList = new ArrayList<String>();
		List<Map<String, Object>> l = this.getAppstoreDao()
				.findListMapBySql(" SELECT packageName FROM t_user_appInfo WHERE mac=?", new Object[] { mac });
		for (Map<String, Object> map : l) {
			oldList.add(map.get("packageName").toString());
		}
		for (Map<String, Object> map : packageList) {
			String packageInfo = String.valueOf(map.get("package"));
			Map<String, InstalledApp> appListMap = deviceMacInfoService.getAppByPackag(packageInfo);
			if (map.get("embeded").equals(1) && mac != null && !mac.equals("")) {
				Map<String, TUserAppInfo> recordeApp = deviceMacInfoService.getAppFromUserInfo(packageInfo, mac);
				newList.add(packageInfo);
				TUserAppInfo userInfo = new TUserAppInfo();
				userInfo.setMac(para.get("mac"));
				userInfo.setTime(new Date());
				userInfo.setAppVersion(String.valueOf(map.get("version")));
				userInfo.setPackageName(String.valueOf(map.get("package")));
				if (appListMap.get(packageInfo) != null) {
					userInfo.setAppName(appListMap.get(packageInfo).getName());
				}
				if (recordeApp.get(packageInfo) != null) {
					String version = recordeApp.get(packageInfo).getAppVersion();
					if (!(map.get("version").equals(version))) {
						this.getAppstoreDao().save(userInfo);
					}
				} else {
					this.getAppstoreDao().save(userInfo);
				}

			}

			if (appListMap.containsKey(packageInfo)) {
				InstalledApp app = appListMap.get(packageInfo);
				Map<String, Object> rMap = new HashMap<String, Object>();
				rMap.put("id", app.getId());
				rMap.put("name", app.getName());

				rMap.put("version", app.getVersion());
				String upVersion = map.get("version").toString();
				if (!app.getVersion().equals(upVersion.split("#")[0])) {
					updateDes.append("," + app.getName());
				}

				rMap.put("categoryId", app.getCategoryId());
				rMap.put("versionCode", app.getVersionCode());
				rMap.put("package", packageInfo);

				rMap.put("app", ParaUtils.checkAppUri(app.getApp(),
						StringUtils.isEmpty(para.get("batchId")) ? "" : para.get("batchId"),
						StringUtils.isEmpty(para.get("mac")) ? "" : para.get("mac"), "", rMap.get("id").toString()));
				rMap.put("size", app.getSize());
				rMap.put("summary", app.getSummary());
				rMap.put("newFuture", app.getNewFuture());
				rMap.put("support", app.getSupport());
				rMap.put("ret", app.getRet());
				rMap.put("icon", ParaUtils.checkPicUri(app.getIcon()));
				rMap.put("shareContent", app.getShareContent());
				rMap.put("heat", "4");
				rMap.put("pic1", ParaUtils.checkPicUri(app.getPic1()));
				rMap.put("pic2", ParaUtils.checkPicUri(app.getPic2()));
				rMap.put("pic3", ParaUtils.checkPicUri(app.getPic3()));
				rMap.put("pic4", ParaUtils.checkPicUri(app.getPic4()));
				rMap.put("pic5", ParaUtils.checkPicUri(app.getPic5()));
				Map<String, Object> info = new HashMap<String, Object>();
				info.put("company", app.getCompany());
				info.put("classify", app.getClassify());
				info.put("lastestUpdate", app.getLastestUpdate());
				info.put("version", app.getVersion());
				info.put("size", app.getSize());
				info.put("ageLimit", app.getAgeLimit());
				info.put("compaticity", app.getCompaticity());
				rMap.put("infomation", info);

				rList.add(rMap);
			}
		}
		oldList.removeAll(newList);
		if (oldList.size() > 0) {
			for (int i = 0; i < oldList.size(); i++) {
				this.getAppstoreDao().excuteBySql("DELETE FROM t_user_appInfo WHERE mac=? AND packageName=?",
						new Object[] { mac, oldList.get(i).toString() });
			}
		}
		if ("true".equals(para.get("pushType"))) {
			// 如果用户本地应用版本号和服务器版本号不一样，则下发应用需要更新PUSH通知，每天只通知一次。
			if (StringUtils.isNotBlank(mac) && StringUtils.isNotBlank(updateDes.toString())) {
				String title = "款应用有更新！";

				updatePush((updateDes.toString().split(",").length - 1) + title,
						updateDes.substring(1) + "有新版本了，请前往AppStore进行更新！", mac);
			}
		}
		resultMap.put("list", rList);
		resultMap.put("size", rList.size());
		return MarketUtils.getResJson(JSONObject.fromObject(resultMap).toString());
	}

	/**
	 * //如果用户本地应用版本号和服务器版本号不一样，则下发应用需要更新PUSH通知，每天只通知一次。
	 * 
	 * @param upVersion
	 * @param serverVersion
	 *            提示语：仙魔奇侠有新版本了，进入AppStore更新！
	 */
	public void updatePush(String title, String updateDes, String mac) {

		// String token = "17f452cc9f7bc0604edfb0582d16c235e2a55116";
		String token = "";
		long accessId = 2100045838; // appstore
		String secretKey = "64d8aabf59194e5992429fe15b563c69";
		int appId = 1;// appId=1为appStore,为2为gameCenter

		List<Object> tokenList = this.getAppstoreDao().findList("from MDevXgToken where mac=?", new Object[] { mac });
		if (tokenList.size() == 0)
			return;

		MDevXgToken tt = (MDevXgToken) tokenList.get(0);
		token = tt.getToken();

		Date date = new Date();
		List<TGameUpdatePush> list = this.getAppstoreDao().findList(
				"from TGameUpdatePush a where a.mac=? and a.date=? and a.appId=?",
				new Object[] { mac, new Date(), appId });
		if (list.size() == 0) {
			// 使用信鸽发送push
			new XgUtil(title, updateDes).pushSingleDeviceNotification(token, accessId, secretKey);
			LOGGER.info("token:[" + token + "];push content:[" + updateDes + "]");

			TGameUpdatePush push = new TGameUpdatePush();
			push.setAppId(appId);
			push.setMac(mac);
			push.setDate(date);
			push.setCtime(date);
			this.getAppstoreDao().save(push);
		}
	}

	/**
	 * 判断平台版本（目前平台版本概念不存在，使用应用版本检查）
	 */
	public String checkPlatFormVersion(Map<String, String> para) throws AppBizException {
		return this.checkAppVersion(para);
	}

	/**
	 * 判断应用版本
	 * 
	 * @param para
	 * @return
	 * @throws AppBizException
	 */
	private String checkAppVersion(Map<String, String> para) throws AppBizException {
		String mac = para.get("mac");
		try {
			if (StringUtils.isEmpty(mac)) {// 如果mac没有，需要找回，或新增
				String androidId = ParaUtils.checkStringAndGet(para.get("androidId"), "androidId");
				String deviceId = ParaUtils.checkStringAndGet(para.get("deviceId"), "deviceId");
				String batchId = ParaUtils.checkStringAndGet(para.get("batchId"), "batchId");

				String ver = para.get("ver") == null ? "" : para.get("ver");

				TDeviceMacInfo macInfo = deviceMacInfoService.getMac(androidId, deviceId);
				if (macInfo != null) {
					mac = macInfo.getMac().toString();
				}
				if (StringUtils.isBlank(mac)) {
					mac = deviceMacInfoService.add(androidId, deviceId, batchId, getProvince(para), para.get("ip"),
							Constants.DEFAULT_APP_ID, ver, para.get("osType"));
				} else {
					if (StringUtils.isEmpty(macInfo.getIp()) || "".equals(macInfo.getIp())) {
						deviceMacInfoService.upProvince(macInfo, getProvince(para), para.get("ip"));
					}
				}
			} else {
				if (!StringUtils.isNumeric(mac)) {
					throw new AppBizException(AppExcCodes.E_INVALID_PARA, "mac is not number");
				}
				TDeviceMacInfo info = (TDeviceMacInfo) this.getAppstoreDao().get(TDeviceMacInfo.class,
						Integer.valueOf(mac));
				if (info == null) {
					throw new AppBizException(AppExcCodes.E_INVALID_PARA, "mac is not found");
				} else {
					if (StringUtils.isEmpty(info.getIp()) || "".equals(info.getIp())) {
						deviceMacInfoService.upProvince(info, getProvince(para), para.get("ip"));
					}
				}
			}
		} catch (Exception e) {
			System.out
					.println("mac=" + mac + "androidId=" + para.get("androidId") + "deviceId=" + para.get("deviceId"));
			LOGGER.error("mac=" + mac + "androidId=" + para.get("androidId") + "deviceId=" + para.get("deviceId"));
		}

		// 记录每个游戏每台手机最后一次访问的batchId
		deviceMacInfoService.addMacLastBatch(mac, Constants.DEFAULT_APP_ID,
				para.get("batchId") == null ? "" : para.get("batchId").toString());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appname", "AppStore");
		map.put("versioncode", "14");
		map.put("versionname", "1.0");
		map.put("isForced", "1"); // 1为强制更新，其他为普通更新
		map.put("updateinfo", "test");
		map.put("downUrl", "http://api.np.mobilem.360.cn/redirect/down/?from=aiqi&appid=56");
		map.put("mac", mac);
		return MarketUtils.getResJson(JSONObject.fromObject(map).toString());
	}

	private int getProvince(Map<String, String> para) {
		int province = 0;
		try {
			if (this.getMemProgrammer().get("getProvince_province_" + para.get("ip")) == null) {
				if (null == para.get("province")) {
					List<Object> provinceList = this.getAppstoreDao().findListBySql(
							"select province from t_province_ip where inet_aton(?) >= ip_start and ip_end >= inet_aton(?)",
							new Object[] { para.get("ip"), para.get("ip") });
					if (provinceList.size() > 0) {
						Map<String, Object> map = (Map<String, Object>) provinceList.get(0);
						if (StringUtils.isNotEmpty(String.valueOf(map.get("province"))))
							province = Integer.parseInt(map.get("province").toString());
					}
				} else {
					if (!"".equals(para.get("province")) && StringUtils.isNumeric(para.get("province")))
						province = Integer.parseInt(para.get("province"));
				}

				this.getMemProgrammer().set("getProvince_province_" + para.get("ip"), province);
			} else {
				province = (Integer) this.getMemProgrammer().get("getProvince_province_" + para.get("ip"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return province;
	}

	@Override
	public Map<String, Object> queryRecommendList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		int sort = 1;
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("SELECT * FROM ( ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*,t4.rtype  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
		sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
		sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort, t5.sort) t1 ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*, '' rtype FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort) t2 ");
		sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
		for (Map<String, Object> map : rlist) {
			String subjId = map.get("subj_id").toString();
			if ("0".equals(subjId))
				cThemeMap.put(subjId, 0);
			else if (cThemeMap.containsKey(subjId))
				cThemeMap.put(subjId, 2);
			else
				cThemeMap.put(subjId, 1);
		}
		Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
		for (String key : cThemeMap.keySet()) {
			Integer value = cThemeMap.get(key);
			if (value > 1)
				isThemeMap.put(key, true);
			else
				isThemeMap.put(key, false);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String type = "1";
		String subPic = "";
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			String subjId = map.get("subj_id").toString();
			if (i == 0) {
				subjectMap.put("subjectName", map.get("subj_name"));
			}
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			if (isThemeMap.get(subjId)) {
				if (!countMap.containsKey(subjId)) {
					appMap.put("id", Integer.parseInt(subjId));
					appMap.put("pic", ParaUtils.checkPicUri(map.get("subj_pic")));
					appMap.put("type", 2);
					list.add(appMap);
					countMap.put(subjId, true);
				}
				continue;
			}
			appMap.put("id", Integer.parseInt(map.get("id").toString()));

			subPic = ParaUtils.checkPicUri(map.get("icon"));
			if (null != map.get("subj_pic") && !"".equals(map.get("subj_pic").toString()))
				subPic = ParaUtils.checkPicUri(map.get("subj_pic"));
			appMap.put("pic", subPic);
			if (null != map.get("rtype") && !"".equals(map.get("rtype").toString()))
				type = map.get("rtype").toString();
			appMap.put("type", type);

			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("package", map.get("pakeage"));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> queryAppList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		String type = para.get("type");
		int sort = 0;
		List<Object> plist = new ArrayList<Object>();
		if (mu.equals(type, "1")) {
			sort = 2;
		} else if (mu.equals(type, "2")) {
			sort = 3;
		} else if (mu.equals(type, "3")) {
			sort = 5;
		} else if (mu.equals(type, "4")) {
			sort = 7;
		} else if (mu.equals(type, "5")) {
			sort = 9;
		}
		String recommendId = para.get("recommendId");// 专题图片下的游戏列表获取
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		if (StringUtils.isNotEmpty(recommendId)) {
			sql.append("SELECT * FROM ( ");
			sql.append(
					"SELECT t5.theme_id subj_id, CONCAT(t4.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*,t4.rtype  FROM ");
			sql.append(" t_app_theme t4 ");
			sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
			sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
			sql.append(
					"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
			sql.append("ORDER BY t5.sort) t1 where t1.subj_id= " + recommendId);
		} else {
			sql.append("SELECT * FROM ( ");
			sql.append("SELECT * FROM ( ");
			sql.append(
					"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*,t4.rtype  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
							+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
			sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
			sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
			sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
			sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
			sql.append(
					"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
			sql.append("ORDER BY t3.sort, t5.sort) t1 ");
			sql.append("UNION ALL ");
			sql.append("SELECT * FROM ( ");
			sql.append(
					"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*,'' rtype  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
							+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
			sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
			sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
			sql.append(
					"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
			sql.append("ORDER BY t3.sort) t2 ");
			sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		}
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
		for (Map<String, Object> map : rlist) {
			String subjId = map.get("subj_id").toString();
			if (StringUtils.isNotEmpty(recommendId)) {
				cThemeMap.put(subjId, 1);
				break;
			} else {
				if ("0".equals(subjId))
					cThemeMap.put(subjId, 0);
				else if (cThemeMap.containsKey(subjId))
					cThemeMap.put(subjId, 2);
				else
					cThemeMap.put(subjId, 1);
			}
		}
		Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
		for (String key : cThemeMap.keySet()) {
			Integer value = cThemeMap.get(key);
			if (value > 1)
				isThemeMap.put(key, true);
			else
				isThemeMap.put(key, false);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> recordeApp = new ArrayList<Map<String, Object>>();
		Integer ver = Integer.parseInt(para.get("ver").split("\\.")[0]);
		if ((para.get("type").equals("1") | para.get("type").equals("2"))
				&& (para.get("mac") != null && para.get("mac") != "" && ver < 5)) {
			recordeApp = this.getAppstoreDao().findListMapBySql("SELECT packageName FROM t_user_appInfo WHERE mac = ?",
					new Object[] { para.get("mac") });
			for (int i = 0; i < recordeApp.size(); i++) {
				Map<String, Object> map1 = recordeApp.get(i);
				for (int j = 0; j < rlist.size(); j++) {
					Map<String, Object> map2 = rlist.get(j);
					if (map1.get("packageName").equals(map2.get("pakeage"))) {
						rlist.remove(j);
					}
				}
			}
			if (rlist.size() < 4) {
				rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
			}
		}

		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			String subjId = map.get("subj_id").toString();
			if (i == 0) {
				subjectMap.put("subjectName", map.get("subj_name"));
			}
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			if (isThemeMap.get(subjId)) {
				if (!countMap.containsKey(subjId)) {
					appMap.put("id", Integer.parseInt(subjId));
					list.add(appMap);
					countMap.put(subjId, true);
				}
				continue;
			}
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			if (map.get("uptime") != null) {
				infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
				appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			}

			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);

			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> queryThemePicList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		String type = para.get("type");
		int sort = 0;
		List<Object> plist = new ArrayList<Object>();
		if (mu.equals(type, "1")) {
			sort = 4;
		} else if (mu.equals(type, "2")) {
			sort = 6;
		} else if (mu.equals(type, "3")) {
			sort = 8;
		}
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("SELECT * FROM ( ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*,t4.rtype  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
		sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
		sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort, t5.sort) t1 ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*,'' rtype  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort) t2 ");
		sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
		for (Map<String, Object> map : rlist) {
			String subjId = map.get("subj_id").toString();
			if ("0".equals(subjId))
				cThemeMap.put(subjId, 0);
			else if (cThemeMap.containsKey(subjId))
				cThemeMap.put(subjId, 2);
			else
				cThemeMap.put(subjId, 1);
		}
		Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
		for (String key : cThemeMap.keySet()) {
			Integer value = cThemeMap.get(key);
			if (value > 1)
				isThemeMap.put(key, true);
			else
				isThemeMap.put(key, false);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String rtype = "1";
		String subPic = "";
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			String subjId = map.get("subj_id").toString();
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			if (isThemeMap.get(subjId)) {
				if (!countMap.containsKey(subjId)) {
					appMap.put("id", Integer.parseInt(subjId));
					appMap.put("pic", ParaUtils.checkPicUri(map.get("subj_pic")));
					appMap.put("type", 2);
					list.add(appMap);
					countMap.put(subjId, true);
				}
				continue;
			}
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("pic", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("type", 1);

			subPic = ParaUtils.checkPicUri(map.get("icon"));
			if (null != map.get("subj_pic") && !"".equals(map.get("subj_pic").toString()))
				subPic = ParaUtils.checkPicUri(map.get("subj_pic"));
			appMap.put("pic", subPic);
			if (null != map.get("rtype") && !"".equals(map.get("rtype").toString()))
				rtype = map.get("rtype").toString();
			appMap.put("type", rtype);

			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> queryCommentList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		StringBuffer sql = new StringBuffer();
		List<Object> plist = new ArrayList<Object>();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("SELECT * FROM t_app_comment where 1=1");
		if (StringUtils.isNotEmpty(para.get("appId"))) {
			sql.append(" and app_id=?");
			plist.add(para.get("appId"));
		}
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> temp : rlist) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("commentTitle", temp.get("title"));
			map.put("userId", temp.get("user_id"));
			map.put("userName", temp.get("nickname"));
			map.put("ret", temp.get("grade"));
			map.put("comment", temp.get("content"));
			map.put("time", mu.formateDate(temp.get("ctime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(map);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> addComment(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		TAppComment po = new TAppComment();
		if (para.get("title") != null)
			po.setTitle(para.get("title"));
		if (para.get("nickname") != null)
			po.setNickname(para.get("nickname"));
		if (para.get("appId") != null)
			po.setAppId(mu.toInt(para.get("appId")));
		if (para.get("star") != null)
			po.setGrade(mu.toInt(para.get("star")));
		if (para.get("comment") != null)
			po.setContent(para.get("comment"));
		po.setCtime(new Date());
		this.getAppstoreDao().save(po);
		subjectMap.put("msg", "提交成功");
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> getRank(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		String type = para.get("type");
		int sort = 0;
		List<Object> plist = new ArrayList<Object>();
		if (mu.equals(type, "1")) {
			sort = 10;
		} else if (mu.equals(type, "2")) {
			sort = 11;
		} else if (mu.equals(type, "3")) {
			sort = 12;
		}
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("SELECT * FROM ( ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
		sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
		sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort, t5.sort) t1 ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort) t2 ");
		sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
		for (Map<String, Object> map : rlist) {
			String subjId = map.get("subj_id").toString();
			if ("0".equals(subjId))
				cThemeMap.put(subjId, 0);
			else if (cThemeMap.containsKey(subjId))
				cThemeMap.put(subjId, 2);
			else
				cThemeMap.put(subjId, 1);
		}
		Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
		for (String key : cThemeMap.keySet()) {
			Integer value = cThemeMap.get(key);
			if (value > 1)
				isThemeMap.put(key, true);
			else
				isThemeMap.put(key, false);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			String subjId = map.get("subj_id").toString();
			if (i == 0) {
				subjectMap.put("subjectName", map.get("subj_name"));
			}
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			if (isThemeMap.get(subjId)) {
				if (!countMap.containsKey(subjId)) {
					appMap.put("id", Integer.parseInt(subjId));
					list.add(appMap);
					countMap.put(subjId, true);
				}
				continue;
			}
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			if (map.get("uptime") != null) {
				infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
				appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			}
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);

			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> nearmeApp(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		int sort = 13;
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("SELECT * FROM ( ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
		sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
		sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort, t5.sort) t1 ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort) t2 ");
		sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
		for (Map<String, Object> map : rlist) {
			String subjId = map.get("subj_id").toString();
			if ("0".equals(subjId))
				cThemeMap.put(subjId, 0);
			else if (cThemeMap.containsKey(subjId))
				cThemeMap.put(subjId, 2);
			else
				cThemeMap.put(subjId, 1);
		}
		Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
		for (String key : cThemeMap.keySet()) {
			Integer value = cThemeMap.get(key);
			if (value > 1)
				isThemeMap.put(key, true);
			else
				isThemeMap.put(key, false);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			String subjId = map.get("subj_id").toString();
			if (i == 0) {
				subjectMap.put("subjectName", map.get("subj_name"));
			}
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			if (isThemeMap.get(subjId)) {
				if (!countMap.containsKey(subjId)) {
					appMap.put("id", Integer.parseInt(subjId));
					list.add(appMap);
					countMap.put(subjId, true);
				}
				continue;
			}
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	/**
	 * 对搜索结果进行排序 map.get("id") map.get("name") map.get("count")
	 * 
	 * @return
	 */
	private List<Map<String, Object>> sortSearchList(List<Map<String, Object>> list, String keyName) {
		List<Map<String, Object>> rrList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		tempList.addAll(list);
		for (Map<String, Object> mm : list) {
			if (mm.get("name").toString().equalsIgnoreCase(keyName)) {
				rrList.add(mm);
				tempList.remove(mm);
			}
		}
		rrList.addAll(tempList);

		return rrList;
	}

	/**
	 * method=searchApp&keyName=QQ&mac=12:34:56:78:90:AB&pageNo=1&pageSize=20&
	 * language=CN&batchId=20140828_S001_BAB 1.以完全匹配优先排列 2.以字数短优先排列
	 * 3.以点击下载次数优先排列 4.以ID号顺序排列 对处理过的查询数据按以上条件排序
	 */
	@Override
	public Map<String, Object> searchApp(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		String keyName = para.get("keyName");

		TSearchLog log = new TSearchLog();
		log.setBatchId(para.get("batchId"));
		log.setCtime(new Date());
		log.setKeyName(keyName);
		log.setLanguage(para.get("language"));
		log.setMac(para.get("mac"));
		log.setVer(para.get("ver"));
		log.setAppType(para.get("appType"));
		this.getAppstoreDao().save(log);

		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		if (mu.isBlank(keyName)) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			subjectMap.put("list", list);
			subjectMap.put("size", list.size());
			rmap.put("data", subjectMap);
			return rmap;
		}
		sql.append("FROM t_app t6 ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id left join t_app_category_hot t8 on t6.id=t8.app_id ");
		sql.append("WHERE t6.name LIKE '%" + keyName + "%'");
		sql.append("OR t6.app_tag LIKE '%" + keyName + "%'");
		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) " + sql, plist.toArray());
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum("30");
		sql.insert(0, "SELECT t7.cat1_name, t7.cat2_name, t6.*,t8.count ");
		List<Map<String, Object>> rlist = this.getAppstoreDao()
				.findListMapPageBySql(sql.toString() + " ORDER BY t8.count desc", plist.toArray(), pb);
		DecimalFormat df = new DecimalFormat("#.00");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		rlist = sortSearchList(rlist, keyName);
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			list.add(appMap);
		}
		if (rlist.size() < 30) {
			para.put("type", "3");
			Map<String, Object> rank = getRank(para);
			Map<String, Object> obj = (Map<String, Object>) rank.get("data");
			List<Map<String, Object>> rankList = (List<Map<String, Object>>) obj.get("list");
			for (int i = 0; i < 30 - rlist.size(); i++) {
				list.add(rankList.get(i));
			}
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> getCategory(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		int totalSize = this.getAppstoreDao().findIntBySql(
				"SELECT COUNT(1) FROM t_app_category t1 WHERE t1.level = 2 ORDER BY t1.level, t1.seq", plist.toArray());
		List<Map<String, Object>> tlist = this.getAppstoreDao().findListMapPageBySql(
				"SELECT t1.* FROM t_app_category t1 WHERE t1.level = 2 ORDER BY t1.level, t1.seq", plist.toArray(), pb);
		sql.append("SELECT t1.* FROM t_app_category t1 WHERE t1.level = 3 ORDER BY t1.level, t1.seq");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < tlist.size(); i++) {
			Map<String, Object> map = tlist.get(i);
			Map<String, Object> catMap = new LinkedHashMap<String, Object>();
			catMap.put("categoryId", map.get("id"));
			catMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			catMap.put("name", map.get("name"));
			List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < rlist.size(); j++) {
				Map<String, Object> temp = rlist.get(j);
				if (!map.get("id").toString().equals(temp.get("father_id").toString()))
					continue;
				Map<String, Object> subMap = new HashMap<String, Object>();
				subMap.put("subCategoryId", temp.get("id"));
				subMap.put("subCategoryName", temp.get("name"));
				subMap.put("subIcon", ParaUtils.checkPicUri(map.get("icon")));
				subList.add(subMap);
			}
			if (subList.isEmpty()) {
				catMap.put("type", 0);
			} else {
				catMap.put("type", 1);
				catMap.put("subCategory", subList);
			}
			list.add(catMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> gameCenterAppList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		String type = para.get("type");
		int sort = 0;
		List<Object> plist = new ArrayList<Object>();
		if (mu.equals(type, "1")) {
			sort = 14;
		} else if (mu.equals(type, "2")) {
			sort = 15;
		} else if (mu.equals(type, "3")) {
			sort = 16;
		} else if (mu.equals(type, "4")) {
			sort = 17;
		}
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("FROM ( ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
		sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
		sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort, t5.sort) t1 ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort) t2 ");
		sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) " + sql, plist.toArray());
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		sql.insert(0, "SELECT * ");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	/**
	 * 应用下载
	 * 
	 * @param para
	 * @param request
	 * @param response
	 * @throws AppBizException
	 * @throws IOException
	 */
	public boolean downloadApp(final Map<String, String> para, HttpServletRequest request, HttpServletResponse response)
			throws AppBizException, IOException {
		String fileName = ParaUtils.checkStringAndGet(para.get("appUrl"), "appUrl");
		final BaseDao appstoreDao = getAppstoreDao();
		executorService.execute(new Runnable() {
			public void run() {
				if (StringUtils.isNotBlank(para.get("appId")) && StringUtils.isNumeric(para.get("appId"))) {
					int appId = 0;
					try {
						appId = ParaUtils.checkNumberAndGet(para.get("appId"), "appId");
					} catch (AppBizException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int user_Id = 0;
					if (null != para.get("userId") && !"".equals(para.get("userId"))) {
						user_Id = Integer.valueOf(para.get("userId"));
					}
					final int userId = user_Id;
					Date ctime = new Date();
					TAppDownload appDownload = new TAppDownload();
					appDownload.setAppId(appId);
					appDownload.setCtime(ctime);
					appDownload.setUserId(userId);
					appDownload.setBatchId(para.get("batchId"));
					appDownload.setMac(para.get("mac"));
					appstoreDao.save(appDownload);

					TAppCategoryHot hot = (TAppCategoryHot) appstoreDao.findObject("from TAppCategoryHot where appId=?",
							new Object[] { appDownload.getAppId() });
					if (hot == null) {
						TApp app = (TApp) appstoreDao.findObject("from TApp where id=?",
								new Object[] { appDownload.getAppId() });
						if (app != null) {
							hot = new TAppCategoryHot();
							hot.setAppId(appDownload.getAppId());
							hot.setCategoryId(app.getCategoryId());
							hot.setCount(1);
							hot.setCtime(ctime);
							appstoreDao.save(hot);
						}
					} else {
						hot.setCount(hot.getCount() + 1);
						hot.setCtime(ctime);
						appstoreDao.update(hot);
					}

				}
			}
		});
		// if (!StringUtils.startsWith(fileName.toString(), "http://"))
		// return false;
		response.sendRedirect(fileName);
		return true;

	}

	@Override
	public Map<String, Object> getCategoryInfo(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		int categoryId = NumberUtils.toInt(para.get("categoryId"));
		if (categoryId == 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			subjectMap.put("list", list);
			subjectMap.put("size", list.size());
			subjectMap.put("totalSize", list.size());
			rmap.put("data", subjectMap);
			return rmap;
		}
		plist.add(categoryId);

		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));

		if ("good".equals(para.get("cType"))) { // 精品类别
			sql.append("from t_app t1 INNER JOIN t_app_category_good b on t1.id=b.app_id and b.category_id=?");
		} else if ("hot".equals(para.get("cType"))) { // 热门类别
			sql.append(
					"from t_app t1 INNER JOIN t_app_category_hot b on t1.id=b.app_id and b.category_id=? order by b.count desc");
			pb.setEveryPageNum("30");
		} else { // 其他类别
			sql.append(
					"from t_app t1 INNER JOIN t_app_category_hot b on t1.id=b.app_id and b.category_id=? order by b.count desc");
			// sql.append("FROM t_app t1 WHERE t1.category_id = ?");
		}

		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) " + sql, plist.toArray());

		sql.insert(0, "SELECT t1.* ");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> categoryMap = new HashMap<String, String>();
		if (rlist.size() > 0) {
			categoryMap = getCategory();
		}
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", categoryMap.get(map.get("category_id").toString()));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	public Map<String, String> getCategory() {
		Map<String, String> categoryMap = new HashMap<String, String>();
		List<Map<String, Object>> clist = this.getAppstoreDao().findListMapBySql("select * from t_app_category t1",
				null);
		for (Map<String, Object> mm : clist) {
			categoryMap.put(mm.get("id").toString(), mm.get("name").toString());
		}
		return categoryMap;
	}

	@Override
	public Map<String, Object> getRelationAppList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		int appId = NumberUtils.toInt(para.get("appId"));
		if (appId == 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			subjectMap.put("list", list);
			subjectMap.put("size", list.size());
			subjectMap.put("totalSize", list.size());
			rmap.put("data", subjectMap);
			return rmap;
		}
		plist.add(appId);
		plist.add(appId);
		sql.append("FROM t_app WHERE id != ? AND category_id = (SELECT category_id FROM t_app WHERE id = ?)");
		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) " + sql, plist.toArray());
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum("20");
		sql.insert(0, "SELECT * ");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> categoryMap = new HashMap<String, String>();
		if (rlist.size() > 0) {
			List<Map<String, Object>> clist = this.getAppstoreDao().findListMapBySql("select * from t_app_category t1",
					null);
			for (Map<String, Object> mm : clist) {
				categoryMap.put(mm.get("id").toString(), mm.get("name").toString());
			}
		}
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", categoryMap.get(map.get("category_id").toString()));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	public String putXgPushToken(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		String mac = para.get("mac");
		String token = para.get("token");
		String batchId = para.get("batchId");

		List<Object> list = this.getAppstoreDao().findList("from MDevXgToken where token=?", new Object[] { token });
		if (list.size() > 0)
			return MarketUtils.getResJson("success");

		MDevXgToken tt = new MDevXgToken();
		tt.setMac(mac);
		tt.setToken(token);
		tt.setBatchId(batchId);
		tt.setCtime(new Date());
		try {
			this.getAppstoreDao().save(tt);
			rmap.put("msg", "success");
			return MarketUtils.getResJson(JSONObject.fromObject(rmap).toString());
		} catch (Exception e) {
			return MarketUtils.getErrorJson("error");
		}
	}

	private DeviceMacInfoService deviceMacInfoService;

	public void setDeviceMacInfoService(DeviceMacInfoService deviceMacInfoService) {
		this.deviceMacInfoService = deviceMacInfoService;
	}

	@Override
	public String downAppComplete(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		TDownCompleteLog log = new TDownCompleteLog();
		log.setBatchId(para.get("batchId"));
		log.setCtime(new Date());
		log.setAppId(para.get("appId"));
		log.setLanguage(para.get("language"));
		log.setMac(para.get("mac"));
		log.setVer(para.get("ver"));
		log.setAppType(para.get("appType"));
		this.getAppstoreDao().save(log);
		rmap.put("msg", "success");
		return MarketUtils.getResJson(JSONObject.fromObject(rmap).toString());
	}

	@Override
	public String AppInstallComplete(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		TInstallCompleteLog log = new TInstallCompleteLog();
		log.setBatchId(para.get("batchId"));
		log.setCtime(new Date());
		log.setAppId(para.get("appId"));
		log.setLanguage(para.get("language"));
		log.setMac(para.get("mac"));
		log.setVer(para.get("ver"));
		log.setAppType(para.get("appType"));
		this.getAppstoreDao().save(log);
		rmap.put("msg", "success");
		return MarketUtils.getResJson(JSONObject.fromObject(rmap).toString());
	}

	@Override
	public Map<String, Object> querySxjpList(Map<String, String> para) throws Exception {
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		sql.append(
				"select t2.id,t4.type,t4.pic,t4.des,t4.title from t_sx_app_theme_bag_sort t1 INNER JOIN t_app_theme_bag t2 on t1.theme_bag_id =t2.id");
		sql.append(
				" INNER JOIN t_app_theme_bag_list t3 on t2.id = t3.theme_bag_id INNER JOIN t_app_theme t4 on t3.sub_id=t4.id");
		sql.append(" order by t1.sort");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());

		List<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> mm : rlist) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("appListType", mm.get("id").toString());
			if (StringUtils.isEmpty(String.valueOf(mm.get("type"))))
				continue;
			int type = Integer.valueOf(mm.get("type").toString());
			m.put("type", type);
			m.put("pic", ParaUtils.checkPicUri(mm.get("pic").toString()));
			// if(2==type) {
			m.put("title", mm.get("title"));
			m.put("description", mm.get("des"));
			// }
			ll.add(m);
		}
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		subjectMap.put("list", ll);
		subjectMap.put("size", ll.size());
		subjectMap.put("totalSize", ll.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	/**
	 * 23 三星独家定制 24 三星Specials 25 三星付费排行 26 三星免费排行 27 三星畅销排行
	 */
	@Override
	public Map<String, Object> getSxAppList(Map<String, String> para) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t7.name as cat2_name,t6.* from t_app_theme_bag t2  ");
		sql.append(" INNER JOIN t_app_theme_bag_list t3 on t2.id = t3.theme_bag_id ");
		sql.append(" INNER JOIN t_app t6 on t3.sub_id=t6.id ");
		sql.append(" INNER JOIN t_app_category t7 on t6.category_id=t7.id ");
		sql.append(" and t2.id=?");
		sql.append(" order by t3.sort");

		MyUtil mu = MyUtil.getInstance();
		int stype = Integer.valueOf(para.get("type"));
		if ("1".equals(para.get("type")))
			stype = 23;
		else if ("2".equals(para.get("type")))
			stype = 24;
		else if ("3".equals(para.get("type"))) {
			if ("1".equals(para.get("lxType")))
				stype = 25;
			else if ("2".equals(para.get("lxType")))
				stype = 26;
			else if ("3".equals(para.get("lxType")))
				stype = 27;
		} else {
			sql = new StringBuffer();
			sql.append("select t7.name as cat2_name,t6.*,t4.rtype from t_app_theme_bag t2 ");
			sql.append(
					" INNER JOIN t_app_theme_bag_list t3 on t2.id = t3.theme_bag_id INNER JOIN t_app_theme t4 on t3.sub_id=t4.id ");
			sql.append(" inner JOIN t_app_theme_list t5 on t4.id = t5.theme_id ");
			sql.append(" INNER JOIN t_app t6 on t5.app_id=t6.id");
			sql.append(" INNER JOIN t_app_category t7 on t6.category_id=t7.id");
			sql.append(" and t2.id=?");
			sql.append(" order by t5.sort");
		}

		List<Object> plist = new ArrayList<Object>();

		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);

		plist.add(stype);

		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) from (" + sql + ") aa", plist.toArray());
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> categoryMap = new HashMap<String, String>();
		if (rlist.size() > 0) {
			categoryMap = getCategory();
		}
		String type = "1";
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			if (null != map.get("rtype") && !"".equals(map.get("rtype").toString()))
				type = map.get("rtype").toString();
			appMap.put("type", Integer.valueOf(type));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", categoryMap.get(map.get("category_id").toString()));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}

		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> getAdConf(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		subjectMap.put("switch", 0);
		rmap.put("data", subjectMap);
		return rmap;
	}

	// 热门应用51
	@Override
	public Map<String, Object> hotSearchApp(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		// List<Object> list =
		// this.getAppstoreDao().findListMax("from TSearchHot a where
		// a.appType=? order by id desc",
		// new Object[] { Integer.valueOf(para.get("appType")) }, 10);
		List<Map<String, String>> rList = new ArrayList<Map<String, String>>();
		// for (Object oo : list) {
		// Map<String, String> tempMap = new HashMap<String, String>();
		// TSearchHot hot = (TSearchHot) oo;
		// tempMap.put("keyName", hot.getKeyName());
		// rList.add(tempMap);
		// }
		StringBuffer sql = new StringBuffer();
		sql.append("select t7.name as cat2_name,t6.* from t_app_theme_bag t2  ");
		sql.append(" INNER JOIN t_app_theme_bag_list t3 on t2.id = t3.theme_bag_id ");
		sql.append(" INNER JOIN t_app t6 on t3.sub_id=t6.id ");
		sql.append(" INNER JOIN t_app_category t7 on t6.category_id=t7.id ");
		sql.append(" and t2.id=?");
		sql.append(" order by t3.sort");

		MyUtil mu = MyUtil.getInstance();
		int stype = 51;

		List<Object> plist = new ArrayList<Object>();
		plist.add(stype);

		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);

		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, String> tempMap = new HashMap<String, String>();
			tempMap.put("keyName", map.get("app_tag").toString());
			rList.add(tempMap);
		}

		subjectMap.put("list", rList);
		subjectMap.put("size", rList.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> discoveryList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		int sort = 40;
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		sql.append("SELECT * FROM ( ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*,t4.rtype  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.theme_bag_id = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
		sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
		sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort, t5.sort) t1 ");
		sql.append("UNION ALL ");
		sql.append("SELECT * FROM ( ");
		sql.append(
				"SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*, '' rtype FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.theme_bag_id = "
						+ sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
		sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
		sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
		sql.append(
				"LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
		sql.append("ORDER BY t3.sort) t2 ");
		sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
		for (Map<String, Object> map : rlist) {
			String subjId = map.get("subj_id").toString();
			if ("0".equals(subjId))
				cThemeMap.put(subjId, 0);
			else if (cThemeMap.containsKey(subjId))
				cThemeMap.put(subjId, 2);
			else
				cThemeMap.put(subjId, 1);
		}
		Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
		for (String key : cThemeMap.keySet()) {
			Integer value = cThemeMap.get(key);
			if (value > 1)
				isThemeMap.put(key, true);
			else
				isThemeMap.put(key, false);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String type = "1";
		String subPic = "";
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			String subjId = map.get("subj_id").toString();
			if (i == 0) {
				subjectMap.put("subjectName", map.get("subj_name"));
			}
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			if (isThemeMap.get(subjId)) {
				if (!countMap.containsKey(subjId)) {
					appMap.put("id", Integer.parseInt(subjId));
					appMap.put("pic", ParaUtils.checkPicUri(map.get("subj_pic")));
					appMap.put("type", 2);
					list.add(appMap);
					countMap.put(subjId, true);
				}
				continue;
			}
			appMap.put("id", Integer.parseInt(map.get("id").toString()));

			subPic = ParaUtils.checkPicUri(map.get("icon"));
			if (null != map.get("subj_pic") && !"".equals(map.get("subj_pic").toString()))
				subPic = ParaUtils.checkPicUri(map.get("subj_pic"));
			appMap.put("pic", subPic);
			if (null != map.get("rtype") && !"".equals(map.get("rtype").toString()))
				type = map.get("rtype").toString();
			appMap.put("type", type);

			appMap.put("categoryId", map.get("category_id"));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("package", map.get("pakeage"));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", map.get("cat2_name"));
			if (map.get("uptime") != null) {
				infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
				appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			}

			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			list.add(appMap);
		}
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> adAppList(Map<String, String> para) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t7.name as cat2_name,t6.* from t_app_theme_bag t2  ");
		sql.append(" INNER JOIN t_app_theme_bag_list t3 on t2.id = t3.theme_bag_id ");
		sql.append(" INNER JOIN t_app t6 on t3.sub_id=t6.id ");
		sql.append(" INNER JOIN t_app_category t7 on t6.category_id=t7.id ");
		sql.append(" and t2.id=?");
		sql.append(" order by t3.sort");

		MyUtil mu = MyUtil.getInstance();
		int stype = 45;

		List<Object> plist = new ArrayList<Object>();

		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);

		plist.add(stype);

		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) from (" + sql + ") aa", plist.toArray());
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> categoryMap = new HashMap<String, String>();
		if (rlist.size() > 0) {
			categoryMap = getCategory();
		}
		String type = "1";
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			if (null != map.get("rtype") && !"".equals(map.get("rtype").toString()))
				type = map.get("rtype").toString();
			appMap.put("type", Integer.valueOf(type));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", categoryMap.get(map.get("category_id").toString()));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}

		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	@Override
	public Map<String, Object> preDownAppList(Map<String, String> para) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t7.name AS cat2_name, t6.* FROM t_app_theme_bag t2");
		sql.append(" INNER JOIN t_app_theme_bag_list t3 ON t2.id = t3.theme_bag_id");
		sql.append(" INNER JOIN t_app t6 ON t3.sub_id = t6.id");
		sql.append(" INNER JOIN t_app_category t7 ON t6.category_id = t7.id");
		sql.append(" AND t2.name = ? and t2.bag_type = 1");
		sql.append(" ORDER BY t3.sort");

		MyUtil mu = MyUtil.getInstance();
		String bagName = "预先下载";

		List<Object> plist = new ArrayList<Object>();

		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);

		plist.add(bagName);

		int totalSize = this.getAppstoreDao().findIntBySql("SELECT COUNT(1) FROM (" + sql + ") aa", plist.toArray());
		PageBar pb = new PageBar(para);
		if (mu.isNotBlank(para.get("pageNo")) && NumberUtils.isDigits(para.get("pageNo")))
			pb.setCurrentPageNum(para.get("pageNo"));
		if (mu.isNotBlank(para.get("pageSize")) && NumberUtils.isDigits(para.get("pageSize")))
			pb.setEveryPageNum(para.get("pageSize"));
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(),
				pb);
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> categoryMap = new HashMap<String, String>();
		if (rlist.size() > 0) {
			categoryMap = getCategory();
		}
		String type = "1";
		for (int i = 0; i < rlist.size(); i++) {
			Map<String, Object> map = rlist.get(i);
			Map<String, Object> appMap = new LinkedHashMap<String, Object>();
			appMap.put("id", Integer.parseInt(map.get("id").toString()));
			appMap.put("categoryId", map.get("category_id"));
			if (null != map.get("rtype") && !"".equals(map.get("rtype").toString()))
				type = map.get("rtype").toString();
			appMap.put("type", Integer.valueOf(type));
			appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "",
					map.get("id").toString()));
			appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
			appMap.put("name", map.get("name"));
			appMap.put("summary", map.get("app_desc"));
			// appMap.put("summary", map.get("single_word"));
			appMap.put("support", map.get("support"));
			appMap.put("newFuture", map.get("up_desc"));
			appMap.put("package", map.get("pakeage"));
			appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
			appMap.put("preDown", 1);
			appMap.put("shareContent", map.get("share_content"));
			appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
			appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
			appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
			appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
			appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
			Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("company", map.get("develope"));
			infoMap.put("classify", categoryMap.get(map.get("category_id").toString()));
			infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
			infoMap.put("version", map.get("version"));
			infoMap.put("size", df.format(map.get("app_size")));
			infoMap.put("ageLimit", map.get("age_limit"));
			infoMap.put("compaticity", map.get("os_version_min"));
			appMap.put("infomation", infoMap);
			appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
			list.add(appMap);
		}

		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		subjectMap.put("list", list);
		subjectMap.put("size", list.size());
		subjectMap.put("totalSize", totalSize);
		rmap.put("data", subjectMap);
		return rmap;
	}

	public Map<String, Object> jmSwitch(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("switch", Integer.valueOf(EnvConfigurer.getEvn("jmSwitch")));
		return rmap;
	}

	@Override
	public Map<String, Object> checkADSDk(Map<String, String> para) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		map.put("result", 1);
		String batchId = para.get("batchId");
		String mac = para.get("mac");
		Map<String, Object> batchInfo = this.getAppstoreDao()
				.findMapBySql("SELECT * from t_device_batch WHERE batch_id=?", new Object[] { batchId });
		Map<String, Object> sdkInfo = this.getAppstoreDao().findMapBySql("SELECT * from t_app_sdk WHERE id=?",
				new Object[] { batchInfo.get("sdk_id") });
		if (batchInfo.get("sdk_switch").equals("1")) {
			Map<String, Object> macInfo = this.getAppstoreDao().findMapBySql(
					"SELECT * from t_device_mac_info WHERE mac=? AND batch_id=?", new Object[] { mac, batchId });
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(macInfo.get("ctime").toString()));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(sdf.format(new Date())));
			long time2 = cal.getTimeInMillis();
			Integer val = Integer.parseInt(String.valueOf((time2 - time1) / (1000 * 3600 * 24)));
			if (val >= Integer.parseInt(String.valueOf(sdkInfo.get("activeTime").toString()))) {
				map1.put("Switch", 1);
			} else {
				map1.put("Switch", 0);
			}
		}
		map.put("data", map1);
		return map;
	}
}
