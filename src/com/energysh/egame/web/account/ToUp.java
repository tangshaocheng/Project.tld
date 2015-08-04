package com.energysh.egame.web.account;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class ToUp extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, Object> vo = this.getAccountService().get(this.getPara());
			this.getRequest().setAttribute("vo", vo);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
