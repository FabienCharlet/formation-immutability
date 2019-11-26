package com.github.fabiencharlet.formation.immutability.domain;

import java.util.UUID;

public class Roue implements Cloneable {

	public enum Etat {

		BON,
		CREVEE
	}

	public enum Position {

		AVANT_CONDUCTEUR,
		AVANT_PASSAGER,
		ARRIERE_PASSAGER,
		ARRIERE_CONDUCTEUR,
		SECOURS
	}

	public static Roue nouvelle(Position position) {

		Roue res = new Roue();

		res.setId(UUID.randomUUID().toString());
		res.setEtat(Etat.BON);
		res.setKilometres(0);
		res.setPosition(position);

		return res;
	}

	private String id;
	private int kilometres;
	private Etat etat;
	private Position position;
	private Position positionPrecedente;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getKilometres() {
		return kilometres;
	}
	public void setKilometres(int kilometres) {
		this.kilometres = kilometres;
	}
	public Etat getEtat() {
		return etat;
	}
	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Position getPositionPrecedente() {
		return positionPrecedente;
	}
	public void setPositionPrecedente(Position positionPrecedente) {
		this.positionPrecedente = positionPrecedente;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
