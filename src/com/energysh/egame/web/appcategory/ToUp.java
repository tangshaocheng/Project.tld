package com.energysh.egame.web.appcategory;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TAppCategory;
import com.energysh.egame.web.BaseController;

public class ToUp extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> map = this.getPara();
			TAppCategory vo = this.getAppCategoryService().get(map);
			String fatherName = "";
			if (vo.getFatherId() > 0) {
				TAppCategory ac = this.getAppCategoryService().queryById(vo.getFatherId());
				if (ac.getFatherId() > 0)
					fatherName = this.getAppCategoryService().queryById(ac.getFatherId()).getName() + " >> " + ac.getName();
				else
					fatherName = ac.getName();
			}
			this.getRequest().setAttribute("vo", vo);
			this.getRequest().setAttribute("fatherName", fatherName);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
