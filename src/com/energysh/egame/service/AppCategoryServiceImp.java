package com.energysh.egame.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.util.Constants;
import com.energysh.egame.util.PageBar;
import com.energysh.egame.util.Utils;

@SuppressWarnings("unchecked")
public class AppCategoryServiceImp extends BaseService implements AppCategoryService {

	public static final String SORT_TYPE_DOWNIIMES = "downloadTimes";
	public static final String SORT_TYPE_NEWEST = "newest";
	public static final String SORT_TYPE_STARS = "stars";

	public List<Object> getAllCategory() throws Exception {
		return this.getAppstoreDao().findList("from TAppCategory order by seq", new Object[] {});
	}

	public PageBar query(Map<String, String> para) throws Exception {
		TAppCategory appCategory = new TAppCategory();
		Utils.map2Object(para, appCategory);
		para.put("everyPageNum", "1000");
		PageBar pb = new PageBar(para);
		List<Object> plist = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder("from t_app_category a left join t_app_category b on a.father_id= b.id where 1=1 ");
		if (StringUtils.isNotEmpty(appCategory.getName())) {
			hql.append(" and a.name like '%" + appCategory.getName() + "%' ");
		}
		if (Utils.isNumber(para.get("fatherId"))) {
			hql.append(" and a.father_id =? ");
			plist.add(appCategory.getFatherId());
		}
		int categoryId = Integer.valueOf(para.get("categoryId"));
		int subCategoryId = Integer.valueOf(para.get("subCategoryId"));
		if (subCategoryId > 0) {
			hql.append(" and (  a.father_id in (?) or  b.father_id in (?)  OR a.id in (?) or b.id in (?) OR a.id in (?))");
			plist.add(subCategoryId);
			plist.add(subCategoryId);
			plist.add(subCategoryId);
			plist.add(subCategoryId);
			plist.add(categoryId);
		} else if (categoryId > 0) {
			hql.append(" and (  a.father_id = ? or b.father_id = ? OR a.id = ?)");
			plist.add(categoryId);
			plist.add(categoryId);
			plist.add(categoryId);
		}
		if (Utils.isNumber(para.get("level"))) {
			hql.append(" and a.level =? ");
			plist.add(appCategory.getLevel());
		}
		if (Utils.isNumber(para.get("level"))) {
			hql.append(" and a.level =? ");
			plist.add(appCategory.getLevel());
		}
		if (Utils.isNumber(para.get("pubStatus"))) {
			hql.append(" and a.pub_status =? ");
			plist.add(appCategory.getPubStatus());
		}
		pb.setTotalNum(this.getAppstoreDao().findIntBySql(" select count(a.id) " + hql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		hql.insert(0, "select a.* ");
		hql.append(" order by level,seq ");
		List<Map<String, Object>> rList = this.getAppstoreDao().findListMapPageBySql(hql.toString(), plist.toArray(), pb);
		List<Map<String, Object>> rList2 = buildCategoryListTree(rList, 0);
		if (!Utils.isNullEmpty(para.get("name"))) {
			// 处理搜索name二三级菜单不显示
			for (Map<String, Object> map : rList) {
				if (!rList2.contains(map))
					rList2.add(map);
			}
		}
		pb.setResultList(rList2);
		return pb;
	}

	/**
	 * 功能：分类List树状显示
	 * 
	 * @author xinwang.xu
	 * @param list
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> buildCategoryListTree(List<Map<String, Object>> list, Integer categoryId) {
		List<Map<String, Object>> rList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> tList = new ArrayList<Map<String, Object>>();
		int fatherId = 0;
		for (Map<String, Object> map : list) {
			fatherId = Integer.valueOf(map.get("father_id").toString());
			if (fatherId == categoryId) {
				rList.add(map);
				tList = buildCategoryListTree(list, Integer.valueOf(map.get("id").toString()));
				rList.addAll(tList);
			}
		}
		return rList;
	}

	public TAppCategory get(Map<String, String> para) throws Exception {
		int id = Integer.parseInt(para.get("categoryId"));
		return (TAppCategory) this.getAppstoreDao().get(TAppCategory.class, id);
	}

	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		TAppCategory appCategory = new TAppCategory();
		Utils.map2Object(para, appCategory);
		// 判断重复 name为唯一标准
		List<Object> result = this.getAppstoreDao().findList("from TAppCategory a where name=? ", new Object[] { appCategory.getName() });
		if (result != null && result.size() > 0) {
			rmap.put("info", "duplicate");
			return rmap;
		}
		appCategory.setCtime(new Date());
		appCategory.setUptime(new Date());
		int fahterId = 0;
		int categoryId = Integer.valueOf(para.get("categoryId"));
		int subCategoryId = Integer.valueOf(para.get("subCategoryId"));
		int level = 1;
		if (categoryId == 0) {
			level = 1;
			fahterId = 0;
		} else if (subCategoryId == 0) {
			level = 2;
			fahterId = categoryId;
		} else if (subCategoryId > 0) {
			level = 3;
			fahterId = subCategoryId;
		}
		appCategory.setLevel(level);
		appCategory.setIsOpex(appCategory.getIsOpex() != null ? appCategory.getIsOpex() : 0);
		appCategory.setPubStatus(appCategory.getPubStatus() != null ? appCategory.getPubStatus() : 1);
		appCategory.setFatherId(fahterId);
		this.getAppstoreDao().save(appCategory);
		Integer id = appCategory.getId();
		String appTempRootPath = para.get("tempDirPath");
		String appRootPath = Constants.FILE_ROOT + Constants.SUB_ROOT_CATEGORY + id + Constants.FILE_SEPARATOR;
		FileUtils.copyDirectory(new File(appTempRootPath), new File(appRootPath));
		FileUtils.deleteDirectory(new File(appTempRootPath));
		// 更新资源地址
		appCategory.setIcon(Constants.SUB_ROOT_CATEGORY + id + Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON + Constants.FILE_SEPARATOR + appCategory.getIcon());
		this.getAppstoreDao().update(appCategory);
		rmap.put("info", "ok");
		return rmap;
	}

	public Map<String, String> up(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		TAppCategory appCategory = new TAppCategory();
		Utils.map2Object(para, appCategory);
		// 判断重复 name为唯一标准
		List<Object> result = this.getAppstoreDao().findList("from TAppCategory a where name=? and id!=?", new Object[] { appCategory.getName(), appCategory.getId() });
		if (result != null && result.size() > 0) {
			rmap.put("info", "duplicate");
			return rmap;
		}
		Integer id = appCategory.getId();
		TAppCategory srcAppCategory = (TAppCategory) this.getAppstoreDao().get(TAppCategory.class, id);
		this.getAppstoreDao().evict(srcAppCategory);
		if (srcAppCategory == null) {
			rmap.put("info", "notExist");
			return rmap;
		}
		if (appCategory.getIcon() == null) {
			appCategory.setIcon(srcAppCategory.getIcon());
		} else {
			appCategory.setIcon(Constants.SUB_ROOT_CATEGORY + id + Constants.FILE_SEPARATOR + Constants.FILE_TYPE_ICON + Constants.FILE_SEPARATOR + appCategory.getIcon());
		}
		appCategory.setCtime(srcAppCategory.getCtime());
		// 更新时间
		appCategory.setUptime(new Date());
		appCategory.setLevel(appCategory.getLevel() != null ? appCategory.getLevel() : srcAppCategory.getLevel());
		appCategory.setIsOpex(appCategory.getIsOpex() != null ? appCategory.getIsOpex() : srcAppCategory.getIsOpex());
		appCategory.setPubStatus(appCategory.getPubStatus() != null ? appCategory.getPubStatus() : srcAppCategory.getPubStatus());
		appCategory.setFatherId(appCategory.getFatherId() != null ? appCategory.getFatherId() : srcAppCategory.getFatherId());
		this.getAppstoreDao().update(appCategory);
		String appTempRootPath = para.get("tempDirPath");
		String appRootPath = Constants.FILE_ROOT + Constants.SUB_ROOT_CATEGORY + id + Constants.FILE_SEPARATOR;
		FileUtils.copyDirectory(new File(appTempRootPath), new File(appRootPath));
		FileUtils.deleteDirectory(new File(appTempRootPath));
		rmap.put("info", "ok");
		return rmap;
	}

	public Map<String, String> del(Map<String, String> para) throws Exception {
		int id = Integer.parseInt(para.get("id"));
		Map<String, String> rmap = new HashMap<String, String>();
		int count = this.getAppstoreDao().findInt(" from TApp where categoryId=?", new Object[] { id });
		if (count > 0) {
			rmap.put("info", "app");
			return rmap;
		}
		count = this.getAppstoreDao().findInt(" from TAppCategory where fatherId=?", new Object[] { id });
		if (count > 0) {
			rmap.put("info", "sub");
			return rmap;
		}
		// 删除文件夹
		File file = new File(Constants.FILE_ROOT + Constants.SUB_ROOT_CATEGORY + id);
		if (file.exists()) {
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		TAppCategory appCategory = new TAppCategory();
		appCategory.setId(id);
		this.getAppstoreDao().del(appCategory);
		rmap.put("info", "ok");
		return rmap;
	}

	public TAppCategory queryById(int categoryId) throws Exception {
		TAppCategory po = (TAppCategory) this.getAppstoreDao().findObject("from TAppCategory where fatherId=?", new Object[] { categoryId });
		return po;
	}

	public List<TAppCategory> queryCategoryByFatherId(Map<String, String> para) throws Exception {
		int categoryId = NumberUtils.toInt(para.get("categoryId"));
		List<TAppCategory> list = this.getAppstoreDao().findList("from TAppCategory where fatherId=?", new Object[] { categoryId });
		return list;
	}

	public List<TAppCategory> queryCategoryByLevel(Map<String, String> para) throws Exception {
		int categoryId = NumberUtils.toInt(para.get("categoryId"));
		List<TAppCategory> list = this.getAppstoreDao().findList("from TAppCategory where level=? order by fatherId", new Object[] { categoryId });
		return list;
	}

}