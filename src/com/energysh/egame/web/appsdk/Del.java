package com.energysh.egame.web.appsdk;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Del extends BaseController {

	public ModelAndView excute() {
		try {
			return this.setJsonResult(this.getAppSdkService().del(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}
