package com.github.fabiencharlet.formation.immutability.immutable.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.fabiencharlet.formation.immutability.immutable.domain.ChangementRoue;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Position;
import com.github.fabiencharlet.formation.immutability.immutable.domain.ReparationCrevaison;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Roue;
import com.github.fabiencharlet.formation.immutability.immutable.domain.Voiture;

public class GarageService {

	public ReparationCrevaison changeRoueCreveeEtAdjacenteEnParalleleSurVoiture(
			Voiture voiture,
			Position positionRoueCrevee,
			List<Roue> stockRoues) {

		final List<ChangementRoue> changements = Collections.synchronizedList(new ArrayList<>());

		Thread changeRoueCreveeThread = new Thread() {

			@Override
			public void run() {

				changements.addAll(changeRoueCrevee(voiture, positionRoueCrevee, stockRoues.remove(0)));
			}
		};

		Thread changeRoueAdjacenteThread = new Thread() {

			@Override
			public void run() {

				changements.add(changeRoueAdjacente(voiture, positionRoueCrevee, stockRoues.remove(0)));
			}
		};

		changeRoueCreveeThread.run();
		changeRoueAdjacenteThread.run();


		try {
			changeRoueCreveeThread.join();
			changeRoueAdjacenteThread.join();

		} catch (Exception e) {

			throw new RuntimeException("Thread problems");
		}

		return ReparationCrevaison.from(voiture, changements);
	}

	public ReparationCrevaison changeRoueCreveeEtAdjacenteSurVoiture(
			Voiture voiture,
			Position positionRoueCrevee,
			List<Roue> stockRoues) {

		final List<ChangementRoue> changements = Collections.synchronizedList(new ArrayList<>());

		changements.addAll(changeRoueCrevee(voiture, positionRoueCrevee, stockRoues.remove(0)));
		changements.add(changeRoueAdjacente(voiture, positionRoueCrevee, stockRoues.remove(0)));

		return ReparationCrevaison.from(voiture, changements);
	}

	public ChangementRoue changeRoueAdjacente(Voiture voiture, Position positionRoueCrevee, Roue nouvelleRoue) {

		return new ChangementRoue(voiture.roueAdjacenteAt(positionRoueCrevee), nouvelleRoue);
	}

	public List<ChangementRoue> changeRoueCrevee(Voiture voiture, Position positionRoueCrevee, Roue nouvelleRoue) {

		return Arrays.asList(
				new ChangementRoue(voiture.roueSecours, voiture.roueAt(positionRoueCrevee)),
				new ChangementRoue(voiture.roueAt(positionRoueCrevee), nouvelleRoue));
	}
}
