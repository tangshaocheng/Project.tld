package com.energysh.egame.web.pubuserincome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class GetList extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			List<Map<String, Object>> rlist = this.getUserIncomeService().queryList(para);
			Map<String, Object> rmap = new HashMap<String, Object>();
			rmap.put("lastDate", para.get("lastDate"));
			rmap.put("rlist", rlist);
			return this.setJsonResultObj(rmap);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}