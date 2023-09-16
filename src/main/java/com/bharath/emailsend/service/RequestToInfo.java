package com.bharath.emailsend.service;

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

import com.bharath.emailsend.dto.Attachment;
import com.bharath.emailsend.dto.EmailInfo;
import com.bharath.emailsend.exception.EmailException;
import com.bharath.emailsend.utility.ReadDataFromExcel;
import com.bharath.emailsend.utility.Utility;

@Component
public class RequestToInfo {
	@Autowired
	private Utility utility;
	private ReadDataFromExcel r = new ReadDataFromExcel();

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

		List<Attachment> attach = getAttachments(multipartRequest, fileNamesl.get(1));

		info.setAttachments(attach);
		info.setPassword(multipartRequest.getParameter("password"));
		;

		return info;

	}

	public List<EmailInfo> getIndividualInfo(MultipartHttpServletRequest request) throws IOException {

		List<EmailInfo> list = new ArrayList<EmailInfo>();
		Iterator<String> fns = request.getFileNames();
		List<String> fileNames = new ArrayList<String>();

		while (fns.hasNext()) {
			fileNames.add(fns.next());
		}
		if (fileNames.isEmpty())
			throw new EmailException("no files added ");
		System.out.println(fileNames);
		MultipartFile excel = request.getFile(fileNames.get(0));
		if (excel == null || excel.isEmpty())
			throw new EmailException("error with excel");
		list = r.readEmailInfo(excel.getInputStream());
		list.stream().forEach(s -> {
			s.setFrom(request.getParameter("from"));
			s.setPassword(request.getParameter("password"));
			s.setSubject(request.getParameter("subject"));
			String message = "Dear " + s.getName() + "," + System.lineSeparator() + request.getParameter("message");

			s.setText(message);
			try {
				s.setAttachments(getAttachments(request, fileNames.get(1)));
			} catch (IOException e) {

				e.printStackTrace();
			}

		});

		return list;
	}

	public List<Attachment> getAttachments(MultipartHttpServletRequest multipartRequest, String filename)
			throws IOException {
		List<MultipartFile> attachments = multipartRequest.getFiles(filename);
		List<Attachment> attach = new ArrayList<>();
		for (MultipartFile att : attachments) {
			if (att != null && !att.isEmpty()) {
				Attachment a = new Attachment();
				a.setFile(att.getBytes());
				a.setFileName(att.getOriginalFilename());
				attach.add(a);
			}
		}
		return attach;
	}
}
// creting email infos based on input text
//String to = request.getParameter("to");
//if (to.contains(",")) {
//	List<String> toList = utility.convertArrayToList(to);
//	for (String to1 : toList) {
//		EmailInfo info = new EmailInfo();
//		toList = new ArrayList<String>();
//		toList.add(to1);
//		info.setToList(toList);
//		info.setAttachments(getAttachments(request, ""));
//	}
//}