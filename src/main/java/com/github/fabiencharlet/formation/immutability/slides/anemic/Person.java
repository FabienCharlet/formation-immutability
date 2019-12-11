package com.github.fabiencharlet.formation.immutability.slides.anemic;

import java.util.Arrays;
import java.util.List;

public class Person {

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

	public static void main(String[] args) {

		Person fabien = new Person();
		fabien.setName("Fabien");
		fabien.setMails(Arrays.asList("fabien@example.org"));
		fabien.getMails().add("fabien@example.com");

		fabien.setName(doesThisMethodReturnNull());

		fabien.getMails().remove(0);

		fabien.setName(null);
		fabien.setMails(null);

		fabien = new Person();
	}



	private static String doesThisMethodReturnNull() {

		return null;
	}
}
