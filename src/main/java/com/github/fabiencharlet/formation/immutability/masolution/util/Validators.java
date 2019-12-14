package com.github.fabiencharlet.formation.immutability.masolution.util;

public class Validators {

	public static <T> T checkNotNull(T o, String name) {

		if (o == null) {

			throw new RuntimeException(name + " was null");
		}

		return o;
	}

	public static <T> T checkNotNull(T o) {

		if (o == null) {

			throw new RuntimeException("Object was null");
		}

		return o;
	}
}
