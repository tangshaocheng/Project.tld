package com.energysh.egame.xg;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.Style;
import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;

public class XgUtil {

	private Message message1;

	public XgUtil(String appName) {

		message1 = new Message();
		message1.setType(Message.TYPE_NOTIFICATION);

		Style style = new Style(1);
		style = new Style(3, 1, 0, 1, 0);
		ClickAction action = new ClickAction();

		// action.setActionType(ClickAction.TYPE_URL);
		// action.setUrl("http://xg.qq.com");

		// action.setActionType(ClickAction.TYPE_INTENT);
		// action.setIntent("intent:"+appId+"#Intent;action=mobi.joy7.GameInfoActivity;S.key=value;end");//com.joy7.apple.appstore.activity.MainTabActivity
		// action.setIntent("intent:10086#Intent;scheme=tel;action=android.intent.action.DIAL;S.key=value;end");
		action.setActionType(ClickAction.TYPE_ACTIVITY);
		action.setActivity("com.joy7.apple.appstore.activity.MainTabActivity");

		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key", "update");

		message1.setTitle("应用更新提示");
		message1.setContent(appName + "有新版本了，进入AppStore更新！");
		message1.setStyle(style);

		message1.setAction(action);
		message1.setCustom(custom);

		TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
		message1.addAcceptTime(acceptTime1);

	}

	public XgUtil(String title, String updateDes) {
		message1 = new Message();
		message1.setType(Message.TYPE_NOTIFICATION);

		Style style = new Style(1);
		style = new Style(3, 1, 0, 1, 0);
		ClickAction action = new ClickAction();

		action.setActionType(ClickAction.TYPE_ACTIVITY);
		action.setActivity("com.joy7.apple.appstore.activity.MainTabActivity");

		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key", "update");

		message1.setTitle(title);
		message1.setContent(updateDes);
		message1.setStyle(style);

		message1.setAction(action);
		message1.setCustom(custom);

		TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
		message1.addAcceptTime(acceptTime1);
	}

	long accessId = 2100045838; // appstore
	String secretKey = "64d8aabf59194e5992429fe15b563c69";

	// 单个设备下发通知消息
	public JSONObject pushSingleDeviceNotification(String token) {
		XingeApp xinge = new XingeApp(accessId, secretKey);
		JSONObject ret = xinge.pushSingleDevice(token, message1);
		return (ret);
	}

	// 单个设备下发通知消息
	public JSONObject pushSingleDeviceNotification(String token, long accessId, String secretKey) {
		XingeApp xinge = new XingeApp(accessId, secretKey);
		JSONObject ret = xinge.pushSingleDevice(token, message1);
		System.out.println("xg response:" + ret);
		return (ret);
	}

	public static void main(String[] args) {
		// System.out.println(new XgUtil("仙魔奇侠").pushSingleDeviceNotification("17f452cc9f7bc0604edfb0582d16c235e2a55116"));

		System.out.println(new XgUtil("push test", "testUC浏览器,支付宝快捷支付服务,计算器,日历同步服务,微博,QQ,腾讯新闻,小米游戏中心有新版本了，请前往AppStore进行更新！").pushSingleDeviceNotification("019fe2a3ac516bd2453d82aa384910badaea9d74", Long.parseLong("2100045838"), "64d8aabf59194e5992429fe15b563c69"));
	}

}
