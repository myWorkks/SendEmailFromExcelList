package com.bharath.emailsend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Utility utility;

	public String sendEmail(EmailInfo info) {
		System.out.println(info);
		System.out.println(info.getCcList());
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(info.getFrom());
		String[] array = utility.listToArray(info.getToList());
		message.setTo(array);

		array = utility.listToArray(info.getBccList());
		message.setBcc(array);

		array = utility.listToArray(info.getCcList());
		message.setCc(array);

		if (info.getSubject() != null)
			message.setSubject(info.getSubject());
		message.setText(info.getText());
		// System.out.println(message);
		javaMailSender.send(message);
		// System.out.println("after sent");
		return "mail sent successfully";
	}
}
