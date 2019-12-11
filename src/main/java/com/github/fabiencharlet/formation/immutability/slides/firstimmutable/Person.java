package com.github.fabiencharlet.formation.immutability.slides.firstimmutable;

import java.util.Collections;
import java.util.List;

public final class Person {

	private final String name;
	private final List<String> mails;

	public Person(String name, List<String> mails) {

		this.name = name;
		this.mails = Collections.unmodifiableList(mails);
	}
	public String getName() {
		return name;
	}
	public List<String> getMails() {
		return mails;
	}
}
