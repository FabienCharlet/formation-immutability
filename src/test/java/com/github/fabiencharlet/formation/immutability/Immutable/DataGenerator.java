package com.github.fabiencharlet.formation.immutability.Immutable;

import java.util.ArrayList;
import java.util.List;

import com.github.fabiencharlet.formation.immutability.immutable.domain.Roue;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Voiture;

public class DataGenerator {

	public static List<Roue> stockRoues() {

		List<Roue> stock = new ArrayList<>();

		for (int i = 0; i < 1_000; i++) {

			stock.add(Roue.nouvelle());
		}

		return stock;
	}

	public static Voiture voitureRoueCrevee() {

		Voiture voiture = Voiture.nouvelle();

		voiture = voiture
				.change(voiture.roueAvantConducteur, voiture.roueAvantConducteur.kilometresParcourues(10_000))
				.change(voiture.roueAvantPassager, voiture.roueAvantPassager.kilometresParcourues(10_000))
				.change(voiture.roueArriereConducteur, voiture.roueArriereConducteur.kilometresParcourues(10_000))
				.change(voiture.roueArrierePassager, voiture.roueArrierePassager.kilometresParcourues(10_000))
				.change(voiture.roueSecours, voiture.roueSecours.kilometresParcourues(80));

		Roue roueSecours = voiture.roueSecours;

		return voiture.
				change(voiture.roueSecours, voiture.roueArriereConducteur.crevee()).
				change(voiture.roueArriereConducteur, roueSecours);
	}

	public static void main(String[] args) {

		Voiture voiture = voitureRoueCrevee();

		System.out.println(voiture.roueSecours.etat);
	}
}
