package com.energysh.egame.web;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.web.BaseController;

public class DownloadAppListCtrl extends BaseController {

	public ModelAndView excute() {
		try {
			return this.setJsonResultObj(this.getDownloadAppListService().query(this.getAjaxPara()));
		} catch (Exception e) {
			return this.errorAjax(e);
		}
	}
}
