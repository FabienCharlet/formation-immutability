package com.github.fabiencharlet.formation.immutability.immutable.domain;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.fabiencharlet.formation.immutability.immutable.util.Validators;

public class Voiture {

	public static Voiture nouvelle() {

		return new Voiture(UUID.randomUUID().toString(),
				Roue.nouvelle(),
				Roue.nouvelle(),
				Roue.nouvelle(),
				Roue.nouvelle(),
				Roue.nouvelle());
	}

	public final String id;

	public final Roue roueAvantConducteur;
	public final Roue roueAvantPassager;
	public final Roue roueArriereConducteur;
	public final Roue roueArrierePassager;
	public final Roue roueSecours;


	private Voiture(
			String id,
			Roue roueAvantConducteur,
			Roue roueAvantPassager,
			Roue roueArriereConducteur,
			Roue roueArrierePassager,
			Roue roueSecours) {

		this.id = Validators.checkNotNull(id);

		this.roueAvantConducteur = Validators.checkNotNull(roueAvantConducteur);
		this.roueAvantPassager = Validators.checkNotNull(roueAvantPassager);
		this.roueArriereConducteur = Validators.checkNotNull(roueArriereConducteur);
		this.roueArrierePassager = Validators.checkNotNull(roueArrierePassager);
		this.roueSecours = Validators.checkNotNull(roueSecours);
	}

	public List<Roue> rouesAsList() {

		return Arrays.asList(
				roueAvantConducteur,
				roueAvantPassager,
				roueArriereConducteur,
				roueArrierePassager,
				roueSecours);
	}

	public Roue roueAt(Position position) {

		switch (position) {
			case ARRIERE_CONDUCTEUR: return roueArriereConducteur;
			case ARRIERE_PASSAGER:   return roueArrierePassager;
			case AVANT_CONDUCTEUR:   return roueAvantConducteur;
			case AVANT_PASSAGER:     return roueAvantPassager;
			case SECOURS: 			 return roueSecours;
		}

		throw new RuntimeException("Position inconnue : " + position);
	}

	public Roue roueAdjacenteAt(Position position) {

		switch (position) {
			case ARRIERE_CONDUCTEUR: return roueArrierePassager;
			case ARRIERE_PASSAGER:   return roueArriereConducteur;
			case AVANT_CONDUCTEUR:   return roueAvantPassager;
			case AVANT_PASSAGER:     return roueAvantConducteur;
			case SECOURS: 			 return roueSecours;
		}

		throw new RuntimeException("Position inconnue : " + position);
	}

	public Voiture applique(ChangementRoue changement) {

		return change(changement.ancienneRoue, changement.ancienneRoue);
	}

	public Voiture change(Roue roueARemplacer, Roue nouvelleRoue) {

		if (roueARemplacer.isCrevee() && nouvelleRoue.isCrevee()) {
			throw new RuntimeException("On ne remplace pas une roue crevée par une autre roue crevée");
		}

		return new Voiture(
				id,
				roueAvantConducteur.equals(roueARemplacer) ? nouvelleRoue : roueAvantConducteur,
				roueAvantPassager.equals(roueARemplacer) ? nouvelleRoue : roueAvantPassager,
				roueArriereConducteur.equals(roueARemplacer) ? nouvelleRoue : roueArriereConducteur,
				roueArrierePassager.equals(roueARemplacer) ? nouvelleRoue : roueArrierePassager,
				roueSecours.equals(roueARemplacer) ? nouvelleRoue : roueSecours);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((roueArriereConducteur == null) ? 0 : roueArriereConducteur.hashCode());
		result = prime * result + ((roueArrierePassager == null) ? 0 : roueArrierePassager.hashCode());
		result = prime * result + ((roueAvantConducteur == null) ? 0 : roueAvantConducteur.hashCode());
		result = prime * result + ((roueAvantPassager == null) ? 0 : roueAvantPassager.hashCode());
		result = prime * result + ((roueSecours == null) ? 0 : roueSecours.hashCode());
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
		Voiture other = (Voiture) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (roueArriereConducteur == null) {
			if (other.roueArriereConducteur != null)
				return false;
		} else if (!roueArriereConducteur.equals(other.roueArriereConducteur))
			return false;
		if (roueArrierePassager == null) {
			if (other.roueArrierePassager != null)
				return false;
		} else if (!roueArrierePassager.equals(other.roueArrierePassager))
			return false;
		if (roueAvantConducteur == null) {
			if (other.roueAvantConducteur != null)
				return false;
		} else if (!roueAvantConducteur.equals(other.roueAvantConducteur))
			return false;
		if (roueAvantPassager == null) {
			if (other.roueAvantPassager != null)
				return false;
		} else if (!roueAvantPassager.equals(other.roueAvantPassager))
			return false;
		if (roueSecours == null) {
			if (other.roueSecours != null)
				return false;
		} else if (!roueSecours.equals(other.roueSecours))
			return false;
		return true;
	}


}
