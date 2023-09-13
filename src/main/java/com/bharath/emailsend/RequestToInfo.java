package com.bharath.emailsend;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class RequestToInfo {
	@Autowired
	private Utility utility;

	public EmailInfo convertRequestToEmail(MultipartHttpServletRequest multipartRequest) throws IOException {
		EmailInfo info = new EmailInfo();
		String from = multipartRequest.getParameter("from");
		String subject = multipartRequest.getParameter("subject");
		String message = multipartRequest.getParameter("message");
		info.setSubject(subject);
		info.setFrom(from);
		info.setText(message);

		// MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)
		// request;

		// Retrieve the uploaded files
		Iterator<String> fileNames = multipartRequest.getFileNames();
		List<String> fileNamesl = new ArrayList<String>();
		while (fileNames.hasNext()) {
			fileNamesl.add(fileNames.next());

		}
		MultipartFile excel = multipartRequest.getFile(fileNamesl.get(0));
		if (excel != null && !excel.isEmpty()) {

			InputStream fis = excel.getInputStream();
			ReadDataFromExcel r = new ReadDataFromExcel();
			info = r.readData(fis, info);
			List<String> toList1 = info.getToList();
			if (toList1 == null) {
				toList1 = new ArrayList<String>();
				toList1 = utility.convertArrayToList(multipartRequest.getParameter("to"));
				info.setToList(toList1);
				// System.out.println("toList null feteched from form" + info);
			} else {

				toList1.addAll(utility.convertArrayToList(multipartRequest.getParameter("to")));

				toList1 = toList1.stream().distinct().collect(Collectors.toList());

				info.setToList(toList1);
			}

			toList1 = info.getCcList();
			if (toList1 == null) {
				toList1 = new ArrayList<String>();
				toList1 = utility.convertArrayToList(multipartRequest.getParameter("cc"));

				info.setCcList(toList1);
			} else {
				toList1.addAll(utility.convertArrayToList(multipartRequest.getParameter("cc")));
				toList1 = toList1.stream().distinct().collect(Collectors.toList());

				info.setCcList(toList1);
			}

			toList1 = info.getBccList();
			if (toList1 == null) {
				toList1 = new ArrayList<String>();
				toList1 = utility.convertArrayToList(multipartRequest.getParameter("bcc"));
				info.setBccList(toList1);
			} else {
				toList1.addAll(utility.convertArrayToList(multipartRequest.getParameter("bcc")));
				toList1 = toList1.stream().distinct().collect(Collectors.toList());

				info.setBccList(toList1);
			}
		}

		else {

			String to = multipartRequest.getParameter("to");

			String cc = multipartRequest.getParameter("cc");
			String bcc = multipartRequest.getParameter("bcc");

			List<String> toList = utility.convertArrayToList(cc);
			info.setCcList(toList);
			;
			toList = utility.convertArrayToList(bcc);

			info.setBccList(toList);

			toList = utility.convertArrayToList(to);

			info.setToList(toList);
		}

		List<MultipartFile> attachments = multipartRequest.getFiles(fileNamesl.get(1));
		List<Attachment> attach = new ArrayList<>();
		for (MultipartFile att : attachments) {
			if (att != null && !att.isEmpty()) {
				Attachment a = new Attachment();
				a.setFile(att.getBytes());
				a.setFileName(att.getOriginalFilename());
				attach.add(a);
			}
		}
		info.setAttachments(attach);

		;
		System.out.println("info before service" + info);
		return info;

	}
}
