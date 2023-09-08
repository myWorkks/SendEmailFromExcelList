package com.bharath.emailsend;

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

public class ReadDataFromExcel {
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
			if (to.matches("[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-zA-Z]+"))
				toList.add(to);
			else {
				String message = "to Email id invalid in Excel sheet with mail id" + to + " end";
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
			if (to.matches("[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-zA-Z]+"))
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
			if (to.matches("[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-zA-Z]+"))
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
}
