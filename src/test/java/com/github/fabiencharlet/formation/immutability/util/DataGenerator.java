package com.github.fabiencharlet.formation.immutability.util;

import java.util.Arrays;
import java.util.List;

import com.github.fabiencharlet.formation.immutability.db.FakeDB;
import com.github.fabiencharlet.formation.immutability.domain.Roue;
import com.github.fabiencharlet.formation.immutability.domain.Roue.Etat;
import com.github.fabiencharlet.formation.immutability.domain.Roue.Position;
import com.github.fabiencharlet.formation.immutability.domain.Voiture;

public class DataGenerator {

	public static List<Voiture> basicSampleDataInDb() {

		Voiture v = Voiture.nouvelle();

		FakeDB<Object> repo = new FakeDB<>();

		repo.insert(v.getId(), v);

		return Arrays.asList(v);
	}


	public static FakeDB<Roue> stockRoues() {

		FakeDB<Roue> repo = new FakeDB<>();

		for (int i = 0; i < 1_000; i++) {

			Roue roue = Roue.nouvelle(null);
			repo.insert(roue.getId(), roue);
		}

		return repo;
	}

	public static FakeDB<Voiture> voitureRoueCrevee() {

		FakeDB<Voiture> repo = new FakeDB<>();
		Voiture voiture = Voiture.nouvelle();

		List<Roue> roues = voiture.getRoues();

		Roue roueCrevee = roues.get(2);
		Roue roueSecours = roues.get(4);

		roueSecours.setPosition(roueCrevee.getPosition());
		roueSecours.setPositionPrecedente(Position.SECOURS);

		roueCrevee.setEtat(Etat.CREVEE);
		roueCrevee.setPositionPrecedente(roueCrevee.getPosition());
		roueCrevee.setPosition(Position.SECOURS);

		repo.insert(voiture.getId(), voiture);

		return repo;
	}


	public static void main(String[] args) {

		List<Voiture> voitures = basicSampleDataInDb();
		String vId = voitures.get(0).getId();



		FakeDB<Voiture> repo = new FakeDB<>();

		Voiture v1 = repo.select(vId);
		Voiture v2 = repo.select(vId);

		System.out.println(v2.getRoues().get(0) == v1.getRoues().get(0));
	}
}
