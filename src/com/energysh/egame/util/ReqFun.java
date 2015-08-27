package com.energysh.egame.util;

public interface ReqFun {

	/**
	 * 检查平台最新版本
	 */
	final String FUN_EGAME_VERSION_CHECK = "eGameVersionCheck";

	/**
	 * 检查用户是否注册
	 */
	final String FUN_CHECK_USER_REGISTER = "accountCheckRegister";

	/**
	 * 用户快速注册
	 */
	final String FUN_QUICK_REGISTER = "accountQuickRegister";

	final String FUN_PUT_XG_PUSH_TOKEN = "putXgPushToken";

	/**
	 * 用户手动注册
	 */
	final String FUN_ACCOUNT_REGISTER = "accountRegister";

	/**
	 * 用户登录
	 */
	final String FUN_LOGIN = "accountLogin";

	/**
	 * 绑定对接新用户登录
	 */
	final String FUN_LOGIN_JOY7 = "accountLoginJoy7";

	/**
	 * 用户登出
	 */
	final String FUN_LOGOUT = "accountLogout";

	/**
	 * 用户编辑
	 */
	final String FUN_ACCOUNT_EDIT = "accountEdit";

	/**
	 * 查询用户余额
	 */
	final String FUN_ACCOUNT_BALANCE = "accountBalance";
	/**
	 * 获取用户头像列表
	 */
	final String FUN_GET_AVATAR_LIST = "getAvatarList";

	/**
	 * 密码修改
	 */
	final String FUN_ACCOUNT_PWD_CHANGE = "accountPwdChange";

	/**
	 * 密码修改
	 */
	final String FUN_ACCOUNT_PAY_PWD = "accountPayPwd";

	/**
	 * session验证
	 */
	final String FUN_VERIFY_SESSIONID = "verifySessionId";

	/**
	 * 同步已安装应用列表
	 */
	final String FUN_APP_INSTALL_LIST_SYNC = "appInstalledListSync";

	/**
	 * 首页推荐top
	 */
	final String FUN_HOME_RECOMMEND_TOP_LIST = "homeRecommendTopList";

	/**
	 * 首页推荐
	 */
	final String FUN_HOME_RECOMMEND_LIST = "homeRecommendList";
	/**
	 * 游戏大厅--专题列表
	 */
	final String FUN_SUBJECT_LIST = "subjectList";

	/**
	 * 游戏大厅--专题APP列表
	 */
	final String FUN_SUBJECT_APP_LIST = "subjectAppList";

	/**
	 * 游戏大厅 -- 推荐列表
	 */
	final String FUN_RECOMMEND_LIST = "recommendList";

	/**
	 * 游戏大厅 -- 推荐Top列表
	 */
	final String FUN_RECOMMEND_TOP_LIST = "recommendTopList";

	/**
	 * 游戏大厅 -- APP列表
	 */
	final String FUN_APP_LIST = "appList";

	/**
	 * 游戏大厅 -- 专题图片列表
	 */
	final String FUN_APP_THEME_PIC_LIST = "themePicList";

	/**
	 * 游戏大厅-- 排行分类列表
	 */
	final String FUN_RANK_LIST = "rankList";

	/**
	 * 排行APP列表
	 */
	final String FUN_RANK_APP_LIST = "rankAppList";

	/**
	 * 游戏大厅--分类列表
	 */
	final String FUN_CATEGORY_LIST = "categoryList";

	/**
	 * 游戏大厅--分类APP详细
	 */
	final String FUN_CATEGORY_APP_LIST = "categoryAppList";

	/**
	 * 文章列表
	 */
	final String FUN_ARTICLE_LIST = "articleList";

	/**
	 * 文章列表Top
	 */
	final String FUN_ARTICLE_TOP_LIST = "articleTopList";

	/**
	 * 文章详细
	 */
	final String FUN_ARTICLE_DETAIL = "articleDetail";

	/**
	 * 文章内容页面
	 */
	final String FUN_ARTICLE_PAGE = "articlePage";
	/**
	 * PUSH运营活动内容页面
	 */
	final String FUN_ACTIVITY_PAGE = "activityPage";

	/**
	 * 添加文章评论
	 */
	final String FUN_ADD_ARTICLE_COMMENT = "addArticleComment";

	/**
	 * 添加游戏评论
	 */
	final String FUN_ADD_APP_COMMENT = "addAppComment";

	/**
	 * 文章评论列表
	 */
	final String FUN_ARTICLE_COMMENT_LIST = "articleCommentList";

	/**
	 * 获取游戏评论
	 */
	final String FUN_APP_COMMENT_LIST = "appCommentList";

	/**
	 * 添加游戏评论
	 */
	final String FUN_APP_ADD_COMMENT = "addComment";

