package com.energysh.egame.web.genbatch;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class Query extends BaseController {

	public ModelAndView excute() {
		try {
			return this.setJsonResult(this.getGenBatchService().query(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}