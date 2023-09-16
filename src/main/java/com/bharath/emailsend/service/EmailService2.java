package com.bharath.emailsend.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bharath.emailsend.dto.Attachment;
import com.bharath.emailsend.dto.EmailInfo;
import com.bharath.emailsend.exception.EmailException;
import com.bharath.emailsend.utility.Utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService2 {
	// uncomment below lines
//	@Autowired
//	private JavaMailSender javaMailSender;
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	@Autowired
	private Utility utility;

	public String sendEmail(EmailInfo info) {
		
		javaMailSender.setUsername(info.getFrom());
		javaMailSender.setPassword(info.getPassword());
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			if (info.getFrom() == null || info.getFrom().length() == 0)
				throw new EmailException("provide the from address");
			helper.setFrom(info.getFrom());

			if (info == null || info.getToList().isEmpty()) {
				throw new EmailException("To list not added provided. Kindly check.");
			}

			String[] array = utility.listToArray(info.getToList());
			helper.setTo(array);

			array = utility.listToArray(info.getBccList());
			helper.setBcc(array);

			array = utility.listToArray(info.getCcList());
			helper.setCc(array);

			if (info.getSubject() != null) {
				helper.setSubject(info.getSubject());
			}
			// System.out.println(info.getText());
			String text = info.getText();
			 helper.setText(info.getText());
			//helper.setText(text, true);
			// Add attachments
			if (info.getAttachments() != null && !info.getAttachments().isEmpty()) {
				for (Attachment attachment : info.getAttachments()) {
					helper.addAttachment(attachment.getFileName(), new ByteArrayResource(attachment.getFile()));
				}
			}

			javaMailSender.send(message);
			return "Mail sent successfully";
		} catch (MessagingException e) {
			throw new EmailException("Error sending email: " + e.getMessage());
		}
	}

	public void sendIndividually(List<EmailInfo> list) {
	
		for (EmailInfo email : list) {
			sendEmail(email);
		}
	}
}