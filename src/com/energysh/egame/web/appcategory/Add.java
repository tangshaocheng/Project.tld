package com.energysh.egame.web.appcategory;

import java.io.PrintWriter;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.Constants;
import com.energysh.egame.util.UploadHepler;
import com.energysh.egame.web.BaseController;

public class Add extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> rmap = this.getAppCategoryService().add(UploadHepler.saveFileAndGetPara(this.getRequest(), Constants.FUN_CATEGORY));
			PrintWriter printWriter = this.getResponse().getWriter();
			printWriter.print("<script type='text/javascript'>parent.backAdd_('" + rmap.get("info") + "');</script>");
			printWriter.close();
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}
}
