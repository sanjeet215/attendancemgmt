package com.asiczen.api.attendancemgmt.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.model.Mail;

@Service
public class EmailServiceImpl {

	@Autowired
	JavaMailSender mailSender;

	@Async
	public void emailData(String mailFrom, String mailTo, String mailContent, String mailSubject) {

		Mail mail = new Mail();

		mail.setMailFrom(mailFrom);
		mail.setMailTo(mailTo);
		mail.setMailContent(mailContent);
		mail.setMailSubject(mailSubject);
		
		sendEmail(mail);
	}

	private void sendEmail(Mail mail) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "asiczen.com"));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent());

			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
