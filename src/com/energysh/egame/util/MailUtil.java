package com.energysh.egame.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;

import com.energysh.egame.service.MemcachedService;

/**
 * 邮件发送
 * 
 * @author yulei.yang
 * 
 */
public class MailUtil {

	private final static Log log = LogFactory.getLog(MailUtil.class);
	private final static BlockingQueue<Object> insertQueue = new LinkedBlockingQueue<Object>();

	/**
	 * 启动异步线程发送预警邮件
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		// if (!Boolean.valueOf(MyConfigurer.getEvn("chan.las.main"))) {
		// System.out.println("chan.las.main=" + (Boolean.valueOf(MyConfigurer.getEvn("chan.las.main"))));
		// return;
		// }
		// for (int i = 0; i < 5; i++) {
		// AsyncTakeThread asyncTakeThread = new AsyncTakeThread(this, i);
		// Thread takeThread = new Thread(asyncTakeThread);
		// takeThread.start();
		// }
	}

	public void send(String[] to, String from, String subject, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setFrom(from);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		senderImpl.send(mailMessage);
	}

	public void sendAttachment(String[] to, String from, String subject, String text) {
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = null;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(to);// 接收者
			messageHelper.setFrom(from);// 发送者
			messageHelper.setSubject(subject);// 主题
			messageHelper.setText(text, true);// 附件内容
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			log.error("send attachment error: ", e);
		}
	}

	public void sendHtmlWithImage(String[] to, String from, String subject, String text, List<String> typeList) {
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = null;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(to);// 接收者
			messageHelper.setFrom(from);// 发送者
			messageHelper.setSubject(subject);// 主题
			messageHelper.setText(text, true);// html内容
			if (typeList != null)
				for (String type : typeList) {
					FileSystemResource fsr = new FileSystemResource(ResourceUtils.getFile(type + ".jpg"));
					// html必须包含: <img src="cid:#id#"/>
					messageHelper.addInline(type, fsr);
				}
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			log.error("send htmlWithImage error: ", e);
		}
	}

	// String[]
	// strHead={"日期：","报警时间：","SP名称：","指令/ 长号码：","省份：","日上限：","当日本时段MO数："};
	// String[] strV={"SOHU","876","河南","300","301"};
	// String[] strV1={"SOHU","8761","河南1","3001","3011"};
	// String[] strV2={"SOHU","8762","河南1","3002","3012"};
	// List<String[]> vList = new ArrayList<String[]>();
	// vList.add(strV);
	// vList.add(strV1);
	// vList.add(strV2);
	public void sendTableMail(String[] to, String subject, String content) {
		sendHtmlWithImage(to, "logServer@energysh.com", subject, content, null);
	}

	public String getTableHtml(String[] strHead1, String[] strHead2, String[] alertTimes, List<String[]> vList) {

		StringBuilder sb = new StringBuilder("<table border=\"1\">");
		sb.append("<tr>");
		sb.append("<td colspan=\"3\">");
		sb.append(strHead1[0]);
		sb.append(alertTimes[0]);
		sb.append("</td>");

		sb.append("<td colspan=\"3\">");
		sb.append(strHead1[1]);
		sb.append(alertTimes[1]);
		sb.append("</td>");

		sb.append("</tr>");
		sb.append("<tr>");
		for (int i = 0; i < strHead2.length; i++) {
			sb.append("<td>");
			sb.append(strHead2[i]);
			sb.append("</td>");
		}
		sb.append("</tr>");
		for (int i = 0; i < vList.size(); i++) {
			sb.append("<tr>");
			String[] values = vList.get(i);
			if (values != null && values.length > 0)
				for (int j = 0; j < values.length; j++) {
					sb.append("<td>");
					sb.append(values[j]);
					sb.append("</td>");
				}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	public void insertSendQueue(String key, String subject, String text) {
		try {
			Map<String, String> paras = new HashMap<String, String>();
			paras.put("key", key);
			paras.put("subject", subject);
			paras.put("text", text);
			insertQueue.put(paras);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送预警邮件
	 * 
	 * @param key
	 * @param text
	 */
	public void takeSendQueue() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class AsyncTakeThread implements Runnable {

		private int threadNum;
		private MailUtil mailUtil = null;
		private final Log log = LogFactory.getLog(MailUtil.class);

		public AsyncTakeThread(MailUtil mailUtil, int threadNum) {
			this.mailUtil = mailUtil;
			this.threadNum = threadNum;
		}

		public void run() {
			while (true) {
				try {
					mailUtil.takeSendQueue();
				} catch (Exception e) {
					log.error("ERROR takeSendQueue Thread " + threadNum, e);
				}
			}
		}
	}

	private MemcachedService memCounter;

	private JavaMailSenderImpl senderImpl;

	public MemcachedService getMemCounter() {
		return memCounter;
	}

	public void setMemCounter(MemcachedService memCounter) {
		this.memCounter = memCounter;
	}

	public void setSenderImpl(JavaMailSenderImpl senderImpl) {
		this.senderImpl = senderImpl;
	}

}
