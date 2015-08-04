package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class SalesServiceImp extends BaseService implements SalesService {

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM appstore.t_sale_batch_relation t1 LEFT JOIN permit_user t2 ON t1.user_id = t2.user_id WHERE t1.del = 0");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("nickname"))) {
			sql.append(" AND t2.nickname LIKE ?");
			plist.add("%" + para.get("nickname") + "%");
		}
		if (mu.isNotBlank(para.get("batchId"))) {
			sql.append(" AND t1.batch_id LIKE ?");
			plist.add("%" + para.get("batchId") + "%");
		}
		pb.setTotalNum(this.getSsoDao().findIntBySql("SELECT COUNT(t1.id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.*, t2.nickname ");
		List<Map<String, Object>> rList = this.getSsoDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		String nickname = para.get("nickname");
		String batchId = para.get("batchId");
		String divideRate = para.get("divideRate");
		char a = 6;
		MyUtil mu = MyUtil.getInstance();
		String[] nicknames = StringUtils.split(nickname, a);
		String[] batchIds = StringUtils.split(batchId, a);
		String[] divideRates = StringUtils.split(divideRate, a);
		String batchs = "";
		for (int i = 0; i < nicknames.length; i++) {
			String sBatchId = this.getSsoDao().findStringBySql("SELECT t1.batch_id FROM appstore.t_sale_batch_relation t1, permit_user t2 WHERE t1.del = 0 AND t1.user_id = t2.user_id AND t2.user_type = 3 AND t2.nickname = ? AND t1.batch_id = ?", new Object[] { nicknames[i], batchIds[i] });
			if (mu.isNotBlank(sBatchId)) {
				batchs += "[name = " + nicknames[i] + ", batchId = " + batchIds[i] + "]";
				continue;
			}
			int pUserId = this.getSsoDao().findIntBySql("SELECT t1.user_id FROM permit_user t1 WHERE t1.nickname = '" + nicknames[i] + "' AND t1.user_type = 3", null);
			if (pUserId == 0) {
				this.getSsoDao().excuteBySql("INSERT INTO permit_user(lname, lps, nickname, user_type, ctime) VALUES('', '', '" + nicknames[i] + "', 3, NOW())");
				pUserId = this.getSsoDao().findIntBySql("SELECT t1.user_id FROM permit_user t1 WHERE t1.nickname = '" + nicknames[i] + "' AND t1.user_type = 3", null);
			}
			Object num = this.getAppstoreDao().excuteBySql("INSERT INTO t_sale_batch_relation(user_id, batch_id, divide_rate, ctime) VALUES(" + pUserId + ", '" + batchIds[i] + "', '" + divideRates[i] + "', NOW())");
			if (mu.toInt(((Integer) num).toString()) == 0) {
				batchs += "[name = " + nicknames[i] + ", batchId = " + batchIds[i] + "]";
			}
		}
		rmap.put("batchs", batchs);
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public Map<String, String> del(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		String id = para.get("id");
		if (mu.isNotBlank(id)) {
			this.getAppstoreDao().excuteBySql("UPDATE t_sale_batch_relation SET del = 1 WHERE id = ?", new Object[] { Integer.parseInt(id) });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

}
