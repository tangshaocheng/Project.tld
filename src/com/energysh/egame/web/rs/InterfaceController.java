package com.energysh.egame.web.rs;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.exception.AppBizException;
import com.energysh.egame.exception.AppExcCodes;
import com.energysh.egame.service.InterfaceService;
import com.energysh.egame.util.MarketUtils;
import com.energysh.egame.util.ReqFun;
import com.energysh.egame.util.Servlets;
import com.energysh.egame.web.BaseController;

public class InterfaceController extends BaseController {

	private static final Logger LOGGER = Logger
			.getLogger(InterfaceController.class);

	public ModelAndView excute() {
		try {
			this.api(this.getRequest(), this.getResponse());
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}

	public String api(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		StringBuilder loggerStr = new StringBuilder(" request is :"
				+ request.getQueryString());
		response.setContentType("text/plain;charset=utf-8");
		String res = "";
		String method = request.getParameter("method");
		try {
			if (StringUtils.isBlank(method))
				throw new AppBizException(AppExcCodes.E_INVALID_REQUEST,
						"request method:" + method);
			Map<String, String> para = Servlets.getHttpGetMethodPara(this
					.getRequest());
			if (ReqFun.FUN_EGAME_VERSION_CHECK.equals(method)) { // 检查平台最新版本
				res = interfaceService.checkPlatFormVersion(para);
			} else if (ReqFun.FUN_APP_INSTALL_LIST_SYNC.equals(method)) { // 同步已安装应用列表
				String postContent = Servlets.getHttpPostContent(request);
				para.put("postContent", postContent);
				res = interfaceService.appInstalledListSync(para);
			} else if (ReqFun.FUN_RECOMMEND_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService
						.queryRecommendList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService.queryAppList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_THEME_PIC_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService
						.queryThemePicList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_COMMENT_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService
						.queryCommentList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_ADD_COMMENT.equals(method)) {
				para.putAll(Servlets.getJsonPara(this.getRequest()));
				Map<String, Object> rmap = interfaceService.addComment(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_GET_RANK.equals(method)) {
				Map<String, Object> rmap = interfaceService.getRank(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_NEAR_ME_APP.equals(method)) {
				Map<String, Object> rmap = interfaceService.nearmeApp(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_SEARCH_APP.equals(method)) {
				Map<String, Object> rmap = interfaceService.searchApp(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_GET_CATEGORY.equals(method)) {
				Map<String, Object> rmap = interfaceService.getCategory(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_GAME_CENTER_APP_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService
						.gameCenterAppList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_GET_CATEGORY_INFO.equals(method)) {
				Map<String, Object> rmap = interfaceService
						.getCategoryInfo(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_APP_GET_RELATION_APP_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService
						.getRelationAppList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_SXJP_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService.querySxjpList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (ReqFun.FUN_GET_SX_APP_LIST.equals(method)) {
				Map<String, Object> rmap = interfaceService.getSxAppList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (method.equals(ReqFun.FUN_PUT_XG_PUSH_TOKEN)) {// 乐七SDK信鸽token获取
				res = interfaceService.putXgPushToken(para);
			} else if (method.equals(ReqFun.FUN_APP_INSTALL_COMPLETE)) {
				res = interfaceService.AppInstallComplete(para);
			} else if (method.equals(ReqFun.FUN_DOWN_APP_COMPLETE)) {
				res = interfaceService.downAppComplete(para);
			} else if (method.equals(ReqFun.FUN_HOT_SEARCH_APP)) {
				Map<String, Object> rmap = interfaceService.hotSearchApp(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (method.equals(ReqFun.FUN_AD_APP_LIST)) {
				Map<String, Object> rmap = interfaceService.adAppList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (method.equals(ReqFun.FUN_PRE_DOWN_APP_LIST)) {
				Map<String, Object> rmap = interfaceService
						.preDownAppList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (method.equals(ReqFun.FUN_DISCOVERY_LIST)) {
				Map<String, Object> rmap = interfaceService.discoveryList(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (method.equals(ReqFun.FUN_GET_AD_CONF)) {
				Map<String, Object> rmap = interfaceService.getAdConf(para);
				res = JSONObject.fromObject(rmap).toString();
			} else if (method.equals(ReqFun.FUN_JM_SWITCH)) {
				Map<String, Object> rmap = interfaceService.jmSwitch(para);
				res = JSONObject.fromObject(rmap).toString();
			} else {
				LOGGER.info(loggerStr);
				throw new AppBizException(AppExcCodes.E_INVALID_REQUEST,
						"request method:" + method);
			}
		} catch (AppBizException e) {
			LOGGER.error(e);
			res = MarketUtils.getErrorCodeJson(e.getCode());
		} catch (DataAccessException e) {
			LOGGER.error(e);
			res = MarketUtils.getErrorJson(AppExcCodes.E_SYS_DB);
		} catch (Throwable e) {
			LOGGER.error(e);
			res = MarketUtils.getErrorJson(AppExcCodes.E_FAIL);
		}
		loggerStr.append(" ==>>loginServerResponse:" + res);
		LOGGER.info(loggerStr);

		response.getWriter().write(res);
		response.getWriter().flush();

		return null;
	}

	private InterfaceService interfaceService;

	public void setInterfaceService(InterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}

}
