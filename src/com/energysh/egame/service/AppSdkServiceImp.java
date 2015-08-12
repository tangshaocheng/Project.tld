package com.energysh.egame.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.energysh.egame.po.appstore.TAppSdk;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class AppSdkServiceImp extends BaseService implements AppSdkService {

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		String name = para.get("name");
		int count = this.getAppstoreDao().findIntBySql(
				"SELECT * from t_app_sdk where sdk_name=?",
				new Object[] { name });
		if (count > 0) {
			rmap.put("info", "exits");
			return rmap;
		}
		TAppSdk po = new TAppSdk();
		MyUtil mu = MyUtil.getInstance();
		mu.map2Object(para, po);
		po.setSdkName(para.get("name").toString());
		po.setOnOrOff(para.get("bagType").toString());
		po.setCountry(para.get("country").toString());
		po.setActiveTime(para.get("activeTime").toString());
		po.setCreateTime(new Date());
		this.getAppstoreDao().save(po);
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public Map<String, String> del(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		String id = para.get("id");
		if (mu.isNotBlank(id)) {
			this.getAppstoreDao().excuteBySql(
					"DELETE FROM t_app_sdk WHERE id = ?",
					new Object[] { Integer.parseInt(id) });
			this.getAppstoreDao()
					.excuteBySql(
							"UPDATE t_device_batch SET sdk_switch=0,sdk_id=NULL WHERE sdk_id = ?",
							new Object[] { Integer.parseInt(id) });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		int id = Integer.parseInt(para.get("id"));
		String name = para.get("name");
		String type = para.get("bagType");
		String country = para.get("country");
		String activeTime = para.get("activeTime");
		this.getAppstoreDao()
				.excuteBySql(
						"UPDATE t_app_sdk SET sdk_name=?,country=?,activeTime=?,OnOrOff=? WHERE id=?",
						new Object[] { name, country, activeTime, type, id });
		this.getAppstoreDao()
				.excuteBySql(
						"UPDATE t_device_batch SET sdk_id=NULL,sdk_switch='0' WHERE sdk_id=?",
						new Object[] { id });

		 String[] batchid = para.get("batchId").split("");
		 for (int i = 0; i < batchid.length; i++) {
			 this.getAppstoreDao()
				.excuteBySql(
						"UPDATE t_device_batch SET sdk_id=?,sdk_switch=? WHERE batch_id=?",
						new Object[] { id, type, batchid[i] });
		 }
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM t_app_sdk t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("id"))) {
			sql.append(" AND t1.id = ?");
			plist.add(Integer.valueOf(para.get("id")));
		}
		if (mu.isNotBlank(para.get("name"))) {
			sql.append(" AND t1.sdk_name like ?");
			plist.add("%" + para.get("name") + "%");
		}
		if (mu.isNotBlank(para.get("bagType"))) {
			sql.append(" AND t1.OnOrOff = ?");
			plist.add(Integer.valueOf(para.get("bagType")));
		}
		pb.setTotalNum(this.getAppstoreDao().findIntBySql(
				"SELECT COUNT(t1.id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.* ");
		List<Map<String, Object>> rList = this.getAppstoreDao()
				.findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public TAppSdk get(Map<String, String> para) throws Exception {
		List<Map<String, Object>> rlist = this
				.getAppstoreDao()
				.findListMapBySql(
						"SELECT * from t_app_sdk a LEFT JOIN t_device_batch b ON a.id=b.sdk_id WHERE a.id=?",
						new Object[] { Integer.valueOf(para.get("id")) });
		TAppSdk po = new TAppSdk();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		po.setId(Integer.valueOf(para.get("id")));
		po.setSdkName(rlist.get(0).get("sdk_name").toString());
		po.setActiveTime(rlist.get(0).get("activeTime").toString());
		po.setOnOrOff(rlist.get(0).get("OnOrOff").toString());
		po.setCountry(rlist.get(0).get("country").toString());
		for (int i = 0; i < rlist.size(); i++) {
			list.add(rlist.get(i));
		}
		po.setList(list);
		return po;
	}
}
