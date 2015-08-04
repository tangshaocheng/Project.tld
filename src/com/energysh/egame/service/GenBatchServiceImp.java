package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.energysh.egame.po.appstore.TDeviceBatch;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class GenBatchServiceImp extends BaseService implements GenBatchService {

	@Override
	public PageBar query(Map<String, String> para) throws Exception {
		PageBar pb = new PageBar(para);
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM t_device_batch t1 WHERE 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("name"))) {
			sql.append(" AND t1.name LIKE ?");
			plist.add("%" + para.get("name") + "%");
		}
		if (mu.isNotBlank(para.get("batchId"))) {
			sql.append(" AND t1.batch_id LIKE ?");
			plist.add("%" + para.get("batchId") + "%");
		}
		if (mu.isNotBlank(para.get("remark"))) {
			sql.append(" AND t1.remark LIKE ?");
			plist.add("%" + para.get("remark") + "%");
		}
		pb.setTotalNum(this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.batch_id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT t1.* ");
		sql.append(" order by t1.ctime desc");
		List<Map<String, Object>> rList = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> add(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		String name = para.get("name");
		String batchId = para.get("batchId");
		String remark = para.get("remark");
		char a = 6;
		MyUtil mu = MyUtil.getInstance();
		String[] names = StringUtils.split(name, a);
		String[] batchIds = StringUtils.split(batchId, a);
		String[] remarks = StringUtils.split(remark, a);
		String batchs = "";
		for (int i = 0; i < batchIds.length; i++) {
			// String id = this.getAppstoreDao().findStringBySql("SELECT t1.batch_id FROM t_device_batch t1 WHERE t1.name = ?", new Object[] { names[0] });
			// if (mu.isNotBlank(id)) {
			// batchs += "[name = " + names[i] + "]";
			// continue;
			// }
			Object num = this.getAppstoreDao().excuteBySql("INSERT IGNORE INTO t_device_batch(name, batch_id, remark, ctime) VALUES('" + names[0] + "', '" + batchIds[i] + "','" + remarks[i] + "', now())");
			if (mu.toInt(((Integer) num).toString()) == 0) {
				batchs += "[batchId = " + batchIds[i] + "]";
			}
		}
		rmap.put("batchs", batchs);
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public String nextBatchId() throws Exception {
		MyUtil mu = MyUtil.getInstance();
		// String max = this.getAppstoreDao().findStringBySql("SELECT MAX(t1.batch_id) FROM t_device_batch t1", new Object[] {});
		String maxStr = this.getAppstoreDao().findStringBySql("SELECT max(SUBSTR(batch_id,LOCATE('_M',batch_id)+2)) FROM t_device_batch", new Object[] {});
		// String maxStr = StringUtils.substringAfterLast(max, "_M");
		int maxInt = 100000;
		if (mu.isNotBlank(maxStr))
			maxInt = mu.toInt(maxStr) + 1;
		return mu.formateDate(new Date(), "yyyyMMddHHmmssSSS") + "_M" + maxInt;
	}

	@SuppressWarnings("unchecked")
	public List<TDeviceBatch> findAll() throws Exception {
		List<TDeviceBatch> rlist = this.getAppstoreDao().findListBySql("SELECT t1.* FROM t_device_batch t1", null, TDeviceBatch.class);
		return rlist;
	}

}
