package com.bharath.emailsend;

import java.util.Arrays;
import java.util.List;

public class EmailInfo {
	private List<String> toList;

	private String from;
	private List<String> ccList;
	private List<String> bccList;
	private String subject;
	private String text;

	public List<String> getToList() {
		return toList;
	}

	public void setToList(List<String> toList) {
		this.toList = toList;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public List<String> getBccList() {
		return bccList;
	}

	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "EmailInfo [toList=" + toList + ", from=" + from + ", ccList=" + ccList + ", bccList=" + bccList
				+ ", subject=" + subject + ", text=" + text + "]";
	}

}
