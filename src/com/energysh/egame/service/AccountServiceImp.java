package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

@SuppressWarnings("unchecked")
public class AccountServiceImp extends BaseService implements AccountService {

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM (SELECT t1.*, GROUP_CONCAT(t2.batch_id) batch_id FROM permit_user t1 LEFT JOIN appstore.t_sale_batch_relation t2 ON t1.user_id = t2.user_id WHERE t1.user_type = 3 AND LENGTH(t1.lname) > 0 GROUP BY t1.user_id UNION ALL SELECT t1.*, t2.batch_id FROM permit_user t1 LEFT JOIN appstore.t_device_batch t2 ON t1.nickname = t2.name WHERE t1.user_type = 4 AND LENGTH(t1.lname) > 0 GROUP BY t1.user_id) t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("lname"))) {
			sql.append(" AND t1.lname LIKE ?");
			plist.add("%" + para.get("lname") + "%");
		}
		if (mu.isNotBlank(para.get("nickname"))) {
			sql.append(" AND t1.nickname LIKE ?");
			plist.add("%" + para.get("nickname") + "%");
		}
		if (mu.isNotBlank(para.get("batchId"))) {
			sql.append(" AND t1.batch_id LIKE ?");
			plist.add("%" + para.get("batchId") + "%");
		}
		pb.setTotalNum(this.getSsoDao().findIntBySql("SELECT COUNT(t1.user_id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.* ");
		List<Map<String, Object>> rList = this.getSsoDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		String lname = para.get("lname");
		String lps = para.get("lps");
		String nickname = para.get("userId");
		String userType = para.get("userType");
		String userId = para.get("userId");
		int count = this.getSsoDao().findIntBySql("SELECT COUNT(t1.user_id) FROM permit_user t1 WHERE t1.lname = ?", new Object[] { lname });
		if (count > 0) {
			rmap.put("info", "exist");
			return rmap;
		}
		if (mu.equals(userType, "3"))
			count = this.getSsoDao().findIntBySql("SELECT COUNT(t1.user_id) FROM permit_user t1 LEFT JOIN appstore.t_sale_batch_relation t2 ON t1.user_id = t2.user_id WHERE LENGTH(lname) > 0 AND t1.user_id = ?", new Object[] { mu.toInt(userId) });
		else if (mu.equals(userType, "4"))
			count = this.getSsoDao().findIntBySql("SELECT COUNT(t1.user_id) FROM permit_user t1 LEFT JOIN appstore.t_device_batch t2 ON t1.nickname = t2.name WHERE t1.user_type = 4 AND t1.lname = ?", new Object[] { lname });
		if (count > 0) {
			rmap.put("info", "exist");
			return rmap;
		}
		if (mu.equals(userType, "3"))
			this.getSsoDao().excuteBySql("UPDATE permit_user SET lname = '" + lname + "', lps = MD5('" + lps + "') WHERE user_id = " + mu.toInt(userId));
		else if (mu.equals(userType, "4"))
			this.getSsoDao().excuteBySql("INSERT INTO permit_user(lname, lps, nickname, user_type, ctime) VALUES('" + lname + "', MD5('" + lps + "'), '" + nickname + "', 4, NOW())");
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public Map<String, Object> get(Map<String, String> para) throws Exception {
		Map<String, Object> rmap = new HashMap<String, Object>();
		MyUtil mu = MyUtil.getInstance();
		String userId = para.get("userId");
		String userType = para.get("userType");
		if (mu.equals(userType, "3"))
			rmap = this.getSsoDao().findMapBySql("SELECT t1.*, t2.batch_id FROM permit_user t1 LEFT JOIN appstore.t_sale_batch_relation t2 ON t1.user_id = t2.user_id WHERE t1.user_id = ?", new Object[] { mu.toInt(userId) });
		else if (mu.equals(userType, "4"))
			rmap = this.getSsoDao().findMapBySql("SELECT t1.*, t2.batch_id FROM permit_user t1 LEFT JOIN appstore.t_device_batch t2 ON t1.nickname = t2.name WHERE t1.user_id = ?", new Object[] { mu.toInt(userId) });
		return rmap;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		String lps = para.get("lps");
		String userId = para.get("userId");
		this.getSsoDao().excuteBySql("UPDATE permit_user SET lps = MD5('" + lps + "') WHERE user_id = " + mu.toInt(userId));
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public List<Map<String, Object>> queryBatch(Map<String, String> para) throws Exception {
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		MyUtil mu = MyUtil.getInstance();
		String userType = para.get("userType");
		if (mu.equals(userType, "3"))
			rlist = this.getSsoDao().findListMapBySql("SELECT t1.* FROM permit_user t1 WHERE t1.user_type = 3 AND LENGTH(lname) = 0", null);
		else if (mu.equals(userType, "4"))
			rlist = this.getSsoDao().findListMapBySql("SELECT t1.* FROM appstore.t_device_batch t1 WHERE t1.name NOT IN (SELECT lname FROM permit_user WHERE user_type = 4 AND LENGTH(lname) > 0) group by t1.name", null);
		return rlist;
	}

	@Override
	public List<String> getList(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("SELECT nickname FROM permit_user t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("nickname"))) {
			sql.append(" AND t1.nickname LIKE ?");
			plist.add("%" + para.get("nickname") + "%");
		}
		if (mu.isNotBlank(para.get("userType"))) {
			sql.append(" AND t1.user_type = ?");
			plist.add(mu.toInt(para.get("userType")));
		}
		return this.getSsoDao().findListBySql(sql.toString(), plist.toArray());
	}

}
