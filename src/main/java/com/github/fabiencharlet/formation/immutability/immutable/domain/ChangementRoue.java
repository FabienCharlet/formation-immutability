package com.github.fabiencharlet.formation.immutability.immutable.domain;

import com.github.fabiencharlet.formation.immutability.immutable.util.Validators;

public class ChangementRoue {

	public final Roue ancienneRoue;
	public final Roue nouvelleRoue;

	public ChangementRoue(Roue ancienneRoue, Roue nouvelleRoue) {

		this.ancienneRoue = Validators.checkNotNull(ancienneRoue, "ancienneRoue");
		this.nouvelleRoue = Validators.checkNotNull(nouvelleRoue, "nouvelleRoue");

		if (ancienneRoue.id.equals(nouvelleRoue.id)) {
			throw new RuntimeException("Les deux roues sont identiques");
		}
	}



}