	/**
	 * 排行榜
	 */
	final String FUN_APP_GET_RANK = "getRank";

	/**
	 * 我的附近
	 */
	final String FUN_APP_NEAR_ME_APP = "nearmeApp";

	/**
	 * 搜索
	 */
	final String FUN_APP_SEARCH_APP = "searchApp";

	/**
	 * 类别
	 */
	final String FUN_APP_GET_CATEGORY = "getCategory";

	/**
	 * 游戏中心
	 */
	final String FUN_APP_GAME_CENTER_APP_LIST = "gameCenterAppList";

	/**
	 * 分类信息
	 */
	final String FUN_APP_GET_CATEGORY_INFO = "getCategoryInfo";

	/**
	 * 相关项目列表信息
	 */
	final String FUN_APP_GET_RELATION_APP_LIST = "getRelationAppList";

	/**
	 * 获取APP详情
	 */
	final String FUN_APP_DETAIL = "appDetail";

	/**
	 * 获取查询关键字列表
	 */
	final String FUN_SEARCH_KEYWORD_LIST = "searchKeywordList";

	/**
	 * 查询app
	 */
	final String FUN_SEARCH_APP_LIST = "searchAppList";

	/**
	 * 长时间未登陆通知
	 */
	final String FUN_NEED_LOGIN_PUSH_MESSAGE = "needLoginPushMessage";

	/**
	 * 新游戏推荐通知
	 */
	final String FUN_NEED_APP_PUSH_MESSAGE = "needAppPushMessage";

	/**
	 * 游戏更新PUSH通知
	 */
	final String FUN_NEED_UPDATE_PUSH_MESSAGE = "needUpdatePushMessage";

	/**
	 * 运营活动PUSH通知
	 */
	final String FUN_ACTIVITY_PUSH_MESSAGE = "activityPushMessage";

	/**
	 * 上传统计数据 (针对全部用户)
	 */
	final String FUN_PUT_DEVICE_INFO = "putDeviceInfo";

	/**
	 * 获取PUSH通知周期
	 */
	final String FUN_GET_PUSH_PERIOD = "getPushPeriod";
	/**
	 * 获取充值卡配置信息
	 */
	final String FUN_GET_CHARGE_CARD_INFO = "getChargeCardInfo";

	/**
	 * 设置密保问题
	 */
	final String FUN_ACCOUNT_PWD_PROTECTION = "accountPwdProtect";

	/**
	 * 下载应用
	 */
	final String FUN_DOWNLOAD_APP = "downloadApp";

	/**
	 * 下载平台
	 */
	final String FUN_DOWNLOAD_PLATFORM = "downloadPlatform";

	/**
	 * 加载器请求
	 */
	final String FUN_LOADER_INFO = "loaderInfo";

	/**
	 * 下载应用 通过平台
	 */
	final String FUN_DOWNLOAD_NOTIFY_BY_LOADER = "downloadNotifyByLoader";

	/**
	 * 账户充值
	 */
	final String FUN_ACCOUNT_CHARGE = "accountCharge";

	/**
	 * 账户充值确认
	 */
	final String FUN_ACCOUNT_CHARGE_RESPONSE = "accountChargeResponse";

	/**
	 * 支付通知
	 */
	final String FUN_PAY_NOTIFY = "payNotify";

	/**
	 * 支付到游戏
	 */
	final String FUN_PAY_TO_APP = "payToApp";

	/**
	 * 商户订单查询
	 */
	final String FUN_MER_ORDER_QUERY = "merOrderQuery";

	/**
	 * 查询用户交易信息　包含充值平台和充值到游戏
	 */
	final String FUN_QUERY_USERT_TRANS = "queryUsertTrans";

	/**
	 * 获取平台下载地址URL列表
	 */
	final String FUN_GET_DOWN_URL_LIST = "getDownUrlList";

	/**
	 * 获取计费短信渠道
	 */
	final String FUN_GET_SMS_FEE_CUSTOM = "getSmsFeeCustom";

	/**
	 * 获取360 TokenInfo
	 */
	final String FUN_GET_360_TOKEN_INFO = "get360TokenInfo";
	/**
	 * 获取360 QihooUserInfo
	 */
	final String FUN_GET_360_USER_INFO = "get360UserInfo";

	/**
	 * 绑定360账号信息
	 */
	final String FUN_GET_360_USER_BIND = "get360UserBind";

	/**
	 * 获取IOS购买验证
	 */
	final String FUN_GET_IOS_BUY_INFO = "getIosBuyInfo";

	/**
	 * uc用户登陆
	 */
	final String FUN_UC_USER_LOGIN = "ucUserLogin";

	/**
	 * 游戏登陆uc用户登陆
	 */
	final String FUN_GAME_UC_USER_LOGIN = "gameUcUserLogin";

