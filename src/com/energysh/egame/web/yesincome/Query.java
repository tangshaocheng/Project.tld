package com.energysh.egame.web.yesincome;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.sso.PermitUser;
import com.energysh.egame.web.BaseController;

public class Query extends BaseController {

	public ModelAndView excute() {
		try {
			if (StringUtils.contains(this.getRequest().getRequestURL() + "", "_out.htm")) {
				Object energyUser = this.getRequest().getSession().getAttribute("energyUserBms");
				if (energyUser == null) {
					this.getRequest().getRequestDispatcher("/WEB-INF/jsp/dispatcher.jsp").forward(this.getRequest(), this.getResponse());
					return null;
				}
				Map<String, String> paras = this.getAjaxPara();
				paras.put("userId", ((PermitUser) energyUser).getUserId() + "");
				return this.setJsonResult(this.getYesIncomeService().query(paras));
			} else {
				return this.setJsonResult(this.getYesIncomeService().queryList(this.getAjaxPara()));
			}
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}