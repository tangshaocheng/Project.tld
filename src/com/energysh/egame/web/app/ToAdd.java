package com.energysh.egame.web.app;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.po.appstore.TDeviceBatch;
import com.energysh.egame.web.BaseController;

public class ToAdd extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			para.put("categoryId", "2");
			List<TAppCategory> categoryList = this.getAppCategoryService()
					.queryCategoryByLevel(para);
			List<TDeviceBatch> batchList = this.getGenBatchService().findAll();
			this.getRequest().setAttribute("batchList", batchList);
			this.getRequest().setAttribute("categoryList", categoryList);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
