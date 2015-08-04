package com.energysh.egame.web;

import org.springframework.web.servlet.ModelAndView;

public class RedirectControl extends BaseController {

	@Override
	public ModelAndView excute() {
		return new ModelAndView(this.getJsp(), "model", null);
	}
}
