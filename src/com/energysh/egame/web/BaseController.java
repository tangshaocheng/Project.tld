package com.energysh.egame.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.energysh.egame.service.AccountService;
import com.energysh.egame.service.AppCategoryService;
import com.energysh.egame.service.AppService;
import com.energysh.egame.service.AppStatisticsService;
import com.energysh.egame.service.AppThemeBagService;
import com.energysh.egame.service.AppThemeBagSortService;
import com.energysh.egame.service.AppThemeBagSortSxService;
import com.energysh.egame.service.AppThemeService;
import com.energysh.egame.service.CpsIncomeService;
import com.energysh.egame.service.DownloadAppListService;
import com.energysh.egame.service.GenBatchService;
import com.energysh.egame.service.LoginService;
import com.energysh.egame.service.PartnersIncomeConfService;
import com.energysh.egame.service.SalesService;
import com.energysh.egame.service.UserIncomeService;
import com.energysh.egame.service.YesIncomeService;

@SuppressWarnings("unchecked")
public abstract class BaseController extends AbstractController {

	private final static Log log = LogFactory.getLog(BaseController.class);
	private ThreadLocal<Map<String, Object>> tl = new ThreadLocal<Map<String, Object>>();

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) tl.get().get("request");
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) tl.get().get("response");
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest _request,
			HttpServletResponse _response) throws Exception {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("request", _request);
		rsMap.put("response", _response);
		tl.set(rsMap);
		return excute();
	}

	public Map<String, String> getPara() throws Exception {
		Map<String, String> paraMap = new HashMap<String, String>();
		Enumeration<String> pns = this.getRequest().getParameterNames();
		String key = "";
		char a = 6;
		while (pns.hasMoreElements()) {
			key = pns.nextElement();
			String[] vs = this.getRequest().getParameterValues(key);
			String value = "";
			if (vs.length == 1)
				value = vs[0].trim();
			else {
				for (String v : vs)
					value += v.trim() + a;
				value = value.substring(0, value.length() - 1);
			}
			// value = mu.htmEncode(value);// //特殊字符转换
			if (!paraMap.containsKey(key)) {
				paraMap.put(key, value);
			} else {
				paraMap.put(key, paraMap.get(key).toString() + a + value);
			}
		}
		return paraMap;
	}

	public Map<String, String> getPara(Map<String, String> spBase)
			throws Exception {
		Map<String, String> paraMap = new HashMap<String, String>();
		Enumeration<String> pns = this.getRequest().getParameterNames();
		String key = "";
		char a = 6;
		while (pns.hasMoreElements()) {
			key = pns.nextElement();
			String[] vs = this.getRequest().getParameterValues(key);
			String value = "";
			if (vs.length == 1)
				value = vs[0].trim();
			else {
				for (String v : vs)
					value += v.trim() + a;
				value = value.substring(0, value.length() - 1);
			}
			// value = mu.htmEncode(value);// //特殊字符转换
			value = toGb2312(value, spBase);
			if (!paraMap.containsKey(key)) {
				paraMap.put(key, value);
			} else {
				paraMap.put(key, paraMap.get(key).toString() + a + value);
			}
		}
		return paraMap;
	}

	/**
	 * 用getBytes(encoding)：返回字符串的一个byte数组 当b[0]为 63时，应该是转码错误 A、不乱码的汉字字符串：
	 * 1、encoding用GB2312时，每byte是负数； 2、encoding用ISO8859_1时，b[i]全是63。 B、乱码的汉字字符串：
	 * 1、encoding用ISO8859_1时，每byte也是负数； 2、encoding用GB2312时，b[i]大部分是63。 C、英文字符串
	 * 1、encoding用ISO8859_1和GB2312时，每byte都大于0；
	 * 
	 * 总结：给定一个字符串，用getBytes("iso8859_1") 1、如果b[i]有63，不用转码； A-2
	 * 2、如果b[i]全大于0，那么为英文字符串，不用转码； B-1 3、如果b[i]有小于0的，那么已经乱码，要转码。 C-1
	 */
	private static String toGb2312(String str, Map<String, String> spBase) {
		if (str == null)
			return null;
		String retStr = str;
		byte b[];
		try {
			b = str.getBytes("ISO-8859-1");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63)
					break; // 1
				else if (b1 > 0)
					continue;// 2
				else if (b1 < 0) { // 不可能为0，0为字符串结束符
					retStr = new String(b, spBase.get("_decodeWay"));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}

	public Map<String, String> getAjaxPara() throws Exception {
		return getPara();
	}

	public ModelAndView setJsonResult(Object obj) throws Exception {
		this.getResponse().setContentType("text/plain;charset=utf-8");
		PrintWriter responseStream = this.getResponse().getWriter();
		responseStream.println(JSONArray.fromObject(obj));
		responseStream.close();
		return null;
	}

	public ModelAndView setJsonResultObj(Object obj) throws Exception {
		this.getResponse().setContentType("text/plain;charset=utf-8");
		PrintWriter responseStream = this.getResponse().getWriter();
		responseStream.println(JSONObject.fromObject(obj));
		responseStream.close();
		return null;
	}

	public ModelAndView errorAjax(Exception ex) {
		try {
			if (log.isErrorEnabled())
				log.error("web exception:", ex);
			this.getResponse().setContentType("text/plain;charset=utf-8");
			PrintWriter responseStream = this.getResponse().getWriter();
			Map<String, String> rmap = new HashMap<String, String>();
			if (ex instanceof SQLException) {
				rmap.put("info", "sqlEx");
			} else if (ex instanceof IOException) {
				rmap.put("info", "fileEx");
			} else {
				rmap.put("info", "otherEx");
			}
			responseStream.println(JSONArray.fromObject(rmap));
			responseStream.close();
		} catch (IOException e) {
			log.error("error ajax error: ", e);
		}
		return null;
	}

	public ModelAndView error(Exception ex) {
		if (log.isErrorEnabled())
			log.error("web exception:", ex);
		Map<String, String> rmap = new HashMap<String, String>();
		if (ex instanceof SQLException) {
			rmap.put("info", "sqlEx");
		} else if (ex instanceof IOException) {
			rmap.put("info", "fileEx");
		} else if (ex instanceof FileUploadException) {
			rmap.put("info", "fileUploadEx");
		} else {
			rmap.put("info", "otherEx");
		}
		return new ModelAndView("/error.jsp", rmap);
	}

	public String jsp;

	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	public String getJsp() {
		return this.jsp;
	}

	public abstract ModelAndView excute();

	private AppService appService;
	private LoginService loginService;
	private AppThemeService appThemeService;
	private AppThemeBagService appThemeBagService;
	private AppCategoryService appCategoryService;
	private AppThemeBagSortService appThemeBagSortService;
	private AppThemeBagSortSxService appThemeBagSortSxService;
	private DownloadAppListService downloadAppListService;
	private AppStatisticsService appStatisticsService;
	private AccountService accountService;
	private GenBatchService genBatchService;
	private SalesService salesService;
	private YesIncomeService yesIncomeService;
	private PartnersIncomeConfService partnersIncomeConfService;
	private CpsIncomeService cpsIncomeService;
	private UserIncomeService userIncomeService;

	public UserIncomeService getUserIncomeService() {
		return userIncomeService;
	}

	public void setUserIncomeService(UserIncomeService userIncomeService) {
		this.userIncomeService = userIncomeService;
	}

	public CpsIncomeService getCpsIncomeService() {
		return cpsIncomeService;
	}

	public void setCpsIncomeService(CpsIncomeService cpsIncomeService) {
		this.cpsIncomeService = cpsIncomeService;
	}

	public PartnersIncomeConfService getPartnersIncomeConfService() {
		return partnersIncomeConfService;
	}

	public void setPartnersIncomeConfService(
			PartnersIncomeConfService partnersIncomeConfService) {
		this.partnersIncomeConfService = partnersIncomeConfService;
	}

	public AppThemeBagSortSxService getAppThemeBagSortSxService() {
		return appThemeBagSortSxService;
	}

	public void setAppThemeBagSortSxService(
			AppThemeBagSortSxService appThemeBagSortSxService) {
		this.appThemeBagSortSxService = appThemeBagSortSxService;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public AppThemeService getAppThemeService() {
		return appThemeService;
	}

	public void setAppThemeService(AppThemeService appThemeService) {
		this.appThemeService = appThemeService;
	}

	public AppThemeBagService getAppThemeBagService() {
		return appThemeBagService;
	}

	public void setAppThemeBagService(AppThemeBagService appThemeBagService) {
		this.appThemeBagService = appThemeBagService;
	}

	public AppCategoryService getAppCategoryService() {
		return appCategoryService;
	}

	public void setAppCategoryService(AppCategoryService appCategoryService) {
		this.appCategoryService = appCategoryService;
	}

	public AppThemeBagSortService getAppThemeBagSortService() {
		return appThemeBagSortService;
	}

	public void setAppThemeBagSortService(
			AppThemeBagSortService appThemeBagSortService) {
		this.appThemeBagSortService = appThemeBagSortService;
	}

	public DownloadAppListService getDownloadAppListService() {
		return downloadAppListService;
	}

	public void setDownloadAppListService(
			DownloadAppListService downloadAppListService) {
		this.downloadAppListService = downloadAppListService;
	}

	public AppStatisticsService getAppStatisticsService() {
		return appStatisticsService;
	}

	public void setAppStatisticsService(
			AppStatisticsService appStatisticsService) {
		this.appStatisticsService = appStatisticsService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public GenBatchService getGenBatchService() {
		return genBatchService;
	}

	public void setGenBatchService(GenBatchService genBatchService) {
		this.genBatchService = genBatchService;
	}

	public SalesService getSalesService() {
		return salesService;
	}

	public void setSalesService(SalesService salesService) {
		this.salesService = salesService;
	}

	public YesIncomeService getYesIncomeService() {
		return yesIncomeService;
	}

	public void setYesIncomeService(YesIncomeService yesIncomeService) {
		this.yesIncomeService = yesIncomeService;
	}

}
