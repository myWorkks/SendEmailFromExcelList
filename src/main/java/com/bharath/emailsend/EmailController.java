package com.bharath.emailsend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebDriver;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
//
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
//
import jakarta.servlet.http.HttpServletRequest;

//
@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private ConfigurableApplicationContext context;
	@Autowired
	private WebDriver driver;
	@Autowired
	private Utility utility;

	@GetMapping("/")
	public ModelAndView showForm() {
		return new ModelAndView("form");
	}

//
	@PostMapping("/send")
	public String sendEmail(@RequestBody EmailInfo info) {
		emailService.sendEmail(info);
		return "Email sent successfully!";
	}

	@PostMapping("/sent")
	public ModelAndView submitForm(MultipartHttpServletRequest request) throws IOException {
		EmailInfo info = new EmailInfo();
		String from = request.getParameter("from");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		info.setSubject(subject);
		info.setFrom(from);
		info.setText(message);
		if (request instanceof MultipartHttpServletRequest) {
			System.out.println("it is in if block multipart file");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			// Retrieve the uploaded files
			Iterator<String> fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String fileName = fileNames.next();
				MultipartFile file = multipartRequest.getFile(fileName);

				if (file != null && !file.isEmpty()) {

					InputStream fis = file.getInputStream();
					ReadDataFromExcel r = new ReadDataFromExcel();
					info = r.readData(fis, info);
					System.out.println("boolean" + info.getToList().isEmpty());
					System.out.println(info.getCcList() != null && info.getCcList().isEmpty());
					System.out.println(info.getBccList() != null && info.getBccList().isEmpty());
		
					
					if (info.getToList() != null && info.getToList().isEmpty()) {
						List<String> toList = utility.convertArrayToList(request.getParameter("to"));
						info.setToList(toList);
					}
					if (info.getCcList() != null && info.getCcList().isEmpty()) {
						System.out.println("cc list is empty");
						String cc = request.getParameter("cc");
						List<String> toList = utility.convertArrayToList(cc);
						info.setCcList(toList);

					}
					if (info.getBccList() != null && info.getBccList().isEmpty()) {
						String cc = request.getParameter("bcc");
						List<String> toList = utility.convertArrayToList(cc);
						info.setBccList(toList);
					}

				} else {
					System.out.println("file is empty");
					String to = request.getParameter("to");

					// System.out.println(from.length());
					String cc = request.getParameter("cc");
					String bcc = request.getParameter("bcc");

					List<String> toList = utility.convertArrayToList(cc);
					info.setCcList(toList);
					toList = utility.convertArrayToList(bcc);
					info.setBccList(toList);

					toList = utility.convertArrayToList(to);
					info.setToList(toList);
				}
			}
		}

		System.out.println("info before invokung service" + info);
		emailService.sendEmail(info);

		return new ModelAndView("redirect:/success");
	}

//
	@GetMapping("/success")
	public ModelAndView showSuccessPage() {
		return new ModelAndView("success");
	}

	@GetMapping("/close")
	public void terminateApp() throws InterruptedException {
		System.out.println("terminating app");
		driver.close();
		// Thread.sleep(1000);
		context.close();
		System.out.println("terminated success fully");
	}
}

//chat gpt code
