package com.github.fabiencharlet.formation.immutability.immutable.domain;

import java.util.UUID;

import com.github.fabiencharlet.formation.immutability.immutable.util.Validators;

public class Roue {

	public enum Etat {

		BONNE,
		CREVEE
	}

	public static Roue nouvelle() {

		return new Roue(UUID.randomUUID().toString(), 0, Etat.BONNE);
	}

	public final String id;
	public final int kilometres;
	public final Etat etat;

	private Roue(String id, int kilometres, Etat etat) {

		this.id = Validators.checkNotNull(id, "id");
		this.kilometres = kilometres;
		this.etat = Validators.checkNotNull(etat, "etat");
	}

	public Roue crevee() {

		return new Roue(id, kilometres, Etat.CREVEE);
	}

	public Roue kilometresParcourues(int km) {

		return new Roue(id, kilometres + km, etat);
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
		result = prime * result + kilometres;
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
		if (kilometres != other.kilometres)
			return false;
		return true;
	}



}
