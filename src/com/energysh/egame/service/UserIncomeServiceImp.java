package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.util.MyUtil;

public class UserIncomeServiceImp extends BaseService implements UserIncomeService {

	@Override
	public List<Map<String, Object>> queryList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Object> plist = new ArrayList<Object>();
		if (mu.isBlank(para.get("yestDate"))) {
			String yestDate = mu.formateDate(mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH), "yyyy-MM-dd");
			para.put("yestDate", yestDate);
		}
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT s1.*, IFNULL(s2.income, 0) last_income FROM (");
		sql.append("SELECT CONCAT(u1.cdate, '_', u1.id) id, u1.cdate, u1.user_id, u1.user_type, u1.batch_id, SUM(u1.active_num) active_num, SUM(u1.down_num) down_num, SUM(u1.cpa_income) cpa_income, SUM(u1.cps_income) cps_income, u1.pub_status, u1.nickname FROM ");
		sql.append("(");
		sql.append("SELECT CAST(CONCAT(t1.user_id, '_', t1.batch_id) AS CHAR) id, DATE_FORMAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY),'%Y-%m-%d') cdate, t1.user_id, t1.user_type, t1.batch_id, t1.active_num, 0 down_num, t1.cpa_income, 0 cps_income, t1.pub_status, t2.nickname ");
		sql.append("FROM t_cpa_income t1 LEFT JOIN sso.permit_user t2 ON t1.user_id = t2.user_id ");
		sql.append("WHERE t1.pub_status = 1 AND t1.user_type = 4");
		if (mu.isNotBlank(para.get("yestDate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("yestDate"));
		}
		sql.append(" UNION ALL ");
		sql.append("SELECT CAST(CONCAT(t1.user_id, '_', t1.batch_id) AS CHAR) id, DATE_FORMAT(DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY),'%Y-%m-%d') cdate, t1.user_id, t1.user_type, t1.batch_id, 0 active_num, t1.down_num, 0 cpa_income, t1.cps_income, t1.pub_status, t2.nickname ");
		sql.append("FROM t_cps_income t1 LEFT JOIN sso.permit_user t2 ON t1.user_id = t2.user_id ");
		sql.append("WHERE t1.pub_status = 1 AND t1.user_type = 4");
		if (mu.isNotBlank(para.get("yestDate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("yestDate"));
		}
		sql.append(") u1 GROUP BY u1.id");
		sql.append(") s1 LEFT JOIN (SELECT t1.* FROM t_user_income t1 INNER JOIN (SELECT t1.user_id, t1.batch_id, MAX(t1.cdate) cdate FROM t_user_income t1 WHERE t1.pub_status = 1 AND t1.user_type = 4 AND t1.cdate < DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY) GROUP BY t1.user_id, t1.batch_id) t2 ON t1.user_id = t2.user_id AND t1.batch_id = t2.batch_id AND t1.cdate = t2.cdate) s2 ON s1.user_id = s2.user_id AND s1.batch_id = s2.batch_id");
		sql.append(" WHERE NOT EXISTS(SELECT t1.id num FROM t_user_income t1 WHERE t1.pub_status = 1 AND t1.user_type = 4 AND t1.cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY))");
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		return rlist;
	}

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		if (mu.isBlank(para.get("id"))) {
			rmap.put("info", "fail");
			return rmap;
		}
		int num = this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) num FROM t_user_income t1 WHERE t1.pub_status = 1 AND t1.user_type = 4 AND t1.cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY)", null);
		if (num > 0) {
			rmap.put("info", "isPub");
			return rmap;
		}
		List<Map<String, Object>> rlist = this.queryList(para);
		char a = 6;
		boolean isExist = false;
		String[] ids = mu.split(para.get("id"), a);
		String[] cpa_incomes = mu.split(para.get("cpa_income"), a);
		String[] cps_incomes = mu.split(para.get("cps_income"), a);
		rlist: for (Map<String, Object> map : rlist) {
			for (int i = 0; i < ids.length; i++) {
				if (mu.equals(ids[i], map.get("id") + "")) {
					double cpa_income1 = mu.toDouble(cpa_incomes[i]);
					double cpa_income2 = mu.toDouble(map.get("cpa_income") + "");
					double cps_income1 = mu.toDouble(cps_incomes[i]);
					double cps_income2 = mu.toDouble(map.get("cps_income") + "");
					if (cpa_income1 != cpa_income2 || cps_income1 != cps_income2) {
						isExist = true;
						break rlist;
					}
				}
			}
		}
		if (isExist) {
			rmap.put("info", "exist");
			return rmap;
		}
		String[] user_ids = mu.split(para.get("user_id"), a);
		String[] batch_ids = mu.split(para.get("batch_id"), a);
		String[] pub_incomes = mu.split(para.get("pub_income"), a);
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			String cdate = para.get("yestDate");
			int userId = mu.toInt(user_ids[i]);
			int userType = 4;
			String batchId = batch_ids[i];
			double income = mu.toDouble(pub_incomes[i]);
			int pubstatus = 1;
			this.getAppstoreDao().excuteBySql("INSERT INTO t_user_income(id, cdate, user_id, user_type, batch_id, income, pub_status) VALUES(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE income = income + ?, pub_status = 1", new Object[] { id, cdate, userId, userType, batchId, income, pubstatus, income });
			this.getAppstoreDao().excuteBySql("UPDATE t_cpa_income t1 SET t1.pub_status = -1 WHERE t1.user_type = 4 AND t1.pub_status = 1 AND user_id = ? AND batch_id = ?", new Object[] { userId, batchId });
			this.getAppstoreDao().excuteBySql("UPDATE t_cps_income t1 SET t1.pub_status = -1 WHERE t1.user_type = 4 AND t1.pub_status = 1 AND user_id = ? AND batch_id = ?", new Object[] { userId, batchId });
		}
		rmap.put("info", "ok");
		return rmap;
	}

	private CpsIncomeService cpsIncomeService;

	public CpsIncomeService getCpsIncomeService() {
		return cpsIncomeService;
	}

	public void setCpsIncomeService(CpsIncomeService cpsIncomeService) {
		this.cpsIncomeService = cpsIncomeService;
	}

}
