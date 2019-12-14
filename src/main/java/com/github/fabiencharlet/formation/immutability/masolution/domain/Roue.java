package com.github.fabiencharlet.formation.immutability.masolution.domain;

import java.util.UUID;

import com.github.fabiencharlet.formation.immutability.masolution.util.Validators;

public final class Roue {

	public enum Etat {

		BONNE,
		CREVEE
	}

	public static Roue nouvelle() {

		return new Roue(UUID.randomUUID().toString(), 0, Etat.BONNE);
	}

	public final String id;
	public final int kilometrage;
	public final Etat etat;

	private Roue(String id, int kilometres, Etat etat) {

		this.id = Validators.checkNotNull(id);
		this.kilometrage = kilometres;
		this.etat = Validators.checkNotNull(etat);
	}

	public Roue crevee() {

		return new Roue(id, kilometrage, Etat.CREVEE);
	}

	public Roue kilometresParcourus(int km) {

		return new Roue(id, kilometrage + km, etat);
	}

	public Etat getEtat() {
		return etat;
	}

	public boolean isCrevee() {

		return etat == Etat.CREVEE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + kilometrage;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roue other = (Roue) obj;
		if (etat != other.etat)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kilometrage != other.kilometrage)
			return false;
		return true;
	}



}
