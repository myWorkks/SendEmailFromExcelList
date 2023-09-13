package com.bharath.emailsend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	public final String validMail = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

	public List<String> convertArrayToList(String input) {
		System.out.println(input);
		List<String> list = new ArrayList<String>();
		if (input.length() > 0 && input.contains(",")) {
			String[] a = input.split(",");
			for (String s : a) {
				s = s.trim();
				if (s.matches(validMail)) {
					System.out.println(s);
					list.add(s);
				} else
					throw new EmailException("Invalid mail Id from form :" + s);
			}
			return list;
		} else if (input.length() > 0 && !input.contains(",")) {
			if (input.trim().matches(validMail)) {
				list.add(input);

				return list;
			} else
				throw new EmailException("Invalid mail Id from form :" + input);

		} else
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
