package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.po.appstore.TPartnersIncomeConf;
import com.energysh.egame.util.MyUtil;
import com.energysh.egame.util.PageBar;

public class PartnersIncomeConfServiceImp extends BaseService implements PartnersIncomeConfService {

	public PageBar query(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		StringBuilder sql = new StringBuilder("FROM sso.permit_user t1 LEFT JOIN t_partners_income_conf t2 ON t1.user_id = t2.user_id WHERE t1.user_type = 4");
		PageBar pb = new PageBar(para);
		List<Object> plist = new ArrayList<Object>();
		if (mu.isNotBlank(para.get("nickname"))) {
			sql.append(" AND t1.nickname LIKE ?");
			plist.add("%" + para.get("nickname") + "%");
		}
		pb.setTotalNum(this.getAppstoreDao().findIntBySql("SELECT COUNT(t1.user_id) " + sql, plist.toArray()));
		if (pb.getTotalNum() == 0)
			return pb;
		sql.insert(0, "SELECT IFNULL(t2.id, 0) id, t1.user_id, t1.nickname, IFNULL(t2.cpa_price, 0) cpa_price, IFNULL(t2.cps_divide_ratio, 0) cps_divide_ratio, IFNULL(t2.cpa_price_nextday, 0) cpa_price_nextday, IFNULL(t2.cps_divide_ratio_nextday, 0) cps_divide_ratio_nextday ");
		List<Map<String, Object>> rList = this.getAppstoreDao().findListMapPageBySql(sql.toString(), plist.toArray(), pb);
		pb.setResultList(rList);
		return pb;
	}

	@Override
	public Map<String, String> up(Map<String, String> para) throws Exception {
		Map<String, String> rmap = new HashMap<String, String>();
		MyUtil mu = MyUtil.getInstance();
		int userId = mu.toInt(para.get("userId"));
		if (userId == 0) {
			rmap.put("info", "fail");
			return rmap;
		}
		int id = mu.toInt(para.get("id"));
		TPartnersIncomeConf po = null;
		if (id == 0) {
			po = new TPartnersIncomeConf();
			po.setUserId(userId);
			po.setCpaPrice(0D);
			po.setCpsDivideRatio(0D);
		} else {
			po = (TPartnersIncomeConf) this.getAppstoreDao().findObject("FROM TPartnersIncomeConf t1 WHERE t1.id = ?", new Object[] { id });
		}
		po.setCpaPriceNextDay(mu.toDouble(para.get("cpaPriceNextDay")));
		po.setCpsDivideRatioNextDay(mu.toDouble(para.get("cpsDivideRatioNextDay")));
		this.getAppstoreDao().saveOrUpdate(po);
		rmap.put("info", "ok");
		return rmap;
	}

	@Override
	public TPartnersIncomeConf get(Map<String, String> para) throws Exception {
		MyUtil mu = MyUtil.getInstance();
		int id = mu.toInt(para.get("id"));
		TPartnersIncomeConf vo = new TPartnersIncomeConf();
		if (id == 0) {
			int userId = mu.toInt(para.get("userId"));
			String nickname = this.getAppstoreDao().findStringBySql("SELECT t1.nickname FROM sso.permit_user t1 WHERE t1.user_id = ?", new Object[] { userId });
			vo.setUserId(userId);
			vo.setNickname(nickname);
			vo.setCpaPrice(0D);
			vo.setCpsDivideRatio(0D);
			vo.setCpaPriceNextDay(0D);
			vo.setCpsDivideRatioNextDay(0D);
		} else {
			Map<String, Object> rmap = this.getAppstoreDao().findMapBySql("SELECT IFNULL(t1.id, 0) id, t1.user_id, t2.nickname, IFNULL(t1.cpa_price, 0) cpa_price, IFNULL(t1.cps_divide_ratio, 0) cps_divide_ratio, IFNULL(t1.cpa_price_nextday, 0) cpa_price_next_day, IFNULL(t1.cps_divide_ratio_nextday, 0) cps_divide_ratio_next_day, t1.ctime FROM t_partners_income_conf t1 LEFT JOIN sso.permit_user t2 ON t1.user_id = t2.user_id WHERE t1.id = ?", new Object[] { id });
			mu.map2Object(rmap, vo);
		}
		return vo;
	}

}
