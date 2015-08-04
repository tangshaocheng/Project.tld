package com.energysh.egame.web.app;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TApp;
import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.po.appstore.TDeviceBatch;
import com.energysh.egame.web.BaseController;

public class ToUp extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			TApp vo = this.getAppService().get(para);
			para.put("categoryId", "2");
			List<TAppCategory> categoryList = this.getAppCategoryService()
					.queryCategoryByLevel(para);
			List<TDeviceBatch> batchList = this.getGenBatchService().findAll();
			this.getRequest().setAttribute("batchList", batchList);
			this.getRequest().setAttribute("vo", vo);
			this.getRequest()
					.setAttribute("appBatchList", vo.getAppBatchList());
			this.getRequest().setAttribute("appBatchListSize",
					vo.getAppBatchList().size());
			this.getRequest().setAttribute("categoryList", categoryList);
			this.getRequest().setAttribute("currentPageNum",
					para.get("currentPageNum"));
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
