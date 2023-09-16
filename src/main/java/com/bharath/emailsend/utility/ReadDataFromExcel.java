package com.bharath.emailsend.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bharath.emailsend.dto.EmailInfo;
import com.bharath.emailsend.exception.EmailException;

public class ReadDataFromExcel {
	Utility u = new Utility();

	public EmailInfo readData(InputStream stream, EmailInfo info) {
		// System.out.println("reader called");

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(stream);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		XSSFSheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();
		// System.out.println("to rows"+rows);
//to list
		List<String> toList = new ArrayList<String>();
		DataFormatter df = new DataFormatter();
		for (int i = 0; i < rows - 1; i++) {

			String to = df.formatCellValue(sheet.getRow(i + 1).getCell(0));
			if (to.length() == 0)
				break;
			if (to.trim().matches(u.validMail))
				toList.add(to);
			else {
				String message = "To Email id invalid in Excel sheet with mail id :" + to;
				throw new EmailException(message);
			}
		}
//cc list
		sheet = workbook.getSheetAt(1);

		rows = sheet.getPhysicalNumberOfRows();
		// System.out.println("cc rows" + rows);
		List<String> ccList = new ArrayList<String>();

		for (int i = 0; i < rows - 1; i++) {

			String to = df.formatCellValue(sheet.getRow(i + 1).getCell(0));

			if (to.length() == 0)
				break;
			if (to.trim().matches(u.validMail))
				ccList.add(to);
			else {
				String message = "cc Email id invalid in Excel sheet with mail id " + to;
				throw new EmailException(message);
			}
		}
//bccList
		sheet = workbook.getSheetAt(2);

		rows = sheet.getPhysicalNumberOfRows();
		// System.out.println("bcc rows" + rows);
		List<String> bccList = new ArrayList<String>();

		for (int i = 0; i < rows - 1; i++) {

			String to = df.formatCellValue(sheet.getRow(i + 1).getCell(0));
			if (to.length() == 0)
				break;
			if (to.trim().matches(u.validMail))
				bccList.add(to);
			else {
				String message = "bcc Email id invalid in Excel sheet with mail id " + to;
				throw new EmailException(message);
			}
		}
//		System.out.println("bccList" + bccList);
//		System.out.println(ccList);
//		System.out.println("toList" + toList);
		if (!bccList.isEmpty())
			info.setBccList(bccList);
		if (!ccList.isEmpty())
			info.setCcList(ccList);
		if (!toList.isEmpty())
			info.setToList(toList);
		// System.out.println(info);
		try {
			stream.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	public List<EmailInfo> readEmailInfo(InputStream fis) {
		List<EmailInfo> list = new ArrayList<EmailInfo>();
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		XSSFSheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();
		DataFormatter df = new DataFormatter();
		
		for (int i = 1; i < rows; i++) {
			List<String> toList = new ArrayList<String>();
			EmailInfo info = new EmailInfo();

			info.setName(df.formatCellValue(sheet.getRow(i).getCell(0)));
			// to
			String to = df.formatCellValue(sheet.getRow(i).getCell(1));
			if (to.length() > 0) {
				if (to.contains(",")) {
					info.setToList(u.convertArrayToList(to));
				} else {
					if (to.trim().matches(u.validMail)) {
						toList.add(to);
						info.setToList(toList);
					} else
						throw new EmailException("invalid mailid in row " + i + "with mail " + to);
				}
			}
			// cc
			to = df.formatCellValue(sheet.getRow(i).getCell(2));
			if (to.length() > 0) {
				if (to.contains(",")) {
					info.setCcList(u.convertArrayToList(to));
				} else {
					if (to.trim().matches(u.validMail)) {
						toList=new ArrayList<String>();
						toList.add(to);
						info.setCcList(toList);
					} else
						throw new EmailException("invalid mailid in row " + i + "with mail " + to);
				}
			}

			// bcc
			to = df.formatCellValue(sheet.getRow(i).getCell(3));
			if (to.length() > 0) {
				if (to.contains(",")) {
					info.setBccList(u.convertArrayToList(to));
				} else {
					if (to.trim().matches(u.validMail)) {
						toList=new ArrayList<String>();
						toList.add(to);
						info.setBccList(toList);
					} else
						throw new EmailException("invalid mailid in row " + i + "with mail " + to);
				}
			}
			list.add(info);
		}
		return list;
	}
}
