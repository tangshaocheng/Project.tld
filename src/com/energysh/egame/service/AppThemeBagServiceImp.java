package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.energysh.egame.po.appstore.TAppCategoryGood;
import com.energysh.egame.po.appstore.TAppThemeBag;
import com.energysh.egame.po.appstore.TAppThemeBagList;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class AppThemeBagServiceImp extends BaseService implements AppThemeBagService {

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		String name = para.get("name");
		int count = this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) FROM t_app_theme_bag t1 WHERE t1.name = ?", new Object[] { name });
		if (count > 0) {
			rmap.put("info", "exits");
			return rmap;
		}
		TAppThemeBag po = new TAppThemeBag();
		MyUtil mu = MyUtil.getInstance();
		mu.map2Object(para, po);
		po.setBagType(Integer.valueOf(para.get("bagType")));
		this.getAppstoreDao().save(po);
		Integer id = po.getId();
		char a = 6;
		Date ctime = new Date();
		// String[] sorts = StringUtils.split(para.get("sort"), a);
		String[] subIds = StringUtils.split(para.get("subId"), a);
		String[] subTypes = StringUtils.split(para.get("subType"), a);
		for (int i = 0; i < subIds.length; i++) {
			TAppThemeBagList pt = new TAppThemeBagList();
			pt.setThemeBagId(id);
			// pt.setSort(Integer.valueOf(sorts[i]));
			pt.setSort(i + 1);
			pt.setSubId(Integer.valueOf(subIds[i]));
			pt.setSubType(Integer.valueOf(subTypes[i]));
			pt.setCtime(ctime);
			this.getAppstoreDao().save(pt);
		}
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public Map<String, String> del(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		String id = para.get("id");
		if (mu.isNotBlank(id)) {
			this.getAppstoreDao().excute("DELETE TAppThemeBag WHERE id = ?", new Object[] { Integer.parseInt(id) });
			this.getAppstoreDao().excute("DELETE TAppThemeBagList WHERE themeBagId = ?", new Object[] { Integer.parseInt(id) });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

	@Override
	public TAppThemeBag get(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql("SELECT t2.*, CAST(t1.sort AS SIGNED) sort, CAST(t1.sub_type AS SIGNED) subType, CAST(t1.sub_id AS SIGNED) subId, CONCAT(IF(t1.sub_type=1,t3.name,t4.name)) subName, CONCAT(IF(t1.sub_type=1,t3.develope,'')) subAuthor FROM t_app_theme_bag_list t1 LEFT JOIN t_app_theme_bag t2 ON t2.id = t1.theme_bag_id LEFT JOIN t_app t3 ON t1.sub_type = 1 AND t1.sub_id = t3.id LEFT JOIN t_app_theme t4 ON t1.sub_type = 2 AND t1.sub_id = t4.id WHERE t1.theme_bag_id = ? ORDER BY t1.sort, t1.id", new Object[] { mu.toInt(para.get("id")) });
		TAppThemeBag po = new TAppThemeBag();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rlist.size(); i++) {
			if (i == 0) {
				mu.map2Object(rlist.get(i), po);
			}
			list.add(rlist.get(i));
		}
		po.setList(list);
		return po;
	}

	@Override
	public TAppThemeBag getCategoryGood(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql("SELECT CAST(t1.sort AS SIGNED) sort, 1 subType, CAST(t1.app_id AS SIGNED) subId, CONCAT(t3.name) subName, CONCAT(t3.develope) subAuthor FROM t_app_category_good t1 LEFT JOIN t_app t3 ON  t1.app_id = t3.id  WHERE t1.category_id = ? ORDER BY t1.sort", new Object[] { mu.toInt(para.get("id")) });
		TAppThemeBag po = new TAppThemeBag();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rlist.size(); i++) {
			if (i == 0) {
				mu.map2Object(rlist.get(i), po);
			}
			list.add(rlist.get(i));
		}
		po.setList(list);
		return po;
	}

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM t_app_theme_bag t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("id"))) {
			sql.append(" AND t1.id = ?");
			plist.add(Integer.valueOf(para.get("id")));
		}
		if (mu.isNotBlank(para.get("name"))) {
			sql.append(" AND t1.name like ?");
			plist.add("%" + para.get("name") + "%");
		}
		if (mu.isNotBlank(para.get("bagType"))) {
			sql.append(" AND t1.bag_type = ?");
			plist.add(Integer.valueOf(para.get("bagType")));
		}
		pb.setTotalNum(this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.* ");
		List<Map<String, Object>> rList = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> sort(Map<String, String> para) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		int id = Integer.parseInt(para.get("id"));
		String name = para.get("name");
		int count = this.getAppstoreDao().findIntBySql("SELECT t1.* FROM t_app_theme_bag t1 WHERE t1.id != ? AND t1.name = ?", new Object[] { id, name });
		if (count > 0) {
			rmap.put("info", "exits");
			return rmap;
		}
		TAppThemeBag po = (TAppThemeBag) this.getAppstoreDao().findObject("FROM TAppThemeBag t1 WHERE t1.id = ?", new Object[] { id });
		MyUtil mu = MyUtil.getInstance();
		mu.map2Object(para, po);
		po.setBagType(Integer.valueOf(para.get("bagType")));
		this.getAppstoreDao().update(po);
		char a = 6;
		Date ctime = new Date();
		String appIdSql = "0";
		String themeIdSql = "0";
		// String[] sorts = StringUtils.split(para.get("sort"), a);
		String[] subIds = StringUtils.split(para.get("subId"), a);
		String[] subTypes = StringUtils.split(para.get("subType"), a);
		for (int i = 0; i < subIds.length; i++) {
			if (Integer.parseInt(subTypes[i]) == 1)
				appIdSql += ", " + subIds[i];
			else if (Integer.parseInt(subTypes[i]) == 2)
				themeIdSql += ", " + subIds[i];
			TAppThemeBagList pt = (TAppThemeBagList) this.getAppstoreDao().findObject("FROM TAppThemeBagList t1 WHERE themeBagId = ? AND subType = ? AND subId = ?", new Object[] { id, Integer.valueOf(subTypes[i]), Integer.valueOf(subIds[i]) });
			if (pt == null)
				pt = new TAppThemeBagList();
			pt.setThemeBagId(id);
			// pt.setSort(Integer.valueOf(sorts[i]));
			pt.setSort(i + 1);
			pt.setSubId(Integer.valueOf(subIds[i]));
			pt.setSubType(Integer.valueOf(subTypes[i]));
			pt.setCtime(ctime);
			this.getAppstoreDao().saveOrUpdate(pt);
		}
		this.getAppstoreDao().excute("DELETE FROM TAppThemeBagList WHERE themeBagId = ? AND subType = 1 AND subId NOT IN(" + appIdSql + ")", new Object[] { po.getId() });
		this.getAppstoreDao().excute("DELETE FROM TAppThemeBagList WHERE themeBagId = ? AND subType = 2 AND subId NOT IN(" + themeIdSql + ")", new Object[] { po.getId() });
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public Map<String, String> upCategoryGood(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		int id = Integer.parseInt(para.get("id"));

		char a = 6;
		Date ctime = new Date();
		String appIdSql = "0";
		String[] subIds = StringUtils.split(para.get("subId"), a);
		for (int i = 0; i < subIds.length; i++) {
			appIdSql += ", " + subIds[i];

			TAppCategoryGood pt = (TAppCategoryGood) this.getAppstoreDao().findObject("FROM TAppCategoryGood t1 WHERE categoryId = ? AND appId = ? ", new Object[] { id, Integer.valueOf(subIds[i]) });
			if (pt == null)
				pt = new TAppCategoryGood();
			pt.setSort(i + 1);
			pt.setAppId(Integer.valueOf(subIds[i]));
			pt.setCategoryId(id);
			pt.setCtime(ctime);
			this.getAppstoreDao().saveOrUpdate(pt);
		}
		this.getAppstoreDao().excute("DELETE FROM TAppCategoryGood WHERE categoryId = ? AND appId NOT IN(" + appIdSql + ")", new Object[] { id });
		rmap.put("info", "ok");
		return rmap;
	}

}
