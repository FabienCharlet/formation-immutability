package com.github.fabiencharlet.formation.immutability.mutable;

import org.junit.Test;

import com.github.fabiencharlet.formation.immutability.mutable.db.FakeDB;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Roue;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.mutable.service.GarageService;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Voiture;
import com.google.common.truth.Truth;

public class GarageServiceTest {

	@Test
	public void voitureAvecUneRoueCreveeEstBienReparee() {

		FakeDB<Voiture> voituresRepo = DataGenerator.voitureRoueCrevee();
		Voiture voiture = voituresRepo.select(voituresRepo.firstId());

		GarageService service = new GarageService(voituresRepo, DataGenerator.stockRoues());

		service.changeRoueCreveeEtAdjacenteSurVoiture(voiture.getId());

		Truth.assertThat(voiture.getRoues().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BONNE)
				.count()).isEqualTo(5);
	}

	@Test
	public void voitureRepareeEnParalleleEstBienReparee() {

		FakeDB<Voiture> voituresRepo = DataGenerator.voitureRoueCrevee();
		Voiture voiture = voituresRepo.select(voituresRepo.firstId());

		GarageService service = new GarageService(voituresRepo, DataGenerator.stockRoues());

		service.changeRoueCreveeEtAdjacenteEnParalleleSurVoiture(voiture.getId());

		Truth.assertThat(voiture.getRoues().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BONNE)
				.count()).isEqualTo(5);
	}


}
