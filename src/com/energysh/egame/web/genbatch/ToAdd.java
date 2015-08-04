package com.energysh.egame.web.genbatch;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class ToAdd extends BaseController {

	public ModelAndView excute() {
		try {
			String maxBatchId = this.getGenBatchService().nextBatchId();
			this.getRequest().setAttribute("maxBatchId", maxBatchId);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
