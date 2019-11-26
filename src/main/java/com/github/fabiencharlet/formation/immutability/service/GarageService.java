package com.github.fabiencharlet.formation.immutability.service;

import java.util.ArrayList;
import java.util.List;

import com.github.fabiencharlet.formation.immutability.db.FakeDB;
import com.github.fabiencharlet.formation.immutability.domain.Roue;
import com.github.fabiencharlet.formation.immutability.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.domain.Roue.Position;
import com.github.fabiencharlet.formation.immutability.domain.Voiture;

public class GarageService {

	private FakeDB<Voiture> voitureRepository;
	private FakeDB<Roue> stockRouesRepository;


	public GarageService(FakeDB<Voiture> voitureRepository, FakeDB<Roue> stockRouesRepository) {

		this.voitureRepository = voitureRepository;
		this.stockRouesRepository = stockRouesRepository;

	}

	public List<Roue> changeRoueCreveeSurVoiture(String idVoiture) {

		Voiture voiture = voitureRepository.select(idVoiture);

		List<Roue> rouesChangees = new ArrayList<Roue>();
		List<Roue> roues = voiture.getRoues();

		for (int i = 0; i < roues.size(); i++) {

			Roue roue = roues.get(i);

			if (roue.getEtat() == Etat.CREVEE) {

				roues.remove(roue);
				rouesChangees.add(roue);

				String rId = stockRouesRepository.firstId();
				Roue nouvelleRoue = stockRouesRepository.select(rId);

				nouvelleRoue.setPosition(roue.getPositionPrecedente());
				roues.add(nouvelleRoue);

				stockRouesRepository.delete(rId);

				Position position = roue.getPositionPrecedente();
				Position positionAChanger = null;

				switch (position) {
					case ARRIERE_CONDUCTEUR: positionAChanger = Position.ARRIERE_PASSAGER;
						break;
					case ARRIERE_PASSAGER: positionAChanger = Position.ARRIERE_CONDUCTEUR;
						break;
					case AVANT_CONDUCTEUR: positionAChanger = Position.AVANT_PASSAGER;
						break;
					case AVANT_PASSAGER: positionAChanger = Position.AVANT_CONDUCTEUR;
						break;
					case SECOURS: throw new RuntimeException("Aucune position précédente valide de la roue de secours");
				}

				for (int j = 0; j < roues.size(); j++) {

					Roue roueAChanger = roues.get(j);

					if (roueAChanger.getPosition() == positionAChanger) {

						roues.remove(roueAChanger);
						rouesChangees.add(roueAChanger);

						rId = stockRouesRepository.firstId();
						nouvelleRoue = stockRouesRepository.select(rId);

						nouvelleRoue.setPosition(positionAChanger);
						roues.add(nouvelleRoue);

						stockRouesRepository.delete(rId);
					}
				}
			}
		}

		voitureRepository.update(idVoiture, voiture);

		return rouesChangees;
	}
}
