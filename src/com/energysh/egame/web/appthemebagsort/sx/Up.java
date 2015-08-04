package com.energysh.egame.web.appthemebagsort.sx;

import java.io.PrintWriter;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Up extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> rmap = this.getAppThemeBagSortSxService().up(this.getAjaxPara());
			PrintWriter printWriter = this.getResponse().getWriter();
			printWriter.print("<script type='text/javascript'>parent.backUp_('" + rmap.get("info") + "');</script>");
			printWriter.close();
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}
}
