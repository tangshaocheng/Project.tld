package com.energysh.egame.web.appthemebagsort.sx;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class ToQuery extends BaseController {

	public ModelAndView excute() {
		try {
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}