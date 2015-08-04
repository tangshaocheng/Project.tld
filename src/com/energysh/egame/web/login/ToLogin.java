package com.energysh.egame.web.login;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class ToLogin extends BaseController {

	public ModelAndView excute() {
		try {
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}