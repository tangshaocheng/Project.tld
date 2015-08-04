package com.energysh.egame.web.appcategory;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppThemeBag;
import com.energysh.egame.web.BaseController;

public class ToUpCategoryGood extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> map = this.getPara();
			TAppThemeBag vo = this.getAppThemeBagService().getCategoryGood(map);
			this.getRequest().setAttribute("categoryId", map.get("id"));
			this.getRequest().setAttribute("vo", vo);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
