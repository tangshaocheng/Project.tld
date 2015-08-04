package com.energysh.egame.exception;

import java.util.HashMap;
import java.util.Map;

public class AppExcCodes {

	private static AppExcCodes instance = null;

	private AppExcCodes() {
	}

	public static AppExcCodes getInstance() {
		if (instance == null) {
			instance = new AppExcCodes();
		}
		return instance;
	}

	/**
	 * 无效参数
	 */
	public final static String E_INVALID_PARA = "E_INVALID_PARA";

	/**
	 * 无效请求
	 */
	public final static String E_INVALID_REQUEST = "E_INVALID_REQUEST";

	/**
	 * 系统内部错误-数据库错误
	 */
	public final static String E_SYS_DB = "E_SYS_DB";

	/**
	 * 系统内部错误-网络错误
	 */
	public final static String E_SYS_NET = "E_SYS_NET";

	/**
	 * 其他错误
	 */
	public final static String E_FAIL = "E_FAIL";

	/**
	 * 业务错误 -- 平台不存在
	 */
	public final static String B_PLAT_NOT_FOUND = "B_PLAT_NOT_FOUND";

	/**
	 * 业务错误 -- 应用不存在
	 */
	public final static String B_APP_NOT_FOUND = "B_APP_NOT_FOUND";

	/**
	 * 业务错误 -- 文章不存在
	 */
	public final static String B_ARTICLE_NOT_FOUND = "B_ARTICLE_NOT_FOUND";

	/**
	 * 业务错误 -- 用户不存在
	 */
	public final static String B_USER_NOT_FOUND = "B_USER_NOT_FOUND";

	/**
	 * 业务错误 -- 用户已绑定
	 */
	public final static String B_USER_HAVE_BIND = "B_USER_HAVE_BIND";

	/**
	 * 业务错误 -- 用户名重复
	 */
	public final static String B_USER_USERNAME_REPEAT = "B_USER_USERNAME_REPEAT";

	/**
	 * 业务错误 -- 密码错误
	 */
	public final static String B_USER_PWD_WRONG = "B_USER_PWD_WRONG";

	/**
	 * 业务错误 -- 账户余额不足
	 */
	public final static String B_INSUFFICIENT_ACCOUNT_BALANCE = "B_INSUFFICIENT_ACCOUNT_BALANCE";

	/**
	 * 业务错误 -- 分类不存在
	 */
	public final static String B_RANK_NOT_FOUND = "B_RANK_NOT_FOUND";

	/**
	 * 业务错误 --分类类型无效
	 */
	public final static String B_RANK_TYPE_INVALID = "B_RANK_TYPE_INVALID";

	/**
	 * 充值失败
	 */
	public final static String B_PAY_FAIL = "B_PAY_FAIL";

	/**
	 * 支付 向快钱发送请求失败
	 */
	public final static String B_PAY_REQUEST_BILL_FAIL = "B_PAY_REQUEST_BILL_FAIL";

	/**
	 * 支付 向快钱发送请求失败
	 */
	public final static String B_PAY_BILL_RETURN_FAIL = "B_PAY_BILL_RETURN_FAIL";

	/**
	 * 支付 订单号重复
	 */
	public final static String B_PAY_ORDERID_REPEAT = "B_PAY_ORDERID_REPEAT";

	/**
	 * 入账 订单号重复
	 */
	public final static String B_FUNDIN_ORDERID_REPEAT = "B_FUNDIN_ORDERID_REPEAT";

	/**
	 * 初始化订单成功，冻结金额失败
	 */
	public final static String B_INIT_ORDERID_FAIL = "B_INIT_ORDERID_FAIL";

	/**
	 * 此名称不可用
	 */
	public final static String B_IS_KEYWORDS_FILTER = "B_IS_KEYWORDS_FILTER";

	class ExcDodesDetal {
		String code;
		String codeMsg;
		String codePrompt;

		public ExcDodesDetal(String code, String codeMsg, String codePrompt) {
			super();
			this.code = code;
			this.codeMsg = codeMsg;
			this.codePrompt = codePrompt;
		}
	}

