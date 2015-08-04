package com.energysh.egame.web.login;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Welcome extends BaseController {

	public ModelAndView excute() {
		try {
			Object energyUser = this.getRequest().getSession().getAttribute("energyUserBms");
			if (energyUser == null) {
				return new ModelAndView("/login");
			}
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}