package com.energysh.egame.web.app;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.web.BaseController;

public class Get extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			para.put("categoryId", "2");
			List<TAppCategory> categoryList = this.getAppCategoryService().queryCategoryByLevel(para);
			this.getRequest().setAttribute("categoryList", categoryList);
			return this.setJsonResult(this.getAppService().get(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}