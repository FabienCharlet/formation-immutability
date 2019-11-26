package com.github.fabiencharlet.formation.immutability.service;

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

		service.changeRoueCreveeSurVoiture(voiture.getId());

		Truth.assertThat(voiture.getRoues().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BONNE)
				.count()).isEqualTo(5);
	}


}
