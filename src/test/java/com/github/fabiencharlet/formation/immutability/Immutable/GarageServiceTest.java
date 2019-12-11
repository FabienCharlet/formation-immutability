package com.github.fabiencharlet.formation.immutability.Immutable;

import org.junit.Test;

import com.github.fabiencharlet.formation.immutability.immutable.domain.Position;
import com.github.fabiencharlet.formation.immutability.immutable.domain.ReparationCrevaison;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Roue;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Voiture;
import com.github.fabiencharlet.formation.immutability.immutable.service.GarageService;
import com.google.common.truth.Truth;

public class GarageServiceTest {

	@Test
	public void voitureAvecUneRoueCreveeEstBienReparee() {

		Voiture voiture = DataGenerator.voitureRoueCrevee();

		GarageService service = new GarageService();

		ReparationCrevaison reparation = service.changeRoueCreveeEtAdjacenteSurVoiture(
				voiture,
				Position.ARRIERE_CONDUCTEUR,
				DataGenerator.stockRoues());

		Truth.assertThat(reparation.getVoitureReparee().rouesAsList().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BONNE)
				.count()).isEqualTo(5);
	}

	@Test
	public void voitureRepareeEnParalleleEstBienReparee() {

		Voiture voiture = DataGenerator.voitureRoueCrevee();

		GarageService service = new GarageService();

		ReparationCrevaison reparation = service.changeRoueCreveeEtAdjacenteEnParalleleSurVoiture(
				voiture,
				Position.ARRIERE_CONDUCTEUR,
				DataGenerator.stockRoues());

		Truth.assertThat(reparation.getVoitureReparee().rouesAsList().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BONNE)
				.count()).isEqualTo(5);
	}


}
