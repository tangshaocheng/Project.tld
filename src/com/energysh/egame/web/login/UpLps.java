package com.energysh.egame.web.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.sso.PermitUser;
import com.energysh.egame.web.BaseController;

public class UpLps extends BaseController {

	public ModelAndView excute() {
		try {
			Object energyUser = this.getRequest().getSession().getAttribute("energyUserBms");
			if (energyUser == null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("info", "isUser");
				this.setJsonResult(map);
				return null;
			}
			PermitUser permitUser = (PermitUser) energyUser;
			Map<String, String> para = this.getAjaxPara();
			para.put("energyUserId", permitUser.getUserId().toString());
			Map<String, String> rmap = this.getLoginService().upPassWord(para);
			this.setJsonResult(rmap);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}

}
