package com.energysh.egame.web.appthemebagsort.sx;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Sort extends BaseController {

	public ModelAndView excute() {
		try {
			return this.setJsonResult(this.getAppThemeBagSortSxService().sort(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}
