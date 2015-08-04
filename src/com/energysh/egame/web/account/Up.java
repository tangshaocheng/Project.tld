package com.energysh.egame.web.account;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Up extends BaseController {

	public ModelAndView excute() {
		try {
			return this.setJsonResult(this.getAccountService().up(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}
