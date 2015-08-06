package com.energysh.egame.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.energysh.egame.po.appstore.TApp;
import com.energysh.egame.po.appstore.TSearchHot;
import com.energysh.egame.util.HttpUtils;
import com.energysh.egame.util.MyConfigurer;
import com.energysh.egame.util.Utils;
import common.Logger;

@SuppressWarnings("unchecked")
public class TaskServiceImp extends BaseService implements TaskService {

	private static Logger logger = Logger.getLogger(TaskServiceImp.class);

	public boolean isMaster() {
		if ("Y".equalsIgnoreCase(MyConfigurer.getEvn("is_master")))
			return true;
		return false;
	}

	public void getQihooApiData() {
		if (!isMaster())
			return;
	}

	public void doIncome() {
		if (!isMaster())
			return;
		System.out.println("=======================>start doIncome<=======================");
		this.getAppstoreDao().excuteBySql("UPDATE t_partners_income_conf SET cpa_price = cpa_price_nextday, cps_divide_ratio = cps_divide_ratio_nextday");

		this.getSsoDao().excuteBySql("INSERT IGNORE INTO appstore.t_cpa_income(id, cdate, app_id, user_id, user_type, batch_id, active_num, cpa_income, pub_status) SELECT t1.id, t1.cdate, t1.app_id, t1.user_id, 4 user_type, t1.batch_id, count(t1.batch_id) active_num, 0 cpa_income, 0 pub_status FROM (SELECT CONCAT(DATE_FORMAT(t1.ctime,'%Y-%m-%d'), '_', t1.app_id, '_', t3.user_id, '_', t1.batch_id) id, DATE_FORMAT(t1.ctime,'%Y-%m-%d') cdate, t1.app_id, t3.user_id, t1.batch_id FROM (SELECT * FROM appstore.t_device_mac_info WHERE ctime LIKE CONCAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY), '%')) t1 INNER JOIN appstore.t_device_batch t2 ON t1.batch_id = t2.batch_id INNER JOIN (SELECT * FROM sso.permit_user WHERE user_type = 4) t3 ON t2.name = t3.nickname) t1 WHERE t1.id IS NOT NULL GROUP BY t1.id");
		this.getSsoDao().excuteBySql("INSERT IGNORE INTO appstore.t_cps_income(id, cdate, language, app_id, user_id, user_type, batch_id, down_num, cps_income, pub_status) SELECT t1.id, t1.cdate, 0 language, t1.app_id, t1.user_id, 4 user_type, t1.batch_id, count(t1.batch_id) down_num, 0 cps_income, 0 pub_status FROM (SELECT CONCAT(DATE_FORMAT(t1.ctime,'%Y-%m-%d'), '_0_', t1.app_id, '_', t3.user_id, '_', t1.batch_id) id, DATE_FORMAT(t1.ctime,'%Y-%m-%d') cdate, t1.app_id, t3.user_id, 4 user_type, t1.batch_id, 0 cps_income, 0 pub_status FROM (SELECT * FROM appstore.t_down_complete_log WHERE ctime LIKE CONCAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY), '%')) t1 INNER JOIN appstore.t_device_batch t2 ON t1.batch_id = t2.batch_id INNER JOIN (SELECT * FROM sso.permit_user WHERE user_type = 4) t3 ON t2.name = t3.nickname) t1 WHERE t1.id IS NOT NULL GROUP BY t1.id");
		/** 英文版CPS统计 **/
		List<Map<String, Object>> tlist = this.getAppstoreEnDao().findListMapBySql("SELECT t1.* FROM t_down_complete_log t1 INNER JOIN t_app t2 ON t1.app_id = t2.id AND t2.app_source = 0 WHERE t1.ctime LIKE CONCAT(DATE_ADD(CURRENT_DATE, INTERVAL - 1 DAY), '%')", null);
		for (Map<String, Object> map : tlist) {
			this.getAppstoreDao().excuteBySql("INSERT IGNORE INTO t_down_complete_log_en(id, mac, app_id, batch_id, language, app_type, ver, ctime) VALUES(?, ?, ?, ?, ?, ?, ?, ?);", new Object[] { map.get("id"), map.get("mac"), map.get("app_id"), map.get("batch_id"), map.get("language"), map.get("app_type"), map.get("ver"), map.get("ctime") });
		}
		this.getSsoDao().excuteBySql("INSERT IGNORE INTO appstore.t_cps_income(id, cdate, language, app_id, user_id, user_type, batch_id, down_num, cps_income, pub_status) SELECT t1.id, t1.cdate, 1 language, t1.app_id, t1.user_id, 4 user_type, t1.batch_id, count(t1.batch_id) down_num, 0 cps_income, 0 pub_status FROM (SELECT CONCAT(DATE_FORMAT(t1.ctime,'%Y-%m-%d'), '_0_', t1.app_id, '_', t3.user_id, '_', t1.batch_id) id, DATE_FORMAT(t1.ctime,'%Y-%m-%d') cdate, t1.app_id, t3.user_id, 4 user_type, t1.batch_id, 0 cps_income, 0 pub_status FROM (SELECT * FROM appstore.t_down_complete_log_en WHERE ctime LIKE CONCAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY), '%')) t1 INNER JOIN appstore.t_device_batch t2 ON t1.batch_id = t2.batch_id INNER JOIN (SELECT * FROM sso.permit_user WHERE user_type = 4) t3 ON t2.name = t3.nickname) t1 WHERE t1.id IS NOT NULL GROUP BY t1.id");

		this.getAppstoreDao().excuteBySql("UPDATE t_cpa_income t1 LEFT JOIN t_partners_income_conf t2 ON t1.user_id = t2.user_id SET t1.cpa_income = t1.active_num * IFNULL(t2.cpa_price, 0), t1.pub_status = 1");

		// this.getSsoDao().excuteBySql("INSERT IGNORE INTO appstore.t_user_income(id, cdate, user_id, user_type, batch_id, income, down_sum) SELECT t1.* FROM (SELECT CONCAT(DATE_FORMAT(t1.ctime,'%Y-%m-%d'), '_', t2.user_id, '_', t1.batch_id) id, DATE_FORMAT(t1.ctime,'%Y-%m-%d') cdate, t2.user_id, 3 user_type, t1.batch_id, 0 income, count(t1.batch_id) down_sum FROM (SELECT * FROM appstore.t_down_complete_log WHERE ctime LIKE CONCAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY), '%')) t1 INNER JOIN appstore.t_sale_batch_relation t2 ON t2.del = 0 AND t1.batch_id = t2.batch_id GROUP BY t1.batch_id, t2.user_id, date(t1.ctime)) t1");
		// this.getSsoDao().excuteBySql("INSERT IGNORE INTO appstore.t_user_income(id, cdate, user_id, user_type, batch_id, income, down_sum) SELECT t1.* FROM (SELECT CONCAT(DATE_FORMAT(t1.ctime,'%Y-%m-%d'), '_', t3.user_id, '_', t1.batch_id) id, DATE_FORMAT(t1.ctime,'%Y-%m-%d') cdate, t3.user_id, 4 user_type, t1.batch_id, 0 income, count(t1.batch_id) down_sum FROM (SELECT * FROM appstore.t_down_complete_log WHERE ctime LIKE CONCAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY), '%')) t1 INNER JOIN appstore.t_device_batch t2 ON t1.batch_id = t2.batch_id INNER JOIN (SELECT * FROM permit_user WHERE user_type = 4) t3 ON t2.name = t3.nickname GROUP BY t1.batch_id, t3.user_id, date(t1.ctime)) t1");
		System.out.println("=======================>end doIncome<=======================");
	}

