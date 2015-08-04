package com.energysh.egame.web.appthemebagsort.sx;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppThemeBagSort;
import com.energysh.egame.web.BaseController;

public class ToUp extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> map = this.getPara();
			TAppThemeBagSort vo = this.getAppThemeBagSortSxService().get(map);
			this.getRequest().setAttribute("vo", vo);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
