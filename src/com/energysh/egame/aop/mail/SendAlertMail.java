package com.energysh.egame.aop.mail;

import com.energysh.egame.util.MailUtil;

public class SendAlertMail {

	private MailUtil mailUtil;

	public SendAlertMail(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public void sendMial(MailContent content) {
		if (content != null)
			mailUtil.sendTableMail(content.getAddress(), content.getSubject(), content.getContent());
	}
}
