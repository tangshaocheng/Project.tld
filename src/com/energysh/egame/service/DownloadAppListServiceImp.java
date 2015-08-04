package com.energysh.egame.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.po.appstore.TAppComment;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.ParaUtils;

public class DownloadAppListServiceImp extends BaseService implements DownloadAppListService {

	public final static int totalNum = 6;

	public Map<String, Object> query(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		String method = para.get("method");
		String type = para.get("type");
		int sort = 0;
		if (mu.equalsIgnoreCase(method, "recommendList")) {
			sort = 1;
		} else if (mu.equalsIgnoreCase(method, "appList") && mu.equalsIgnoreCase(type, "1")) {
			sort = 2;
		} else if (mu.equalsIgnoreCase(method, "appList") && mu.equalsIgnoreCase(type, "2")) {
			sort = 3;
		} else if (mu.equalsIgnoreCase(method, "themePicList") && mu.equalsIgnoreCase(type, "1")) {
			sort = 4;
		} else if (mu.equalsIgnoreCase(method, "appList") && mu.equalsIgnoreCase(type, "3")) {
			sort = 5;
		} else if (mu.equalsIgnoreCase(method, "themePicList") && mu.equalsIgnoreCase(type, "2")) {
			sort = 6;
		} else if (mu.equalsIgnoreCase(method, "appList") && mu.equalsIgnoreCase(type, "4")) {
			sort = 7;
		} else if (mu.equalsIgnoreCase(method, "themePicList") && mu.equalsIgnoreCase(type, "3")) {
			sort = 8;
		} else if (mu.equalsIgnoreCase(method, "appList") && mu.equalsIgnoreCase(type, "5")) {
			sort = 9;
		} else if (mu.equalsIgnoreCase(method, "nearmeApp")) {

		}
		StringBuffer sql = new StringBuffer();
		List<Object> plist = new ArrayList<Object>();
		Map<String, Object> rmap = new LinkedHashMap<String, Object>();
		rmap.put("result", 1);
		Map<String, Object> subjectMap = new LinkedHashMap<String, Object>();
		if (mu.equalsIgnoreCase(method, "appCommentList")) {
			sql.append("SELECT t1.*, t2.name FROM t_app_comment t1 LEFT JOIN t_app_user t2 ON t1.user_id = t2.id WHERE 1=1");
			if (!"0".equals(para.get("ver"))) {
				sql.append(" AND t1.app_version = ?");
				plist.add(para.get("ver"));
			}
			List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> temp : rlist) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("commentTitle", temp.get("title"));
				map.put("userId", temp.get("user_id"));
				map.put("userName", temp.get("user_name"));
				map.put("ret", temp.get("ret"));
				map.put("comment", temp.get("comment"));
				map.put("time", mu.formateDate(temp.get("ctime").toString(), "yyyy-MM-dd HH:mm:ss"));
				list.add(map);
			}
			subjectMap.put("list", list);
			subjectMap.put("size", list.size());
		} else if (mu.equalsIgnoreCase(method, "addComment")) {
			plist.add(para.get("mac"));
			plist.add(para.get("appId"));
			String userId = this.getAppstoreDao().findString("SELECT userId FROM TAppUserAccount WHERE mac = ? and appId = ?", plist.toArray());
			TAppComment po = new TAppComment();
			po.setTitle(para.get("title"));
			po.setUserId(mu.toInt(userId));
			po.setGrade(mu.toInt(para.get("star")));
			po.setContent(para.get("comment"));
			this.getAppstoreDao().save(po);
			subjectMap.put("msg", "提交成功");
		} else {
			sql.append("SELECT * FROM ( ");
			sql.append("SELECT * FROM ( ");
			sql.append("SELECT t3.sort, t5.theme_id subj_id, CONCAT(t2.name) subj_name, t4.pic subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = " + sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
			sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 2 AND t2.id = t3.theme_bag_id ");
			sql.append("LEFT JOIN t_app_theme t4 ON t3.sub_id = t4.id ");
			sql.append("LEFT JOIN t_app_theme_list t5 ON t4.id = t5.theme_id ");
			sql.append("LEFT JOIN t_app t6 ON t5.app_id = t6.id ");
			sql.append("LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
			sql.append("ORDER BY t3.sort, t5.sort) t1 ");
			sql.append("UNION ALL ");
			sql.append("SELECT * FROM ( ");
			sql.append("SELECT t3.sort, 0 subj_id, CONCAT(t2.name) subj_name, '' subj_pic, t7.cat1_name, t7.cat2_name, t6.*  FROM (SELECT * FROM t_app_theme_bag_sort t1 WHERE t1.sort = " + sort + ") t1 LEFT JOIN t_app_theme_bag t2 ON t1.theme_bag_id = t2.id ");
			sql.append("LEFT JOIN t_app_theme_bag_list t3 ON t3.sub_type = 1 AND t2.id = t3.theme_bag_id ");
			sql.append("LEFT JOIN t_app t6 ON t3.sub_id = t6.id ");
			sql.append("LEFT JOIN (SELECT t1.id, CONCAT(t1.name) cat2_name, CONCAT(t2.name) cat1_name FROM (SELECT * FROM t_app_category WHERE level = 2) t1 LEFT JOIN (SELECT * FROM t_app_category WHERE level = 1) t2 ON t1.level = t2.id) t7 ON t6.category_id = t7.id ");
			sql.append("ORDER BY t3.sort) t2 ");
			sql.append(") t1 WHERE t1.sort IS NOT NULL ORDER BY t1.sort");
			List<Map<String, Object>> rlist = this.getAppstoreDao().findListMapBySql(sql.toString(), plist.toArray());
			Map<String, Integer> cThemeMap = new LinkedHashMap<String, Integer>();
			for (Map<String, Object> map : rlist) {
				String subjId = map.get("subj_id").toString();
				if ("0".equals(subjId))
					cThemeMap.put(subjId, 0);
				else if (cThemeMap.containsKey(subjId))
					cThemeMap.put(subjId, 2);
				else
					cThemeMap.put(subjId, 1);
			}
			Map<String, Boolean> isThemeMap = new LinkedHashMap<String, Boolean>();
			for (String key : cThemeMap.keySet()) {
				Integer value = cThemeMap.get(key);
				if (value > 1)
					isThemeMap.put(key, true);
				else
					isThemeMap.put(key, false);
			}
			DecimalFormat df = new DecimalFormat("#.00");
			Map<String, Boolean> countMap = new LinkedHashMap<String, Boolean>();
			if (mu.equalsIgnoreCase(method, "themePicList")) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < rlist.size(); i++) {
					Map<String, Object> map = rlist.get(i);
					String subjId = map.get("subj_id").toString();
					Map<String, Object> appMap = new LinkedHashMap<String, Object>();
					if (isThemeMap.get(subjId)) {
						if (!countMap.containsKey(subjId)) {
							appMap.put("id", Integer.parseInt(subjId));
							appMap.put("pic", ParaUtils.checkPicUri(map.get("subj_pic")));
							appMap.put("type", 2);
							list.add(appMap);
							countMap.put(subjId, true);
						}
						continue;
					}
					appMap.put("id", Integer.parseInt(map.get("id").toString()));
					appMap.put("pic", ParaUtils.checkPicUri(map.get("icon")));
					appMap.put("type", 1);
					appMap.put("categoryId", map.get("category_id"));
					appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "", map.get("id").toString()));
					appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
					appMap.put("name", map.get("name"));
					appMap.put("summary", map.get("single_word"));
					appMap.put("support", map.get("support"));
					appMap.put("newFuture", map.get("up_desc"));
					appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
					appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
					appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
					appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
					appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
					appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
					Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
					infoMap.put("company", map.get("develope"));
					infoMap.put("classify", map.get("cat2_name"));
					infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
					infoMap.put("version", map.get("version"));
					infoMap.put("size", df.format(map.get("app_size")));
					infoMap.put("ageLimit", map.get("age_limit"));
					infoMap.put("compaticity", map.get("os_version_min"));
					appMap.put("infomation", infoMap);
					appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
					list.add(appMap);
				}
				subjectMap.put("list", list);
				subjectMap.put("size", list.size());
			} else if (mu.equalsIgnoreCase(method, "appList") || mu.equalsIgnoreCase(method, "recommendList")) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < rlist.size(); i++) {
					Map<String, Object> map = rlist.get(i);
					String subjId = map.get("subj_id").toString();
					if (i == 0) {
						subjectMap.put("subjectName", map.get("subj_name"));
					}
					Map<String, Object> appMap = new LinkedHashMap<String, Object>();
					if (isThemeMap.get(subjId)) {
						if (!countMap.containsKey(subjId)) {
							appMap.put("id", Integer.parseInt(subjId));
							if (mu.equalsIgnoreCase(method, "recommendList")) {
								appMap.put("pic", ParaUtils.checkPicUri(map.get("subj_pic")));
								appMap.put("type", 2);
							}
							list.add(appMap);
							countMap.put(subjId, true);
						}
						continue;
					}
					appMap.put("id", Integer.parseInt(map.get("id").toString()));
					if (mu.equalsIgnoreCase(method, "recommendList")) {
						appMap.put("pic", ParaUtils.checkPicUri(map.get("icon")));
						appMap.put("type", 1);
					}
					appMap.put("categoryId", map.get("category_id"));
					appMap.put("app", ParaUtils.checkAppUri(map.get("app"), para.get("batchId"), para.get("mac"), "", map.get("id").toString()));
					appMap.put("icon", ParaUtils.checkPicUri(map.get("icon")));
					appMap.put("name", map.get("name"));
					appMap.put("summary", map.get("single_word"));
					appMap.put("support", map.get("support"));
					appMap.put("newFuture", map.get("up_desc"));
					appMap.put("ret", Integer.parseInt(map.get("heat").toString()));
					appMap.put("pic1", ParaUtils.checkPicUri(map.get("pic1")));
					appMap.put("pic2", ParaUtils.checkPicUri(map.get("pic2")));
					appMap.put("pic3", ParaUtils.checkPicUri(map.get("pic3")));
					appMap.put("pic4", ParaUtils.checkPicUri(map.get("pic4")));
					appMap.put("pic5", ParaUtils.checkPicUri(map.get("pic5")));
					Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
					infoMap.put("company", map.get("develope"));
					infoMap.put("classify", map.get("cat2_name"));
					infoMap.put("lastestUpdate", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd"));
					infoMap.put("version", map.get("version"));
					infoMap.put("size", df.format(map.get("app_size")));
					infoMap.put("ageLimit", map.get("age_limit"));
					infoMap.put("compaticity", map.get("os_version_min"));
					appMap.put("infomation", infoMap);
					appMap.put("deployTime", mu.formateDate(map.get("uptime").toString(), "yyyy-MM-dd HH:mm:ss"));
					list.add(appMap);
				}
				subjectMap.put("list", list);
				subjectMap.put("size", list.size());
			}
		}
		rmap.put("data", subjectMap);
		return rmap;
	}
}
