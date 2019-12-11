package com.github.fabiencharlet.formation.immutability.slides.immutablemodification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Person {

	private final String name;
	private final List<String> mails;

	public Person(String name, List<String> mails) {

		this.name = name;
		this.mails = Collections.unmodifiableList(mails);
	}
	public Person addMail(String mail) {

		List<String> newMails = new ArrayList<>(mails);
		newMails.add(mail);
		return new Person(name, newMails);
	}
	public String getName() {
		return name;
	}
	public List<String> getMails() {
		return mails;
	}
}
