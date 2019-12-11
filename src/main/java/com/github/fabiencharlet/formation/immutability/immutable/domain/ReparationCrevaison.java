package com.github.fabiencharlet.formation.immutability.immutable.domain;

import java.util.ArrayList;
import java.util.List;

public class ReparationCrevaison {

	public static ReparationCrevaison from(
			Voiture voitureAReparer,
			List<ChangementRoue> changements) {

		List<Roue> rouesARecycler = new ArrayList<>();
		Voiture voitureTemp = voitureAReparer;

		for (ChangementRoue changementRoue : changements) {

			voitureTemp = voitureTemp.applique(changementRoue);

			if ( voitureTemp.rouesAsList().contains(changementRoue.ancienneRoue) ) {

				rouesARecycler.add(changementRoue.ancienneRoue);
			}
		}

		return new ReparationCrevaison(voitureTemp, rouesARecycler);
	}

	private final Voiture voitureReparee;
	private final List<Roue> rouesARecycler;

	private ReparationCrevaison(Voiture voitureReparee, List<Roue> rouesARecycler) {

		if (rouesARecycler.size() != 2) {

			throw new RuntimeException("Roues à recycler en mauvais nombre : " + rouesARecycler.size());
		}

		this.voitureReparee = voitureReparee;
		this.rouesARecycler = rouesARecycler;
	}

	public Voiture getVoitureReparee() {
		return voitureReparee;
	}

	public List<Roue> getRouesARecycler() {
		return rouesARecycler;
	}
}
