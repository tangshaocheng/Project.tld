package com.energysh.egame.web.pubuserincome;

import java.util.Calendar;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.web.BaseController;

public class ToQuery extends BaseController {

	public ModelAndView excute() {
		try {
			MyUtil mu = MyUtil.getInstance();
			String yestDate = mu.formateDate(mu.addTime(new Date(), -1, Calendar.DAY_OF_MONTH), "yyyy-MM-dd");
			this.getRequest().setAttribute("yestDate", yestDate);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}