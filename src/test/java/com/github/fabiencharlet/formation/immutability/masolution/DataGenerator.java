package com.github.fabiencharlet.formation.immutability.masolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.fabiencharlet.formation.immutability.masolution.domain.ChangementRoue;
import com.github.fabiencharlet.formation.immutability.masolution.domain.Roue;
import com.github.fabiencharlet.formation.immutability.masolution.domain.Voiture;

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
				.change(voiture.roueAvantConducteur, voiture.roueAvantConducteur.kilometresParcourus(10_000))
				.change(voiture.roueAvantPassager, voiture.roueAvantPassager.kilometresParcourus(10_000))
				.change(voiture.roueArriereConducteur, voiture.roueArriereConducteur.kilometresParcourus(10_000))
				.change(voiture.roueArrierePassager, voiture.roueArrierePassager.kilometresParcourus(10_000))
				.change(voiture.roueSecours, voiture.roueSecours.kilometresParcourus(80));

		Roue roueSecours = voiture.roueSecours;

		return voiture.
				change(voiture.roueSecours, voiture.roueArriereConducteur.crevee()).
				change(voiture.roueArriereConducteur, roueSecours);
	}

	public static void main(String[] args) {

		Voiture voiture = voitureRoueCrevee();

		System.out.println("Début");
		System.out.println(voiture.printEtatRoues());

		System.out.println("\nRemise roue de secours en place");

		List<ChangementRoue> chgts = Arrays.asList(
				new ChangementRoue(voiture.roueSecours, voiture.roueArriereConducteur),
				new ChangementRoue(voiture.roueArriereConducteur, Roue.nouvelle()),
				new ChangementRoue(voiture.roueArrierePassager, Roue.nouvelle()));

		for (ChangementRoue changementRoue : chgts) {

			voiture = voiture.applique(changementRoue);
		}


	}
}
