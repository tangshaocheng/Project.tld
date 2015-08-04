package com.energysh.egame.web.apptheme;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppTheme;
import com.energysh.egame.web.BaseController;

public class ToUp extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> map = this.getPara();
			TAppTheme vo = this.getAppThemeService().get(map);
			this.getRequest().setAttribute("vo", vo);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
