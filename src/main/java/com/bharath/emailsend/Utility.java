package com.bharath.emailsend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	public List<String> convertArrayToList(String input) {
		System.out.println(input);
		List<String> list = new ArrayList<String>();
		if (input.length() > 0 && input.contains(",")) {
			String[] a = input.split(",");
			for (String s : a) {
				if (s.matches("[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-zA-Z]+")) {
					System.out.println(s);
					list.add(s);
				} else
					throw new EmailException("Invalid mail Id :" + s);
			}
		}
		if (input.length() > 0 && input.matches("[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-zA-Z]+"))
			list.add(input);
		else
			throw new EmailException("Invalid mail Id :" + input);
		return list;
	}

	public String[] listToArray(List<String> s) {
		if (s != null && !s.isEmpty()) {
			String[] ss = new String[s.size()];
			for (int i = 0; i < ss.length; i++) {
				ss[i] = s.get(i);
			}
			return ss;
		}
		return new String[] {};
	}
}
