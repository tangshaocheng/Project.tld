package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.util.MyUtil;

public class CpsIncomeServiceImp extends BaseService implements CpsIncomeService {

	@Override
	public List<Map<String, Object>> queryList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("SELECT t1.*, t2.total_num, IFNULL(t3.cdate,'') last_date FROM (SELECT t1.id, t1.cdate, t1.language, t1.app_id, t1.user_id, t1.batch_id, t1.cps_income, SUM(t1.down_num) down_num, t1.pub_status, t1.nickname, t1.cps_divide_ratio FROM (SELECT CAST(CONCAT(t1.language, '_', t1.app_id, '_', t1.user_id, '_', t1.batch_id) AS CHAR) id, DATE_FORMAT(t1.cdate,'%Y-%m-%d') cdate, t1.language, t1.app_id, t1.user_id, t1.batch_id, t1.cps_income, t1.down_num, t1.pub_status, t2.nickname, t3.cps_divide_ratio FROM t_cps_income t1 LEFT JOIN sso.permit_user t2 ON t1.user_id = t2.user_id LEFT JOIN t_partners_income_conf t3 ON t1.user_id = t3.user_id WHERE t1.user_type = 4");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isBlank(para.get("pubStatus"))) {
			String pubStatus = "0";
			para.put("pubStatus", pubStatus);
		}
		if (mu.isBlank(para.get("yestDate"))) {
			String yestDate = mu.formateDate(mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH), "yyyy-MM-dd");
			para.put("yestDate", yestDate);
		}
		if (mu.isNotBlank(para.get("pubStatus"))) {
			sql.append(" AND t1.pub_status = ?");
			plist.add(para.get("pubStatus"));
		}
		if (mu.isNotBlank(para.get("yestDate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("yestDate"));
		}
		if (mu.isNotBlank(para.get("appId"))) {
			sql.append(" AND t1.app_id = ?");
			plist.add(para.get("appId"));
		}
		sql.append(") t1 GROUP BY t1.id) t1 LEFT JOIN (SELECT t1.app_id, t1.language, SUM(t1.down_num) total_num FROM t_cps_income t1 WHERE t1.user_type = 4");
		if (mu.isNotBlank(para.get("pubStatus"))) {
			sql.append(" AND t1.pub_status = ?");
			plist.add(para.get("pubStatus"));
		}
		if (mu.isNotBlank(para.get("yestDate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("yestDate"));
		}
		sql.append(" GROUP BY t1.app_id, t1.language) t2 ON t1.app_id = t2.app_id AND t1.language = t2.language");
		sql.append(" LEFT JOIN (SELECT t1.app_id, t1.language, CAST(MAX(t1.cdate) AS CHAR) cdate FROM t_cps_income t1 WHERE t1.pub_status = -1 AND t1.user_type = 4 GROUP BY t1.app_id, t1.language) t3 ON t1.app_id = t3.app_id AND t1.language = t3.language");
		sql.append(" WHERE NOT EXISTS(SELECT t1.id FROM t_user_income t1 WHERE t1.pub_status = 1 AND t1.user_type = 4 AND t1.cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY))");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		return rlist;
	}

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		if (mu.isBlank(para.get("rincome"))) {
			rmap.put("info", "fail");
			return rmap;
		}
		int num = this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) num FROM t_user_income t1 WHERE t1.pub_status = 1 AND t1.user_type = 4 AND t1.cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY)", null);
		if (num > 0) {
			rmap.put("info", "isPub");
			return rmap;
		}
		para.put("pubStatus", "1");
		List<Map<String, Object>> rlist = this.queryList(para);
		char a = 6;
		boolean isExist = false;
		String[] ids = mu.split(para.get("id"), a);
		rlist: for (Map<String, Object> map : rlist) {
			for (int i = 0; i < ids.length; i++) {
				if (mu.equals(ids[i], map.get("id") + "")) {
					isExist = true;
					break rlist;
				}
			}
		}
		if (isExist) {
			rmap.put("info", "exist");
			return rmap;
		}
		String language = para.get("language");
		String appId = para.get("appId");
		String rincomes = para.get("rincome");
		String[] language_ids = mu.split(para.get("language_id"), a);
		String[] app_ids = mu.split(para.get("app_id"), a);
		String[] user_ids = mu.split(para.get("user_id"), a);
		String[] batch_ids = mu.split(para.get("batch_id"), a);
		String[] total_nums = mu.split(para.get("total_num"), a);
		String[] cps_divide_ratios = mu.split(para.get("cps_divide_ratio"), a);
		for (int i = 0; i < app_ids.length; i++) {
			String language_id = language_ids[i];
			String app_id = app_ids[i];
			if (!mu.equals(language, language_id) || !mu.equals(appId, app_id))
				continue;
			String user_id = user_ids[i];
			String batch_id = batch_ids[i];
			double rincome = mu.toDouble(rincomes);
			rlist = this.getAppstoreDao().findListMapBySql("SELECT t1.id, t1.down_num FROM t_cps_income t1 WHERE t1.pub_status = 0 AND t1.user_type = 4 AND t1.language = ? AND t1.app_id = ? AND t1.user_id = ? AND t1.batch_id = ?", new Object[] { language_id, app_id, user_id, batch_id });
			for (Map<String, Object> map : rlist) {
				String id = map.get("id").toString();
				double down_num = mu.toDouble(map.get("down_num").toString());
				double total_num = mu.toDouble(total_nums[i]);
				double cps_divide_ratio = mu.toDouble(cps_divide_ratios[i]);
				int cps_income = (int) (rincome * down_num / total_num * cps_divide_ratio / 100D);
				this.getAppstoreDao().excuteBySql("UPDATE t_cps_income t1 SET t1.cps_income = ?, t1.pub_status = 1 WHERE t1.user_type = 4 AND t1.id = ?", new Object[] { cps_income, id });
			}
		}
		rmap.put("info", "ok");
		return rmap;
	}
}