	public void uuQihooApp() {
		if (!isMaster())
			return;
		int pageNo = 0;
		int pageSize = 10000;
		Long ss = (Long) this.getAppstoreDao().findObject("select count(id) from TApp a where a.appSource=?", new Object[] { 1 });
		logger.info("奇虎应用个数："+ss);
		int maxSize = ss.intValue();
		int allPage = 0;
		if (maxSize % pageSize == 0)
			allPage = maxSize / pageSize;
		else
			allPage = maxSize / pageSize + 1;

		for (int k = 1; k <= allPage; k++) {
			pageNo = k;

			List<Object> appList = this.getAppstoreDao().findListBySql("select * from t_app a where a.app_source=?", new Object[] { 1 }, pageNo, pageSize, TApp.class);

			for (Object obj : appList) {
				try {
					TApp app = (TApp) obj;
					Map<String, String> map = new HashMap<String, String>();
					map.put("from", MyConfigurer.getEvn("qhCustomId"));// 渠道号（由360分配）

					map.put("appid", app.getId() + ""); // 应用类型（1-软件，2-游戏），默认不区分应用类型

					String sign = Utils.getMD5Str(Utils.sortMap(map) + MyConfigurer.getEvn("qhSecret"));
					map.put("sign", sign);
					String res = "";
					try {
						Thread.sleep(1000);
						res  = HttpUtils.sendGet(MyConfigurer.getEvn("qhMobiApiUrlApp"), map);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					if("".equals(res)) continue;

					JSONObject resObj = JSONObject.fromObject(res);
					JSONArray itemList = JSONArray.fromObject(resObj.get("items"));

					for (Object oo : itemList) {
						JSONObject mm = (JSONObject) oo;
						String pic1 = "", pic2 = "", pic3 = "", pic4 = "", pic5 = "";
						try{
							String[] url = mm.getString("screenshotsUrl").split(",");
							for (int i = 0; i < url.length; i++) {
								switch (i) {
								case 0:
									pic1 = (url[i]);
								case 1:
									pic2 = (url[i]);
								case 2:
									pic3 = (url[i]);
								case 3:
									pic4 = (url[i]);
								case 4:
									pic5 = (url[i]);
								}
							}
						} catch(Exception e) {
							e.printStackTrace();
						}

						app.setVersionCode(Integer.valueOf(mm.getString("versionCode")));
						app.setVersion(mm.getString("versionName"));
						app.setEmbededVersion(mm.getString("versionName"));
						app.setUpDesc(mm.getString("updateInfo"));
						app.setAppDesc(mm.getString("description"));
						app.setAppSize(Double.parseDouble(mm.getString("apkSize")));
						app.setIcon(mm.getString("iconUrl"));
						app.setApp(mm.getString("downloadUrl"));
						app.setEmbededApp(mm.getString("downloadUrl"));
						app.setMainPic(pic1);
						app.setMainPic2(pic2);
						app.setPic1(pic1);
						app.setPic2(pic2);
						app.setPic3(pic3);
						app.setPic4(pic4);
						app.setPic5(pic5);
						//app.setCuptime(new Date());
						app.setUptime(Utils.getAllDatePart(mm.getString("updateTime")));
						app.setAppTag(mm.getString("tag"));
						app.setSingleWord(mm.getString("description"));
						this.getAppstoreDao().update(app);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	@Override
	public void hotSearch() {
		if (!isMaster())
			return;
		logger.info("start hotSearch");
		try {
			String startDate = Utils.getPreviousWeekday();
			String endDate = Utils.getPreviousWeekSunday();
			Date ctime = new Date();
			List<Map<String, Object>> list = this.getAppstoreDao().findListMapBySql("select key_name,count(*) as count from t_search_log where ctime BETWEEN ? and ? and key_name !='' GROUP BY key_name order by count desc LIMIT 10", new Object[] { startDate, endDate });
			// if(list.size() > 0) this.getAppstoreDao().excuteBySql("delete from t_search_hot");
			Collections.reverse(list);
			for (Map<String, Object> mm : list) {
				TSearchHot hot = new TSearchHot();
				hot.setAppType("1");
				hot.setKeyName(mm.get("key_name").toString());
				hot.setCtime(ctime);
				this.getAppstoreDao().save(hot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("end hotSearch");
	}

}
