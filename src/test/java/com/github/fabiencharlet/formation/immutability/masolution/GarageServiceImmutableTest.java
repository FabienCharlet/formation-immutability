package com.github.fabiencharlet.formation.immutability.masolution;

import org.junit.Test;

import com.github.fabiencharlet.formation.immutability.masolution.domain.Position;
import com.github.fabiencharlet.formation.immutability.masolution.domain.ReparationCrevaison;
import com.github.fabiencharlet.formation.immutability.masolution.domain.Roue;
import com.github.fabiencharlet.formation.immutability.masolution.domain.Voiture;
import com.github.fabiencharlet.formation.immutability.masolution.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.masolution.service.GarageImmutalbeService;
import com.google.common.truth.Truth;

public class GarageServiceImmutableTest {

	@Test
	public void voitureAvecUneRoueCreveeEstBienReparee() {

		Voiture voiture = DataGenerator.voitureRoueCrevee();

		GarageImmutalbeService service = new GarageImmutalbeService();
		System.out.println(voiture.printEtatRoues());


		ReparationCrevaison reparation = service.changeRoueCreveeEtAdjacenteSurVoiture(
				voiture,
				Position.ARRIERE_CONDUCTEUR,
				DataGenerator.stockRoues());

		Truth.assertThat(reparation.getVoitureReparee().rouesAsList().stream()
				.map(Roue::getEtat)
				.filter(e -> e == Etat.BONNE)
				.count()).isEqualTo(5);
	}

	//@Test
	public void voitureRepareeEnParalleleEstBienReparee() {

		Voiture voiture = DataGenerator.voitureRoueCrevee();

		GarageImmutalbeService service = new GarageImmutalbeService();

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
