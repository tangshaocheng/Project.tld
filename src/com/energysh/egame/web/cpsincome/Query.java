package com.energysh.egame.web.cpsincome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.web.BaseController;

public class Query extends BaseController {

	public ModelAndView excute() {
		try {
			MyUtil mu = MyUtil.getInstance();
			Map<String, String> para = this.getAjaxPara();
			List<Map<String, Object>> rlist = this.getCpsIncomeService().queryList(para);
			Map<String, Object> rmap = new HashMap<String, Object>();
			int appId = mu.isBlank(para.get("appId")) ? 0 : mu.toInt(para.get("appId"));
			rmap.put("appId", appId);
			rmap.put("lastDate", para.get("lastDate"));
			rmap.put("rlist", rlist);
			return this.setJsonResultObj(rmap);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}