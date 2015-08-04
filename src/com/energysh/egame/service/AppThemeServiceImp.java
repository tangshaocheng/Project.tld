package com.energysh.egame.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.energysh.egame.po.appstore.TAppTheme;
import com.energysh.egame.po.appstore.TAppThemeList;
import com.energysh.egame.util.Constants;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class AppThemeServiceImp extends BaseService implements AppThemeService {

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		String name = para.get("name");
		int count = this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.id) FROM t_app_theme t1 WHERE t1.name = ?", new Object[] { name });
		if (count > 0) {
			rmap.put("info", "exits");
			return rmap;
		}
		TAppTheme po = new TAppTheme();
		MyUtil mu = MyUtil.getInstance();
		mu.map2Object(para, po);
		this.getAppstoreDao().save(po);
		Integer id = po.getId();
		if (mu.isNotBlank(para.get("pic"))) {
			po.setPic(Constants.SUB_ROOT_THEME + id + Constants.FILE_SEPARATOR + Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR + para.get("pic"));
		}
		this.getAppstoreDao().update(po);
		String appTempRootPath = para.get("tempDirPath");
		String appRootPath = Constants.FILE_ROOT + Constants.SUB_ROOT_THEME + id + Constants.FILE_SEPARATOR;
		FileUtils.copyDirectory(new File(appTempRootPath), new File(appRootPath));
		FileUtils.deleteDirectory(new File(appTempRootPath));
		char a = 6;
		Date ctime = new Date();
		// String[] sorts = StringUtils.split(para.get("sort"), a);
		String[] appIds = StringUtils.split(para.get("appId"), a);
		for (int i = 0; i < appIds.length; i++) {
			TAppThemeList pt = new TAppThemeList();
			pt.setThemeId(id);
			// pt.setSort(Integer.valueOf(sorts[i]));
			pt.setSort(i + 1);
			pt.setAppId(Integer.valueOf(appIds[i]));
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
			this.getAppstoreDao().excute("DELETE TAppTheme WHERE id = ?", new Object[] { Integer.parseInt(id) });
			this.getAppstoreDao().excute("DELETE TAppThemeList WHERE themeId = ?", new Object[] { Integer.parseInt(id) });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

	@Override
	public TAppTheme get(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql("SELECT t2.*, CAST(t1.sort AS SIGNED) sort, CAST(t1.app_id AS SIGNED) appId, CONCAT(t3.name) appName, CONCAT(t3.develope) appAuthor FROM t_app_theme_list t1 LEFT JOIN t_app_theme t2 ON t1.theme_id= t2.id LEFT JOIN t_app t3 ON t1.app_id = t3.id WHERE t1.theme_id = ? ORDER BY t1.sort, t1.id", new Object[] { mu.toInt(para.get("id")) });
		TAppTheme po = new TAppTheme();
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
		StringBuilder sql = new StringBuilder("FROM t_app_theme t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("id"))) {
			sql.append(" AND t1.id = ?");
			plist.add(Integer.valueOf(para.get("id")));
		}
		if (mu.isNotBlank(para.get("name"))) {
			sql.append(" AND t1.name like ?");
			plist.add("%" + para.get("name") + "%");
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
	public Map<String, String> uu(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		int id = Integer.parseInt(para.get("id"));
		String name = para.get("name");
		int count = this.getAppstoreDao().findIntBySql("SELECT t1.* FROM t_app_theme t1 WHERE id != ? AND t1.name = ?", new Object[] { id, name });
		if (count > 0) {
			rmap.put("info", "exits");
			return rmap;
		}
		TAppTheme po = (TAppTheme) this.getAppstoreDao().findObject("FROM TAppTheme t1 WHERE id = ?", new Object[] { id });
		MyUtil mu = MyUtil.getInstance();
		mu.map2Object(para, po);
		
		if (mu.isNotBlank(para.get("pic"))) {
			po.setPic(Constants.SUB_ROOT_THEME + id + Constants.FILE_SEPARATOR + Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR + para.get("pic"));
//			this.getAppstoreDao().update(po);
			String appTempRootPath = para.get("tempDirPath");
			String appRootPath = Constants.FILE_ROOT + Constants.SUB_ROOT_THEME + id + Constants.FILE_SEPARATOR;
			FileUtils.copyDirectory(new File(appTempRootPath), new File(appRootPath));
			FileUtils.deleteDirectory(new File(appTempRootPath));
		}
		this.getAppstoreDao().update(po);
		char a = 6;
		Date ctime = new Date();
		String appIdSql = "0";
		// String[] sorts = StringUtils.split(para.get("sort"), a);
		String[] appIds = StringUtils.split(para.get("appId"), a);
		for (int i = 0; i < appIds.length; i++) {
			appIdSql += ", " + appIds[i];
			TAppThemeList pt = (TAppThemeList) this.getAppstoreDao().findObject("FROM TAppThemeList t1 WHERE themeId = ? AND appId = ?", new Object[] { id, Integer.valueOf(appIds[i]) });
			if (pt == null)
				pt = new TAppThemeList();
			pt.setThemeId(id);
			pt.setSort(Integer.valueOf(i + 1));
			pt.setAppId(Integer.valueOf(appIds[i]));
			pt.setCtime(ctime);
			this.getAppstoreDao().saveOrUpdate(pt);
		}
		this.getAppstoreDao().excute("DELETE FROM TAppThemeList WHERE themeId = ? AND appId NOT IN(" + appIdSql + ")", new Object[] { id });
		rmap.put("info", "ok");
		return rmap;
	}

}
