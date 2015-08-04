package com.energysh.egame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.energysh.egame.po.sso.PermitUser;
import com.energysh.sso.util.SSOUtil;

public class LoginServiceImp extends BaseService implements LoginService {

	/**
	 * 查询用户
	 */
	public Map<String, Object> queryUserBms(Map<String, String> para) throws Exception {
		StringBuilder hsql = new StringBuilder(" from PermitUser where 1=1 ");
		SSOUtil mu = SSOUtil.getInstance();
		List<Object> plist = new ArrayList<Object>();
		hsql.append(" and lname = ?");
		String username = para.get("lname");
		plist.add(username);
		if (username.equals("admin")) {
			hsql.append(" and userType = 1");
		} else {
			hsql.append(" and userType IN (2, 3, 4)");
		}
		hsql.append(" and lps=?");
		String password = mu.getMD5Str(para.get("lps"));
		plist.add(password);
		Map<String, Object> rMap = new HashMap<String, Object>();
		PermitUser user = (PermitUser) this.getSsoDao().findObject(hsql.toString(), plist.toArray());
		if (null == user)
			return rMap;
		rMap.put("user", user);
		List<Map<String, Object>> allUrlList = this.getSsoDao().findListMapBySql("SELECT * FROM permit_url a left join permit_project b on a.project_id=b.project_id where b.user_type=2", null);
		List<String> chekcedUrlList = new ArrayList<String>();
		for (Map<String, Object> url : allUrlList) {
			if (null == url.get("url"))
				continue;
			chekcedUrlList.add(url.get("url").toString());
		}
		user.setChekcedUrlList(chekcedUrlList);
		if (user.getLname().equals("admin")) {// 管理员权限
			List<Map<String, Object>> projectList = this.getSsoDao().findListMapBySql("SELECT * FROM permit_project where user_type=2", null);
			user.setProjectList(projectList);
			user.setUrlList(allUrlList);
			user.setCpId(0);// andmin设置cpId为0
			rMap.put("cpId", 0);
			return rMap;
		}
		// 查询用户所属用户组
		List<Map<String, Object>> pugList = this.getSsoDao().findListMapBySql("SELECT a.group_id,b.group_name FROM permit_user_group a,permit_group b where a.group_id=b.group_id and a.user_id=? and b.user_type=?", new Object[] { user.getUserId(), user.getUserType() });
		user.setGroupList(pugList);
		// 先查询用户所在组的权限URL
		List<Map<String, Object>> groupUrlList = this.getSsoDao().findListMapBySql("SELECT distinct a.group_id,c.* FROM permit_url_group a inner join permit_user_group b on a.group_id=b.group_id  inner join permit_url c on a.url_id=c.url_id " + "left join permit_group d on b.group_id=d.group_id  where b.user_id=? and d.user_type=?", new Object[] { user.getUserId(), user.getUserType() });
		// 再查询用户独有的权限URL
		List<Map<String, Object>> userUrlList = this.getSsoDao().findListMapBySql("SELECT * FROM permit_url where url_id=ANY(select url_id from permit_url_user where user_id=?)", new Object[] { user.getUserId() });
		List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
		urlList.addAll(groupUrlList);
		urlList.addAll(userUrlList);
		// 去重重复权限URL
		List<Map<String, Object>> newUrlList = new ArrayList<Map<String, Object>>();
		List<Integer> urlIdList = new ArrayList<Integer>();
		int urlId = 0;
		for (Map<String, Object> map : urlList) {
			urlId = Integer.valueOf(map.get("url_id").toString());
			if (urlIdList.contains(urlId))
				continue;
			newUrlList.add(map);
			urlIdList.add(urlId);
		}
		urlIdList.clear();
		urlList.clear();
		user.setUrlList(newUrlList);
		Map<String, String> projectMap = new HashMap<String, String>();// 用户可以操作的项目
		for (Map<String, Object> gu : groupUrlList) {
			projectMap.put(gu.get("project_id").toString(), "");
		}
		for (Map<String, Object> uu : userUrlList) {
			projectMap.put(uu.get("project_id").toString(), "");
		}
		if (projectMap.isEmpty())
			return rMap;
		// 生成用户操作的左边菜单
		StringBuilder projectIds = new StringBuilder("(");// 可以操作的项目ID
		for (Object projectId : projectMap.keySet().toArray()) {
			projectIds.append(projectId).append(",");
		}
		projectIds.deleteCharAt(projectIds.length() - 1).append(")");
		List<Map<String, Object>> projectList = this.getSsoDao().findListMapBySql("SELECT * FROM permit_project where user_type=? and project_id in " + projectIds, new Object[] { user.getUserType() });
		user.setProjectList(projectList);
		return rMap;
	}

