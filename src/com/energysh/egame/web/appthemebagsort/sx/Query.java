package com.energysh.egame.web.appthemebagsort.sx;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Query extends BaseController {

	public ModelAndView excute() {
		try {
			// return this.setJsonResult(this.getAppThemeBagSortService().query(this.getAjaxPara()));
			return this.setJsonResultObj(this.getAppThemeBagSortSxService().query(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}