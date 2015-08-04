package com.energysh.egame.web.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class GetList extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = this.getRequest().getParameter("query");
			if (null == query)
				query = "";
			try {
				query = new String(query.getBytes("ISO-8859-9"), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, String> paras = this.getAjaxPara();
			paras.put("nickname", query);
			map.put("query", query);
			map.put("suggestions", this.getAccountService().getList(paras));
			return this.setJsonResultObj(map);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}