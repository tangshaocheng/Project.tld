package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.energysh.egame.po.appstore.TAppThemeBagSort;
import com.energysh.egame.po.appstore.TSxAppThemeBagSort;
import com.energysh.egame.util.MyUtil;

public class AppThemeBagSortSxServiceImp extends BaseService implements AppThemeBagSortSxService {

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
			this.getAppstoreDao().excute("DELETE TSxAppThemeBagSort WHERE id = ?", new Object[] { Integer.parseInt(id) });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

	@Override
	public TAppThemeBagSort get(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> query(Map<String, String> para) throws Exception {
		// PageBar pb = new PageBar(para);
		Map<String, Object> map = new HashMap<String, Object>();
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM t_sx_app_theme_bag_sort t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("themeBagid"))) {
			sql.append(" AND t2.id = ?");
			plist.add(Integer.valueOf(para.get("themeBagid")));
		}
		if (mu.isNotBlank(para.get("themeBagname"))) {
			sql.append(" AND t2.name like ?");
			plist.add("%" + para.get("themeBagname") + "%");
		}
		if (mu.isNotBlank(para.get("themeBagType"))) {
			sql.append(" AND t2.bag_type = ?");
			plist.add(Integer.valueOf(para.get("themeBagType")));
		}
		// pb.setTotalNum(this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) " + sql, plist.toArray()));
		// if (pb.getTotalNum() == 0)
		// return pb;
		int size = this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) " + sql, plist.toArray());
		if (0 == size) {
			return map;
		}
		sql.insert(0, "SELECT t1.*, CAST(t1.theme_bag_id AS SIGNED) theme_bag_id, CONCAT(t2.name) theme_bag_name, CAST(t2.bag_type AS SIGNED) theme_bag_type ");
		sql.append(" ORDER BY t1.sort, t1.id");
		List<Map<String, Object>> rList = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
		// pb.setResultList(rList);
		map.put("resultList", rList);
		return map;
	}

	@Override
	public Map<String, String> sort(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		char a = 6;
		// MyUtil mu = MyUtil.getInstance();
		String[] themeBagIds = StringUtils.split(para.get("themeBagId"), a);
		// String[] sorts = StringUtils.split(para.get("sort"), a);
		for (int i = 0; i < themeBagIds.length; i++) {
			int themeBagId = Integer.parseInt(themeBagIds[i]);
			TSxAppThemeBagSort po = (TSxAppThemeBagSort) this.getAppstoreDao().findObject("FROM TSxAppThemeBagSort t1 WHERE t1.themeBagId = ? ", new Object[] { themeBagId });
			if (po == null) {
				po = new TSxAppThemeBagSort();
				po.setThemeBagId(themeBagId);
			}
			/*
			 * if (mu.isBlank(sorts[i])) po.setSort(null); else po.setSort(Integer.valueOf((sorts[i])));
			 */
			po.setSort(i + 1);
			this.getAppstoreDao().saveOrUpdate(po);
		}
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
