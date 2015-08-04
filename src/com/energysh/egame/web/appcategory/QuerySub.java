package com.energysh.egame.web.appcategory;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class QuerySub extends BaseController {

	public ModelAndView excute() {
		try {
			return this.setJsonResult(this.getAppCategoryService().queryCategoryByFatherId(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}