	private static Map<String, ExcDodesDetal> excDodesMap = new HashMap<String, ExcDodesDetal>();

	{
		excDodesMap.put(E_INVALID_PARA, new ExcDodesDetal(E_INVALID_PARA, "无效参数", "您的请求失败哦！"));

		excDodesMap.put(E_INVALID_REQUEST, new ExcDodesDetal(E_INVALID_REQUEST, "无效请求", "您的请求失败哦！"));

		excDodesMap.put(E_SYS_DB, new ExcDodesDetal(E_SYS_DB, "数据库错误", "您的请求失败哦！"));

		excDodesMap.put(E_SYS_NET, new ExcDodesDetal(E_SYS_NET, "网络错误", "您的网络有问题！"));

		excDodesMap.put(E_FAIL, new ExcDodesDetal(E_FAIL, "其他错误", "平台有未知错误，请重试！"));

		excDodesMap.put(B_PLAT_NOT_FOUND, new ExcDodesDetal(B_PLAT_NOT_FOUND, "平台不存在", "找不到相应数据。请重试！"));

		excDodesMap.put(B_APP_NOT_FOUND, new ExcDodesDetal(B_APP_NOT_FOUND, "应用不存在", "找不到相应数据。请重试！"));

		excDodesMap.put(B_ARTICLE_NOT_FOUND, new ExcDodesDetal(B_ARTICLE_NOT_FOUND, "文章不存在", "找不到相应数据。请重试！"));

		excDodesMap.put(B_USER_NOT_FOUND, new ExcDodesDetal(B_USER_NOT_FOUND, "用户不存在", "用户不存在。请重试！"));

		excDodesMap.put(B_USER_HAVE_BIND, new ExcDodesDetal(B_USER_HAVE_BIND, "用户已绑定", "用户已绑定"));

		excDodesMap.put(B_USER_USERNAME_REPEAT, new ExcDodesDetal(B_USER_USERNAME_REPEAT, "用户名重复", "用户名已重复！"));

		excDodesMap.put(B_USER_PWD_WRONG, new ExcDodesDetal(B_USER_PWD_WRONG, "密码错误", "密码错误，请重新输入"));

		excDodesMap.put(B_INSUFFICIENT_ACCOUNT_BALANCE, new ExcDodesDetal(B_INSUFFICIENT_ACCOUNT_BALANCE, "账户余额不足", "账户余额不足，请充值！"));

		excDodesMap.put(B_RANK_NOT_FOUND, new ExcDodesDetal(B_RANK_NOT_FOUND, "分类不存在", "找不到相应数据。请重试！"));

		excDodesMap.put(B_RANK_TYPE_INVALID, new ExcDodesDetal(B_RANK_TYPE_INVALID, "分类类型无效", "分类类型无效！"));

		excDodesMap.put(B_PAY_FAIL, new ExcDodesDetal(B_PAY_FAIL, "充值失败", "充值失败，请重新充值或咨询客服！"));

		excDodesMap.put(B_PAY_ORDERID_REPEAT, new ExcDodesDetal(B_PAY_ORDERID_REPEAT, "支付失败", "支付失败，订单号重复！"));

		excDodesMap.put(B_FUNDIN_ORDERID_REPEAT, new ExcDodesDetal(B_FUNDIN_ORDERID_REPEAT, "入账失败", "入账失败，订单号重复！"));

		excDodesMap.put(B_IS_KEYWORDS_FILTER, new ExcDodesDetal(B_IS_KEYWORDS_FILTER, "此名称不可用", "此名称不可用，请重新填写！"));

	}

	public String getCodeMsg(String code) {
		ExcDodesDetal detail = excDodesMap.get(code);
		if (detail == null) {
			return "";
		} else {
			return detail.codeMsg;
		}
	}

	public String getCodePrompt(String code) {
		ExcDodesDetal detail = excDodesMap.get(code);
		if (detail == null) {
			return "";
		} else {
			return detail.codePrompt;
		}
	}

}
