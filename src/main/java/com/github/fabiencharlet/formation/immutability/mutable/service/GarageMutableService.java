package com.github.fabiencharlet.formation.immutability.mutable.service;

import java.util.Arrays;
import java.util.List;

import com.github.fabiencharlet.formation.immutability.mutable.db.FakeDB;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Roue;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Roue.Position;
import com.github.fabiencharlet.formation.immutability.mutable.domain.Voiture;

public class GarageMutableService {

	private FakeDB<Voiture> voitureRepository;
	private FakeDB<Roue> stockRouesRepository;

	public GarageMutableService(FakeDB<Voiture> voitureRepository, FakeDB<Roue> stockRouesRepository) {

		this.voitureRepository = voitureRepository;
		this.stockRouesRepository = stockRouesRepository;
	}

	public List<Roue> changeRoueCreveeEtAdjacenteEnParalleleSurVoiture(String idVoiture) {

		Voiture voiture = voitureRepository.select(idVoiture);

		List<Roue> roues = voiture.getRoues();

		Roue roue = null;

		for (int i = 0; i < roues.size(); i++) {

			roue = roues.get(i);

			if (roue.getEtat() == Etat.CREVEE) {

				break;
			}
		}

		final Roue roueCrevee = roue;

		Thread changeRoueCreveeThread = new Thread() {

			@Override
			public void run() {

				changeRoueCrevee(voiture, roueCrevee);
			}
		};

		final Roue[] roueAdjacente = new Roue[1];

		Thread changeRoueAdjacenteThread = new Thread() {

			@Override
			public void run() {

			 roueAdjacente[0] = changeRoueAdjacente(voiture, roueCrevee);
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

		voitureRepository.update(idVoiture, voiture);

		return Arrays.asList(roue, roueAdjacente[0]);
	}

	public List<Roue> changeRoueCreveeEtAdjacenteSurVoiture(String idVoiture) {

		Voiture voiture = voitureRepository.select(idVoiture);

		List<Roue> roues = voiture.getRoues();

		Roue roue = null;

		for (int i = 0; i < roues.size(); i++) {

			roue = roues.get(i);

			if (roue.getEtat() == Etat.CREVEE) {

				break;
			}
		}

		changeRoueCrevee(voiture, roue);
		Roue roueAdjacente = changeRoueAdjacente(voiture, roue);

		voitureRepository.update(idVoiture, voiture);

		return Arrays.asList(roue, roueAdjacente);
	}

	public Roue changeRoueAdjacente(Voiture voiture, Roue roueCrevee) {

		Position position = roueCrevee.getPositionPrecedente();
		List<Roue> roues = voiture.getRoues();
		Position positionAChanger = null;

		switch (position) {
		case ARRIERE_CONDUCTEUR:
			positionAChanger = Position.ARRIERE_PASSAGER;
			break;
		case ARRIERE_PASSAGER:
			positionAChanger = Position.ARRIERE_CONDUCTEUR;
			break;
		case AVANT_CONDUCTEUR:
			positionAChanger = Position.AVANT_PASSAGER;
			break;
		case AVANT_PASSAGER:
			positionAChanger = Position.AVANT_CONDUCTEUR;
			break;
		case SECOURS:
			throw new RuntimeException("Aucune position précédente valide de la roue de secours");
		}

		Roue roueChangee = null;

		for (int j = 0; j < roues.size(); j++) {

			Roue roueAChanger = roues.get(j);

			if (roueAChanger.getPosition() == positionAChanger) {

				roues.remove(roueAChanger);
				roueChangee = roueAChanger;

				String rId = stockRouesRepository.firstId();
				Roue nouvelleRoue = stockRouesRepository.select(rId);

				nouvelleRoue.setPosition(positionAChanger);
				roues.add(nouvelleRoue);

				stockRouesRepository.delete(rId);
			}
		}

		return roueChangee;
	}

	public void changeRoueCrevee(Voiture voiture, Roue roueCrevee) {

		List<Roue> roues = voiture.getRoues();

		roues.remove(roueCrevee);

		String rId = stockRouesRepository.firstId();
		Roue nouvelleRoue = stockRouesRepository.select(rId);
		// -> Plante si 2 mêmes roues autre méthode a déjà supprimé la roue

		nouvelleRoue.setPosition(roueCrevee.getPositionPrecedente());
		roues.add(nouvelleRoue);

		stockRouesRepository.delete(rId);
	}
}