	/**
	 * 游戏登陆dl用户登陆
	 */
	final String FUN_DL_USER_LOGIN_VALID = "dlUserLoginValid";

	/**
	 * 游戏登陆xm用户登陆
	 */
	final String FUN_XM_USER_LOGIN_VALID = "xmUserLoginValid";

	/**
	 * 游戏登陆应用汇用户登陆
	 */
	final String FUN_YYH_USER_LOGIN_VALID = "yyhUserLoginValid";

	/**
	 * 游戏登陆豌豆荚用户登陆
	 */
	final String FUN_WDJ_USER_LOGIN_VALID = "wdjUserLoginValid";

	/**
	 * 游戏登陆百度多酷用户登陆
	 */
	final String FUN_DK_USER_LOGIN_VALID = "dkUserLoginValid";

	/**
	 * 游戏登陆百度多酷用户登陆
	 */
	final String FUN_LENOVO_USER_LOGIN_VALID = "lenovoUserLoginValid";

	/**
	 * 游戏强制更新下载接口
	 */
	final String FUN_GAME_ENFORCE_UPDATE_URL = "gameEnforceUpdateUrl";

	/**
	 * 获取游戏手机号绑定验证码
	 */
	final String FUN_GET_PHONE_VALID_CODE = "getPhoneValidCode";

	/**
	 * 获取游戏验证码
	 */
	final String FUN_USER_BIND_PHONE = "userBindPhone";

	/**
	 * 游戏登陆丫丫玩用户登陆
	 */
	final String FUN_YYW_USER_LOGIN_VALID = "yywUserLoginValid";

	/**
	 * 游戏登陆搜狐玩用户登陆
	 */
	final String FUN_SOHU_USER_LOGIN_VALID = "sohuUserLoginValid";

	/**
	 * 游戏登陆机锋玩用户登陆
	 */
	final String FUN_GFAN_USER_LOGIN_VALID = "gfanUserLoginValid";

	/**
	 * 91
	 */
	final String FUN_DJ91_USER_LOGIN_VALID = "dj91UserLoginValid";
	/**
	 * 麒聚
	 */
	final String FUN_QIJU_USER_LOGIN_VALID = "qijuUserLoginValid";

	final String FUN_SMS_BUY_REMIND_CONTENT = "smsBuyRemindContent";

	/**
	 * 游戏登陆琵琶网用户登陆
	 */
	final String FUN_PIPAW_USER_LOGIN_VALID = "pipawUserLoginValid";

	final String FUN_HUAWEI_USER_LOGIN_VALID = "huaweiUserLoginValid";

	final String FUN_GET_USER_BIND_PHONE = "getUserBindPhone";

	final String FUN_GET_USER_PWD_UPDATE_VALID = "getUserPwdUpdateValid";

	final String FUN_USER_PWD_BIND_VALID = "userPwdBindValid";

	/**
	 * wifi助手上传客户端数据
	 */
	final String TYPE_REPORT_DEVICE_DATA = "reportDeviceData";
	final String TYPE_REPORT_USER_ACT_DATA = "reportUserActData";

	/**
	 * 查询外部API app
	 */
	final String FUN_SEARCH_OUT_APP_LIST = "searchOutAppList";

	/**
	 * 查询应用详情API app
	 */
	final String FUN_APP_DETAIL_OUT = "appDetailOut";

	/**
	 * 推荐---精选 新锐
	 */
	final String FUN_RECOMMEND_XINRUI_LIST = "recommendXinruiList";

	/**
	 * 推荐---精选 新锐
	 */
	final String FUN_RECOMMEND_CATEGORY_LIST = "recommendCategoryList";

	final String FUN_DOWN_APP_COMPLETE = "downAppComplete";

	final String FUN_GET_AD_CONF = "getAdConf";

	final String FUN_APP_INSTALL_COMPLETE = "AppInstallComplete";

	/**
	 * 23. 三星精品列表信息
	 */
	final String FUN_SXJP_LIST = "sxjpList";

	/**
	 * 24. 三星应用列表
	 */
	final String FUN_GET_SX_APP_LIST = "getSxAppList";

	/**
	 * 25. APPSTORE热门搜索关键词
	 */
	final String FUN_HOT_SEARCH_APP = "hotSearchApp";

	/**
	 * APPSTORE探索推荐横幅
	 */
	final String FUN_DISCOVERY_LIST = "discoveryList";

	final String FUN_AD_APP_LIST = "adAppList";

	/**
	 * 预先下载应用列表
	 */
	final String FUN_PRE_DOWN_APP_LIST = "preDownAppList";
	
	final String FUN_JM_SWITCH = "jmSwitch";
	
	final String ADSDK_SWITCH="ADsdkActivation";

}
