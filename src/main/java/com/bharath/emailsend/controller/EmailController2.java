package com.bharath.emailsend.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bharath.emailsend.dto.EmailInfo;
import com.bharath.emailsend.service.EmailService2;
import com.bharath.emailsend.service.RequestToInfo;

@RestController
@CrossOrigin
public class EmailController2 {
	@Autowired
	private EmailService2 emailService;
	@Autowired
	private ConfigurableApplicationContext context;
	@Autowired
	private WebDriver driver;
	@Autowired
	private RequestToInfo info;

	@GetMapping("/")
	public ModelAndView showForm() {
		return new ModelAndView("form");
	}

	@GetMapping("/success")
	public ModelAndView showSuccessPage() {
		return new ModelAndView("success");
	}

	@GetMapping("/close")
	public void terminateApp() throws InterruptedException {

		driver.close();

		context.close();

	}

	@PostMapping("/sent1")
	public ModelAndView submitForm(MultipartHttpServletRequest request) throws IOException {

		emailService.sendEmail(info.convertRequestToEmail(request));
		return new ModelAndView("redirect:/success");

	}

	@PostMapping("/send")
	public ModelAndView sendIndividualMail(MultipartHttpServletRequest request) throws IOException {
		System.out.println("in controller");
		List<EmailInfo> list = info.getIndividualInfo(request);

		emailService.sendIndividually(list);
		return new ModelAndView("redirect:/success");

	}
}
