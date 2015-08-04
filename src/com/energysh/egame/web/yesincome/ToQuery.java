package com.energysh.egame.web.yesincome;

import java.util.Calendar;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.MyUtil;
import com.energysh.egame.web.BaseController;

public class ToQuery extends BaseController {

	public ModelAndView excute() {
		try {
			String yTime = MyUtil.getInstance().addTime(new Date(), -1, Calendar.DAY_OF_MONTH);
			String yDate = MyUtil.getInstance().formateDate(yTime, "yyyy-MM-dd");
			this.getRequest().setAttribute("yDate", yDate);
			return new ModelAndView(this.getJsp());
		} catch (Exception e) {
			return this.error(e);
		}
	}
}