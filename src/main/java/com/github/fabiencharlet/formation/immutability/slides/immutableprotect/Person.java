package com.github.fabiencharlet.formation.immutability.slides.immutableprotect;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;

public final class Person {

	private final String name;
	private final List<String> mails;

	public Person(String name, List<String> mails) {

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
}
