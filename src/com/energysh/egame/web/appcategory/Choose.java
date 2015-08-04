package com.energysh.egame.web.appcategory;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Choose extends BaseController {

	public ModelAndView excute() {
		try {
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
