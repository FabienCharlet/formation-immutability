package com.github.fabiencharlet.formation.immutability.slides.immutablebuilders;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;

public final class Person {

	public static Person withOneMail(String name, String mail) {

		return new Person(name, Arrays.asList(mail));
	}

	private final String name;
	private final List<String> mails;

	private Person(String name, List<String> mails) {

		if (Strings.isNullOrEmpty(name))
			throw new InvalidParameterException("Name is empty");

		if (mails == null || mails.isEmpty())
			throw new InvalidParameterException("No mails supplied");

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

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Person fabien = Person
				.withOneMail("Fabien", "fabien@example.org")
				.addMail("fabien@example.com");
	}
}
