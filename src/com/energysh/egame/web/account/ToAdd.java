package com.energysh.egame.web.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class ToAdd extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> paras = new HashMap<String, String>();
			paras.put("userType", "3");
			List<Map<String, Object>> plist = this.getAccountService().queryBatch(paras);
			paras.put("userType", "4");
			List<Map<String, Object>> clist = this.getAccountService().queryBatch(paras);
			this.getRequest().setAttribute("plist", JSONArray.fromObject(plist));
			this.getRequest().setAttribute("clist", JSONArray.fromObject(clist));
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
