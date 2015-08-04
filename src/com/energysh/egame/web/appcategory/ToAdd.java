package com.energysh.egame.web.appcategory;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.web.BaseController;

public class ToAdd extends BaseController {

	public ModelAndView excute() {
		try {
			List<TAppCategory> categoryList = this.getAppCategoryService().queryCategoryByFatherId(this.getAjaxPara());
			this.getRequest().setAttribute("categoryList", categoryList);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
