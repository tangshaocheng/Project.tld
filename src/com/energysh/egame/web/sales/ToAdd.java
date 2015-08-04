package com.energysh.egame.web.sales;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.appstore.TDeviceBatch;
import com.energysh.egame.web.BaseController;

public class ToAdd extends BaseController {

	public ModelAndView excute() {
		try {
			List<TDeviceBatch> rlist = this.getGenBatchService().findAll();
			this.getRequest().setAttribute("v", rlist);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}
