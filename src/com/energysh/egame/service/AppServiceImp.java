package com.energysh.egame.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.energysh.egame.po.appstore.TApp;
import com.energysh.egame.po.appstore.TAppBatch;
import com.energysh.egame.util.Constants;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class AppServiceImp extends BaseService implements AppService {

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		TApp app = new TApp();
		mu.map2Object(para, app);
		String versionCode = para.get("versionCode");
		if (!"".equals(versionCode) && null != versionCode)
			app.setVersionCode(Integer.valueOf(versionCode));
		List<?> count = this
				.getAppstoreDao()
				.findList(
						"from TApp where appSource=? and name=? and develope=? and appStatus>=0",
						new Object[] { 0, app.getName(), app.getDevelope() });
		if (count != null && count.size() > 0) {
			rmap.put("info", "isR");
			return rmap;
		}
		app.setCtime(new Date());
		app.setUptime(new Date());
		app.setDownCnt(0);
		app.setSourceUrl("");
		app.setAppSource(0);
		app.setAppStatus(1);
		app.setNotifyStatus(1);
		app.setShareContent(para.get("shareContent"));
		app.setCategoryId(Integer.valueOf(para.get("categoryId")));
		app.setSubjectId(app.getSubjectId() == null ? 0 : app.getSubjectId());
		app.setVersionCode(app.getVersionCode() == null ? 0 : app
				.getVersionCode());
		// app.setAppTag(app.getAppTag() == null ? "" : app.getAppTag());
		app.setOsVersionMin(app.getOsVersionMin() == null ? "" : app
				.getOsVersionMin());
		app.setSingleWord(app.getAppTag() == null ? "" : app.getSingleWord());
		if (para.get("appSize") != null) {
			app.setAppSize(Double.valueOf(para.get("appSize")));
		}
		if (para.get("appDesc") != null) {
			app.setAppDesc(para.get("appDesc"));
		}
		if (para.get("embededApp") != null) {
			app.setEmbededApp(para.get("embededApp"));
		}
		if (para.get("embededAppSize") != null) {
			app.setEmbededAppSize(Double.valueOf(para.get("embededAppSize")));
		}
		if (para.get("embededMainClass") != null) {
			app.setEmbededMainClass(para.get("embededMainClass"));
		}
		if (para.get("embededPakeage") != null) {
			app.setEmbededPakeage(para.get("embededPakeage"));
		}
		if (para.get("embededVersion") != null) {
			app.setEmbededVersion(para.get("embededVersion"));
		}
		if (para.get("mainClass") != null) {
			app.setMainClass(para.get("mainClass"));
		}
		if (para.get("shareContent") != null) {
			app.setShareContent(para.get("shareContent"));
		}
		if (para.get("singleWord") != null) {
			app.setSingleWord(para.get("singleWord"));
		}
		if (para.get("upDesc") != null) {
			app.setUpDesc(para.get("upDesc"));
		}
		if (para.get("osVersionMin") != null) {
			app.setOsVersionMin(para.get("osVersionMin"));
		}
		if (para.get("appTag") != null) {
			app.setAppTag(para.get("appTag"));
		} 
		if (para.get("support") != null) {
			app.setSupport(para.get("support"));
		}
		if (para.get("ageLimit") != null) {
			app.setAgeLimit(para.get("ageLimit"));
		}
		this.getAppstoreDao().save(app);

		// TStatisticsRank statisticsRank = new TStatisticsRank();
		// statisticsRank.setAppId(app.getId());
		// statisticsRank.setAppName(app.getName());
		// statisticsRank.setBaseNum(1);
		// statisticsRank.setRealAllCount(0);
		// statisticsRank.setMonthRealCount(0);
		// statisticsRank.setMonthReviseCount(0);
		// statisticsRank.setWeekRealCount(0);
		// statisticsRank.setWeekReviseCount(0);
		// statisticsRank.setCtime(new Date());
		// this.getAppstoreDao().save(statisticsRank);

		Integer id = app.getId();
		String appTempRootPath = para.get("tempDirPath");
		String appRootPath = Constants.FILE_ROOT + Constants.SUB_ROOT_APP + id
				+ Constants.FILE_SEPARATOR;
		FileUtils.copyDirectory(new File(appTempRootPath),
				new File(appRootPath));
		FileUtils.deleteDirectory(new File(appTempRootPath));

		// 更新资源地址
		app.setApp(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
				+ Constants.FILE_TYPE_APP + Constants.FILE_SEPARATOR
				+ app.getApp());
		if (app.getEmbededApp() != null)
			app.setEmbededApp(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_EMBEDED_APP
					+ Constants.FILE_SEPARATOR + app.getEmbededApp());
		app.setIcon(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
				+ Constants.FILE_TYPE_ICON + Constants.FILE_SEPARATOR
				+ app.getIcon());
		app.setMainPic(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
				+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
				+ app.getMainPic());
		app.setMainPic2(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
				+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
				+ app.getMainPic2());
		app.setPic1(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
				+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
				+ app.getPic1());
		app.setPic2(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
				+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
				+ app.getPic2());
		if (app.getPic3() != null) {
			app.setPic3(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic3());
		}
		if (app.getPic4() != null) {
			app.setPic4(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic4());
		}
		if (app.getPic5() != null) {
			app.setPic5(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic5());
		}
		this.getAppstoreDao().update(app);
		char a = 6;
		String[] indexs = StringUtils.split(para.get("index"), ",");
		String[] batchIds = StringUtils.split(para.get("batchId"), a);
		for (int i = 0; i < batchIds.length; i++) {
			TAppBatch appBatch = new TAppBatch();
			appBatch.setId(id + "|" + batchIds[i]);
			appBatch.setAppId(id);
			appBatch.setBatchId(batchIds[i]);
			appBatch.setApp(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR + Constants.FILE_TYPE_APP_BATCH
					+ Constants.FILE_SEPARATOR
					+ para.get("appBatch_" + indexs[i]));
			appBatch.setAppSize(NumberUtils.toDouble(para.get("appBatchSize_"
					+ indexs[i])));
			appBatch.setCtime(new Date());
			this.getAppstoreDao().save(appBatch);
		}
		try {
			this.getMemProgrammer().delete(
					"queryApp_appPakeage_" + app.getPakeage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 同步应用到推广专题包47 */
		try {
			this.getAppstoreDao()
					.bulkUpdate(
							"insert into t_app_theme_bag_list(theme_bag_id,sub_type,sub_id,sort) values(47,1,?,999)",
							new Object[] { app.getId() });
		} catch (Exception e) {
			e.printStackTrace();
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
			this.getAppstoreDao().excute("DELETE TApp WHERE id = ?",
					new Object[] { id });
			this.getAppstoreDao().excute("DELETE TAppBatch WHERE appId = ?",
					new Object[] { id });
			this.getAppstoreDao()
					.excute("DELETE TAppThemeList WHERE appId = ?",
							new Object[] { id });
			this.getAppstoreDao().excute(
					"DELETE TAppCategoryGood WHERE appId = ?",
					new Object[] { id });
			this.getAppstoreDao().excute(
					"DELETE TAppThemeBagList WHERE subType = 1 AND subId = ?",
					new Object[] { id });
			rmap.put("info", "ok");
		} else {
			rmap.put("info", "error");
		}
		return rmap;
	}

	@Override
	public TApp get(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		Map<String, Object> rmap = this.getAppstoreDao().findMapBySql(
				"SELECT t1.* FROM t_app t1 WHERE t1.id = ?",
				new Object[] { mu.toInt(para.get("id")) });
		TApp po = new TApp();
		if (MapUtils.isNotEmpty(rmap))
			mu.map2Object(rmap, po);
		@SuppressWarnings("unchecked")
		List<TAppBatch> appBatchList = this.getAppstoreDao().findListBySql(
				"SELECT t1.* FROM t_app_batch t1 WHERE t1.app_id = ?",
				new Object[] { mu.toInt(rmap.get("id").toString()) },
				TAppBatch.class);
		po.setAppBatchList(appBatchList);
		return po;
	}

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM t_app t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("id"))) {
			sql.append(" AND t1.id = ?");
			plist.add(Integer.valueOf(para.get("id")));
		}
		if (StringUtils.isNotEmpty(para.get("name"))) {
			sql.append(" AND t1.name like ?");
			plist.add("%" + para.get("name") + "%");
		}
		if (StringUtils.isNotEmpty(para.get("develope"))) {
			sql.append(" AND t1.develope like ?");
			plist.add("%" + para.get("develope") + "%");
		}
		if (StringUtils.isNotEmpty(para.get("categoryId"))) {
			sql.append(" AND t1.category_id = ?");
			plist.add(para.get("categoryId"));
		}
		if (StringUtils.isNotEmpty(para.get("appSource"))) {
			sql.append(" AND t1.app_source = ?");
			plist.add(para.get("appSource"));
		}
		int numCn = this.getAppstoreDao().findIntBySql(
				"SELECT COUNT(t1.id) " + sql, plist.toArray());
		if (StringUtils.isNotBlank(para.get("language"))) {
			numCn += this.getAppstoreEnDao().findIntBySql(
					"SELECT COUNT(t1.id) " + sql + " AND t1.app_source = 0",
					plist.toArray());
		}
		pb.setTotalNum(numCn);
		if (pb.getTotalNum() == 0)
			return pb;
		List<Map<String, Object>> rList = this.getAppstoreDao()
				.findListMapPageBySql(
						"SELECT t1.*, 0 language " + sql + " ORDER BY id DESC",
						plist.toArray(), pb);
		if (StringUtils.isNotBlank(para.get("language"))
				&& rList.size() < pb.getEveryPageNum()) {
			int firstResult = (rList.size() > 0 && pb.getCurrentPageNum() > 1) ? 0
					: (pb.getCurrentPageNum() - 1) * pb.getEveryPageNum();
			int maxResult = pb.getEveryPageNum() - rList.size();
			List<Map<String, Object>> rListEn = this
					.getAppstoreEnDao()
					.findListMapBySql(
							"SELECT t1.*, 1 language "
									+ sql
									+ " AND t1.app_source = 0 ORDER BY id DESC LIMIT "
									+ firstResult + ", " + maxResult,
							plist.toArray());
			rList.addAll(rList.size(), rListEn);
		}
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		TApp app = new TApp();
		mu.map2Object(para, app);

		Integer id = app.getId();
		TApp srcApp = this.get(para);
		int versionCode = srcApp.getVersionCode() == null ? 0 : srcApp
				.getVersionCode();
		this.getAppstoreDao().evict(srcApp);
		if (srcApp == null) {
			rmap.put("info", "notExist");
			return rmap;
		}
		if (app.getApp() == null) {
			app.setApp(srcApp.getApp());
		} else {
			app.setApp(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_APP + Constants.FILE_SEPARATOR
					+ app.getApp());
		}
		if (app.getEmbededApp() == null) {
			app.setEmbededApp(srcApp.getEmbededApp());
		} else {
			app.setEmbededApp(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_EMBEDED_APP
					+ Constants.FILE_SEPARATOR + app.getEmbededApp());
		}
		if (app.getIcon() == null) {
			app.setIcon(srcApp.getIcon());
		} else {
			app.setIcon(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_ICON + Constants.FILE_SEPARATOR
					+ app.getIcon());
		}
		if (app.getMainPic() == null) {
			app.setMainPic(srcApp.getMainPic());
		} else {
			app.setMainPic(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR + Constants.FILE_TYPE_PIC
					+ Constants.FILE_SEPARATOR + app.getMainPic());
		}
		if (app.getMainPic2() == null) {
			app.setMainPic2(srcApp.getMainPic2());
		} else {
			app.setMainPic2(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR + Constants.FILE_TYPE_PIC
					+ Constants.FILE_SEPARATOR + app.getMainPic2());
		}
		if (app.getPic1() == null) {
			app.setPic1(srcApp.getPic1());
		} else {
			app.setPic1(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic1());
		}
		if (app.getPic2() == null) {
			app.setPic2(srcApp.getPic2());
		} else {
			app.setPic2(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic2());
		}
		if (app.getPic3() == null) {
			app.setPic3(srcApp.getPic3());
		} else {
			app.setPic3(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic3());
		}
		if (app.getPic4() == null) {
			app.setPic4(srcApp.getPic4());
		} else {
			app.setPic4(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic4());
		}
		if (app.getPic5() == null) {
			app.setPic5(srcApp.getPic5());
		} else {
			app.setPic5(Constants.SUB_ROOT_APP + id + Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_PIC + Constants.FILE_SEPARATOR
					+ app.getPic5());
		}
		if (para.get("appSize") != null) {
			app.setAppSize(Double.valueOf(para.get("appSize")));
		}
		if (para.get("appDesc") != null) {
			app.setAppDesc(para.get("appDesc"));
		}
		if (para.get("embededApp") != null) {
			app.setEmbededApp(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR
					+ Constants.FILE_TYPE_EMBEDED_APP
					+ Constants.FILE_SEPARATOR + para.get("embededApp"));
		}
		if (para.get("embededAppSize") != null) {
			app.setEmbededAppSize(Double.valueOf(para.get("embededAppSize")));
		}
		if (para.get("embededMainClass") != null) {
			app.setEmbededMainClass(para.get("embededMainClass"));
		}
		if (para.get("embededPakeage") != null) {
			app.setEmbededPakeage(para.get("embededPakeage"));
		}
		if (para.get("embededVersion") != null) {
			app.setEmbededVersion(para.get("embededVersion"));
		}
		if (para.get("mainClass") != null) {
			app.setMainClass(para.get("mainClass"));
		}
		if (para.get("shareContent") != null) {
			app.setShareContent(para.get("shareContent"));
		}
		if (para.get("singleWord") != null) {
			app.setSingleWord(para.get("singleWord"));
		}
		if (para.get("upDesc") != null) {
			app.setUpDesc(para.get("upDesc"));
		}
		if (para.get("osVersionMin") != null) {
			app.setOsVersionMin(para.get("osVersionMin"));
		}
		if (para.get("support") != null) {
			app.setSupport(para.get("support"));
		}
		if (para.get("appTag") != null) {
			app.setAppTag(para.get("appTag"));
		}
		if (para.get("ageLimit") != null) {
			app.setAgeLimit(para.get("ageLimit"));
		}
		if (para.get("versionCode") != null
				&& !"".equals(para.get("versionCode"))) {
			app.setVersionCode(Integer.valueOf(para.get("versionCode")));
		}

		app.setNotify(srcApp.getNotify());
		app.setNotifyStatus(srcApp.getNotifyStatus());
		app.setUptime(new Date());
		app.setCtime(srcApp.getCtime());
		app.setSourceUrl("");
		app.setAppSource(0);
		app.setAppStatus(1);

		// app.setAppTag(app.getAppTag() == null ? "" : srcApp.getAppTag());
		app.setCategoryId(Integer.valueOf(para.get("categoryId")));
		app.setSubjectId(app.getSubjectId() == null ? 0 : app.getSubjectId());
		app.setVersionCode(app.getVersionCode() == null ? versionCode : app
				.getVersionCode());
		app.setOsVersionMin(app.getOsVersionMin() == null ? "" : app
				.getOsVersionMin());
		app.setSingleWord(app.getAppTag() == null ? "" : app.getSingleWord());
		app.setDownCnt(srcApp.getDownCnt());
		this.getAppstoreDao().update(app);
		String appTempRootPath = para.get("tempDirPath");
		String appRootPath = Constants.FILE_ROOT + Constants.SUB_ROOT_APP + id
				+ Constants.FILE_SEPARATOR;
		FileUtils.copyDirectory(new File(appTempRootPath),
				new File(appRootPath));
		FileUtils.deleteDirectory(new File(appTempRootPath));

		char a = 6;
		String[] indexs = StringUtils.split(para.get("index"), ",");
		String[] batchIds = StringUtils.split(para.get("batchId"), a);
		for (int i = 0; i < batchIds.length && i < indexs.length; i++) {
			String filename = para.get("appBatch_" + indexs[i]);
			if (StringUtils.isBlank(filename))
				continue;
			TAppBatch appBatch = new TAppBatch();
			appBatch.setId(id + "|" + batchIds[i]);
			appBatch.setAppId(id);
			appBatch.setBatchId(batchIds[i]);
			appBatch.setApp(Constants.SUB_ROOT_APP + id
					+ Constants.FILE_SEPARATOR + Constants.FILE_TYPE_APP_BATCH
					+ Constants.FILE_SEPARATOR
					+ para.get("appBatch_" + indexs[i]));
			appBatch.setAppSize(NumberUtils.toDouble(para.get("appBatchSize_"
					+ indexs[i])));
			appBatch.setCtime(new Date());
			this.getAppstoreDao().saveOrUpdate(appBatch);
		}
		if (batchIds.length > 0) {
			String batchIdStr = ArrayUtils.toString(batchIds);
			String batchIdCon = StringUtils.replace(StringUtils.replace(
					StringUtils.replace(batchIdStr, "{", "'"), ",", "','"),
					"}", "'");
			this.getAppstoreDao().excuteBySql(
					"DELETE FROM t_app_batch WHERE app_id = " + id
							+ " AND batch_id NOT IN(" + batchIdCon + ")");
		} else {
			this.getAppstoreDao().excuteBySql(
					"DELETE FROM t_app_batch WHERE app_id = " + id);
		}

		try {
			this.getMemProgrammer().delete("queryApp_appList_" + app.getId());
			this.getMemProgrammer().delete(
					"queryApp_appPakeage_" + app.getPakeage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		rmap.put("info", "ok");
		return rmap;
	}

}
