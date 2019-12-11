package com.github.fabiencharlet.formation.immutability.slides.anemicfinal;

import java.util.List;

public final class Person {

	private String name;
	private List<String> mails;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getMails() {
		return mails;
	}
	public void setMails(List<String> mails) {
		this.mails = mails;
	}
}
