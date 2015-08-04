package com.energysh.egame.web.app;

import java.io.PrintWriter;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.Constants;
import com.energysh.egame.util.UploadHepler;
import com.energysh.egame.web.BaseController;

public class Up extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> rmap = this.getAppService().up(
					UploadHepler.saveFileAndGetPara(this.getRequest(),
							Constants.FUN_APP));
			PrintWriter printWriter = this.getResponse().getWriter();
			printWriter.print("<script type='text/javascript'>parent.backUp_('"
					+ rmap.get("info") + "');</script>");
			printWriter.close();
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}
}
