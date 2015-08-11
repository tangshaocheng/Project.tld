package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.po.appstore.TAppThemeBag;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class AppSdkServiceImp extends BaseService implements AppSdkService {

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> del(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		String id = para.get("id");
		if (mu.isNotBlank(id)) {
			this.getAppstoreDao().excuteBySql("DELETE FROM t_app_sdk WHERE id = ?", new Object[] { Integer.parseInt(id) });
			this.getAppstoreDao().excuteBySql("UPDATE t_device_batch SET sdk_switch=0,sdk_id=NULL WHERE sdk_id = ?", new Object[] { Integer.parseInt(id) });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	public TAppThemeBag get(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TAppThemeBag getCategoryGood(Map<String, String> para)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> sort(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> upCategoryGood(Map<String, String> para)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
