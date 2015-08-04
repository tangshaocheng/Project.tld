package com.energysh.egame.web.login;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.util.Constants;
import com.energysh.egame.web.BaseController;
import com.energysh.sso.util.SSOUtil;

public class Login extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			Map<String, Object> returnMap = this.getLoginService().queryUserBms(para);
			if (null == returnMap.get("user")) {
				returnMap.put("info", "isexist");
				this.setJsonResultObj(returnMap);
				return null;
			}
			if (!SSOUtil.getInstance().isNullEmpty(para.get("verifyCode"))) {
				String verifyCode = para.get("verifyCode");
				if (!StringUtils.equalsIgnoreCase(verifyCode, (String) this.getRequest().getSession().getAttribute(Constants.REGISTER_VERIFYCODE_KEY))) {
					returnMap.put("info", "isverify");
					this.setJsonResultObj(returnMap);
					return null;
				}
			}
			this.getRequest().getSession().setAttribute("energyUserBms", returnMap.get("user"));
			returnMap.put("info", "ok");
			this.setJsonResultObj(returnMap);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}
}
