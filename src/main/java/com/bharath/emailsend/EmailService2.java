package com.bharath.emailsend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService2 {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Utility utility;

	public String sendEmail(EmailInfo info) {
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
			System.out.println(info.getText());

			helper.setText(info.getText());

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
}