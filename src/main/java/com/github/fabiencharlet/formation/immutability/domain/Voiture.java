package com.github.fabiencharlet.formation.immutability.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.fabiencharlet.formation.immutability.domain.Roue.Position;

public class Voiture implements Cloneable {

	private String id;
	private List<Roue> roues;

	public static Voiture nouvelle() {

		Voiture res = new Voiture();

		res.setId(UUID.randomUUID().toString());

		List<Roue> roues = new ArrayList<>();

		for (Position position : Position.values()) {

			roues.add(Roue.nouvelle(position));
		}

		res.setRoues(roues);

		return res;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Roue> getRoues() {
		return roues;
	}
	public void setRoues(List<Roue> roues) {
		this.roues = roues;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {

		Voiture res = (Voiture) super.clone();

		List<Roue> roues = new ArrayList<>();

		for (Roue roue : res.getRoues()) {

			roues.add((Roue) roue.clone());
		}

		res.setRoues(roues);

		return res;
	}
}
