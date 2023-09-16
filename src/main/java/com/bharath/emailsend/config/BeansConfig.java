package com.bharath.emailsend.config;

import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import jakarta.mail.internet.MimeMessage;

@Configuration
public class BeansConfig {
	@Autowired
	private Environment environment;

	@Bean
	@Scope("singleton")
	@Lazy
	public WebDriver webDriver() {
		// System.out.println("web driver bean called");
		System.setProperty("webdriver.chrome.driver",
				"G:\\MyWorks\\Extracts\\ChromeDriver\\chromedriver-win32\\chromedriver.exe");
		return new ChromeDriver();
	}

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl s = new JavaMailSenderImpl();

		s.setHost(environment.getProperty("gmail-host-name"));
		s.setPort(Integer.valueOf(environment.getProperty("email-port")));

		Properties props = s.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.debug", "true");

		return s;
	}
}
