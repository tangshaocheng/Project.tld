package com.energysh.egame.web.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.energysh.egame.po.sso.PermitUser;
import com.energysh.egame.web.BaseController;

public class MenuTree extends BaseController {

	public ModelAndView excute() {
		try {
			Map<String, String> para = this.getAjaxPara();
			PermitUser user = (PermitUser) this.getRequest().getSession().getAttribute("energyUserBms");
			List<Map<String, Object>> urlList = user.getUrlList();
			Map<String, List<Map<String, Object>>> urlMap = new HashMap<String, List<Map<String, Object>>>();
			String key = "";
			for (Map<String, Object> url : urlList) {
				if (url.get("type").equals("BUTTON"))
					continue;
				key = url.get("parent_url_id").toString();
				if (urlMap.containsKey(key)) {
					urlMap.get(key).add(url);
				} else {
					List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
					tlist.add(url);
					urlMap.put(key, tlist);
				}
			}
			String ssoUrl = "", jessionId = "";
			StringBuilder tree = new StringBuilder("");
			if (user.getProjectList() != null)
				for (Map<String, Object> project : user.getProjectList()) {
					tree.append("<div class='u6'><span>").append(project.get("project_name").toString()).append("</span></div>");
					tree.append(buildMenuTreeNew("0", para.get("groupId"), project, urlMap, ssoUrl, jessionId));
				}
			para.put("tree", tree.toString());
			this.setJsonResult(para);
		} catch (Exception e) {
			return this.errorAjax(e);
		}
		return null;
	}

	/**
	 * 功能：新界面递归显示菜单
	 */
	private String buildMenuTreeNew(String permitId, String goupId, Map<String, Object> project, Map<String, List<Map<String, Object>>> urlMap, String ssoUrl, String jessionId) {
		StringBuilder treeStr = new StringBuilder("");
		List<Map<String, Object>> pulist = urlMap.get(permitId);
		if (null == pulist)
			return "";
		for (Map<String, Object> sp : pulist) {
			if (!sp.get("project_id").toString().equals(project.get("project_id").toString()))
				continue;
			if (urlMap.containsKey(sp.get("url_id").toString())) {
				treeStr.append("<div class='u7'><div id='").append(sp.get("url_id").toString());
				treeStr.append("' hide='0' class='u10_img' style=\"background-image: url('").append(project.get("project_prefix_url")).append("/img/new/left_files/");
				treeStr.append(sp.get("url_id").toString()).append("_menu.png');\"></div></div>");
				treeStr.append(buildMenuTreeNew(sp.get("url_id").toString(), goupId, project, urlMap, ssoUrl, jessionId));
			} else {
				if (null != sp.get("url") && !sp.get("url").equals("")) {
					treeStr.append("<div fatherid='").append(sp.get("parent_url_id")).append("' class='u12'>");
					treeStr.append("<div class='u15_img'>");
					treeStr.append("<a href='").append(project.get("project_prefix_url")).append(sp.get("url")).append("' target='rightFrame'>");
					treeStr.append("<img id='").append(sp.get("url_id")).append("' src='").append(project.get("project_prefix_url")).append("/img/new/left_files/");
					treeStr.append(sp.get("url_id")).append("_menu.png' class='raw_image' title='");
					treeStr.append(sp.get("url_name"));
					treeStr.append("'></a></div></div>");
				} else {
					treeStr.append("<div class='u7'><div id='").append(sp.get("url_id").toString());
					treeStr.append("' hide='0' class='u10_img' style=\"background-image: url('").append(project.get("project_prefix_url")).append("/img/new/left_files/");
					treeStr.append(sp.get("url_id").toString()).append("_menu.png');\"></div></div>");
				}
			}
		}
		return treeStr.toString();
	}
}
