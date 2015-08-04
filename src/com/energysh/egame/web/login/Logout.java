package com.energysh.egame.web.login;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Logout extends BaseController {

	public ModelAndView excute() {
		try {
			this.getRequest().getSession().removeAttribute("energyUserBms");
			this.getRequest().getSession().invalidate();
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}