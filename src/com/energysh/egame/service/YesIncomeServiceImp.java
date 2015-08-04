package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class YesIncomeServiceImp extends BaseService implements YesIncomeService {

	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		if (mu.isBlank(para.get("bdate")) && mu.isBlank(para.get("edate"))) {
			String yTime = mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH);
			String yDate = mu.formateDate(yTime, "yyyy-MM-dd");
			para.put("bdate", yDate);
			para.put("edate", yDate);
		} else if (mu.isBlank(para.get("bdate")) && mu.isNotBlank(para.get("edate"))) {
			para.put("bdate", para.get("edate"));
		} else if (mu.isNotBlank(para.get("bdate")) && mu.isBlank(para.get("edate"))) {
			String yTime = mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH);
			String yDate = mu.formateDate(yTime, "yyyy-MM-dd");
			para.put("edate", yDate);
		}
		StringBuilder sql = new StringBuilder("FROM appstore.t_user_income t1 LEFT JOIN permit_user t2 ON t1.user_id = t2.user_id WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("userId"))) {
			sql.append(" AND t1.user_id = ?");
			plist.add(para.get("userId"));
		}
		if (mu.isNotBlank(para.get("bdate"))) {
			sql.append(" AND t1.cdate >= ?");
			plist.add(para.get("bdate"));
		}
		if (mu.isNotBlank(para.get("edate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("edate"));
		}
		if (mu.isNotBlank(para.get("batchId"))) {
			sql.append(" AND t1.batch_id LIKE ?");
			plist.add("%" + para.get("batchId") + "%");
		}
		pb.setTotalNum(this.getSsoDao().findIntBySql("SELECT COUNT(t1.id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.id, DATE_FORMAT(t1.cdate,'%Y-%m-%d') cdate, t1.user_id, t1.batch_id, t1.income/100 income, t2.nickname ");
		List<Map<String, Object>> rList = this.getSsoDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		if (mu.isBlank(para.get("rincome"))) {
			rmap.put("info", "fail");
			return rmap;
		}
		Float total = this.getAppstoreDao().findFloatBySql("SELECT SUM(income) FROM t_user_income t1 WHERE cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY)", null);
		if (total > 0) {
			rmap.put("info", "exist");
			return rmap;
		}
		Double rincome = mu.toDouble(para.get("rincome"));
		Float downTotal = this.getAppstoreDao().findFloatBySql("SELECT SUM(t1.down_sum) FROM (SELECT t1.* FROM t_user_income t1 WHERE cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY) GROUP BY t1.batch_id) t1", null);
		List<Map<String, Object>> incomes = this.getAppstoreDao().findListMapBySql("SELECT t1.*, REPLACE(t2.divide_rate, '%', '')/100 divide_rate FROM t_user_income t1 LEFT JOIN t_sale_batch_relation t2 ON t1.batch_id = t2.batch_id AND t1.user_id = t2.user_id WHERE cdate = DATE_ADD(CURRENT_DATE, INTERVAL -1 DAY) ORDER BY t1.batch_id, t1.user_type", null);
		Map<String, List<Map<String, Object>>> gmap = new HashMap<String, List<Map<String, Object>>>();
		for (Map<String, Object> map : incomes) {
			if (gmap.containsKey(map.get("batch_id") + ""))
				continue;
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> temp : incomes) {
				if (mu.equals(map.get("batch_id") + "", temp.get("batch_id") + ""))
					list.add(temp);
			}
			gmap.put(map.get("batch_id") + "", list);
		}
		for (List<Map<String, Object>> list : gmap.values()) {
			double saleTotal = 0;
			Map<String, Object> partners = new HashMap<String, Object>();
			for (Map<String, Object> income : list) {
				if (mu.equals("4", income.get("user_type") + "")) {
					partners = income;
					continue;
				}
				double newIncome = rincome * (mu.toDouble(income.get("down_sum") + "") / downTotal) * mu.toDouble(income.get("divide_rate") + "");
				this.getAppstoreDao().excuteBySql("update t_user_income set income = " + newIncome + " WHERE id = '" + income.get("id") + "'");
				saleTotal += newIncome;
			}
			if (partners.size() > 0) {
				double newIncome = rincome * (mu.toDouble(partners.get("down_sum") + "") / downTotal) - saleTotal;
				this.getAppstoreDao().excuteBySql("update t_user_income set income = " + newIncome + " WHERE id = '" + partners.get("id") + "'");
			}
		}
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public List<Map<String, Object>> queryList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		if (mu.isBlank(para.get("bdate")) && mu.isBlank(para.get("edate"))) {
			String yTime = mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH);
			String yDate = mu.formateDate(yTime, "yyyy-MM-dd");
			para.put("bdate", yDate);
			para.put("edate", yDate);
		} else if (mu.isBlank(para.get("bdate")) && mu.isNotBlank(para.get("edate"))) {
			para.put("bdate", para.get("edate"));
		} else if (mu.isNotBlank(para.get("bdate")) && mu.isBlank(para.get("edate"))) {
			String yTime = mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH);
			String yDate = mu.formateDate(yTime, "yyyy-MM-dd");
			para.put("edate", yDate);
		}
		StringBuilder sql = new StringBuilder("SELECT t1.id, DATE_FORMAT(t1.cdate,'%Y-%m-%d') cdate, t1.user_id, t1.batch_id, t1.income, t2.nickname FROM appstore.t_user_income t1 LEFT JOIN permit_user t2 ON t1.user_id = t2.user_id WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("bdate"))) {
			sql.append(" AND t1.cdate >= ?");
			plist.add(para.get("bdate"));
		}
		if (mu.isNotBlank(para.get("edate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("edate"));
		}
		if (mu.isNotBlank(para.get("batchId"))) {
			sql.append(" AND t1.batch_id LIKE ?");
			plist.add("%" + para.get("batchId") + "%");
		}
		List<Map<String, Object>> rlist = this.getSsoDao().findListMapBySql(sql.toString(), plist.toArray());
		return rlist;
	}

}
