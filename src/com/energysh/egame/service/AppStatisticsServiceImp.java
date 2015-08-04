package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class AppStatisticsServiceImp extends BaseService implements AppStatisticsService {

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM (SELECT t1.app_id, DATE_FORMAT(t1.ctime,'%Y-%m-%d') cdate, t2.name app_name, count(t1.id) count, t1.batch_id, t3.name pname FROM t_down_complete_log t1 LEFT JOIN t_app t2 ON t1.app_id = t2.id LEFT JOIN t_device_batch t3 ON t1.batch_id = t3.batch_id GROUP BY t1.app_id, t1.batch_id, date(t1.ctime)) t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("bdate"))) {
			sql.append(" AND t1.cdate >= ?");
			plist.add(para.get("bdate"));
		}
		if (mu.isNotBlank(para.get("edate"))) {
			sql.append(" AND t1.cdate <= ?");
			plist.add(para.get("edate"));
		}
		if (mu.isNotBlank(para.get("appName"))) {
			sql.append(" AND t1.app_name LIKE ?");
			plist.add("%" + para.get("appName") + "%");
		}
		if (mu.isNotBlank(para.get("pname"))) {
			sql.append(" AND t1.pname LIKE ?");
			plist.add("%" + para.get("pname") + "%");
		}
		if (mu.isNotBlank(para.get(""))) {
			sql.append(" AND t1.develope LIKE ?");
			plist.add("%" + para.get("develope") + "%");
		}
		if (mu.isNotBlank(para.get("batchId"))) {
			sql.append(" AND t1.batch_id LIKE ?");
			plist.add("%" + para.get("batchId") + "%");
		}
		pb.setTotalNum(this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.app_id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.* ");
		List<Map<String, Object>> rList = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

}
