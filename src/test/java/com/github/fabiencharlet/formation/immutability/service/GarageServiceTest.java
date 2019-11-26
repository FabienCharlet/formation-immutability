package com.github.fabiencharlet.formation.immutability.service;

import java.util.List;

import org.junit.Test;

import com.github.fabiencharlet.formation.immutability.db.FakeDB;
import com.github.fabiencharlet.formation.immutability.domain.Roue;
import com.github.fabiencharlet.formation.immutability.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.domain.Voiture;
import com.github.fabiencharlet.formation.immutability.util.DataGenerator;
import com.google.common.truth.Truth;

public class GarageServiceTest {

	@Test
	public void voitureAvecUneRoueCreveeEstBienReparee() {

		FakeDB<Voiture> voituresRepo = DataGenerator.voitureRoueCrevee();
		Voiture voiture = voituresRepo.select(voituresRepo.firstId());

		GarageService service = new GarageService(voituresRepo, DataGenerator.stockRoues());

		List<Roue> rouesChangees = service.changeRoueCreveeSurVoiture(voiture.getId());


		for (Roue roue : voiture.getRoues()) {

			if (roue.getEtat() == Etat.CREVEE)
				System.out.println(roue.getPosition());
		}


		Truth.assertThat(voiture.getRoues().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BON)
				.count()).isEqualTo(5);

		Truth.assertThat(rouesChangees).hasSize(2);

		for (Roue roue : rouesChangees) {

			System.out.println(roue.getPosition());
		}
	}


}