	/**
	 * 查询用户
	 */
	public Map<String, Object> queryUser(Map<String, String> para) throws Exception {
		StringBuilder hsql = new StringBuilder(" from PermitUser  where 1=1 ");
		SSOUtil mu = SSOUtil.getInstance();
		List<Object> plist = new ArrayList<Object>();
		if (!mu.isNullEmpty(para.get("lname"))) {
			hsql.append(" and lname = ?");
			plist.add(para.get("lname"));
			if (para.get("lname").equals("admin")) {
				hsql.append(" and userType = 1");
			} else {
				hsql.append(" and userType IN (2, 3, 4)");
			}
		}
		if (!mu.isNullEmpty(para.get("lps"))) {
			hsql.append(" and lps=?");
			plist.add(mu.getMD5Str(para.get("lps")));
		}
		Map<String, Object> rMap = new HashMap<String, Object>();
		PermitUser user = (PermitUser) this.getSsoDao().findObject(hsql.toString(), plist.toArray());
		if (null == user)
			return rMap;
		int cpId = this.getSsoDao().findIntBySql("select id from pay_base.t_customer where user_id=?", new Object[] { user.getUserId() });
		user.setCpId(cpId);
		rMap.put("user", user);
		return rMap;
	}

	/**
	 * 密码修改
	 */
	public Map<String, String> upPassWord(Map<String, String> para) throws Exception {
		Map<String, String> rMap = new HashMap<String, String>();
		PermitUser pu = (PermitUser) this.getSsoDao().findObject("from PermitUser where userId=?", new Object[] { Integer.parseInt(para.get("energyUserId")) });
		SSOUtil mu = SSOUtil.getInstance();
		if (!pu.getLps().equals(mu.getMD5Str(para.get("oldps")))) {
			rMap.put("info", "old");
			return rMap;
		}
		pu.setLps(mu.getMD5Str(para.get("ps")));
		this.getSsoDao().update(pu);
		rMap.put("info", "ok");
		return rMap;
	}

	/**
	 * 获取用户
	 */
	public Map<String, Object> getUser(Map<String, String> para) throws Exception {
		Map<String, Object> rMap = new HashMap<String, Object>();
		PermitUser pu = (PermitUser) this.getSsoDao().findObject("from PermitUser where userId=?", new Object[] { Integer.parseInt(para.get("userId")) });
		rMap.put("user", pu);
		return rMap;
	}

	/**
	 * 功能：添加操作日志
	 * 
	 * @author xinwang.xu
	 * @date 2013-6-25 下午02:32:49
	 * @param para
	 * @throws Exception
	 */
	public void addLogs(Map<String, String> para) throws Exception {
		String[] urls = para.get("url").split("[/]");
		String url = urls[urls.length - 2] + "/" + urls[urls.length - 1];
		Map<String, Object> projectMap = this.getSsoDao().findMapBySql("select * from permit_operation where operation_url like ? ", new Object[] { "%" + url + "%" });
		if (null == projectMap || projectMap.size() == 0)
			return;
		StringBuilder sql = new StringBuilder("insert into permit_logs(operation_id,user_id,project_id,map_value)");
		sql.append(" values(?,?,?,?) ");
		this.getSsoDao().excuteBySql(sql.toString(), new Object[] { projectMap.get("operation_id"), Integer.parseInt(para.get("energyUserId")), projectMap.get("project_id"), para.toString() });
	}

}
