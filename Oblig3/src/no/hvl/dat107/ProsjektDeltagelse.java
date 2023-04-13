package no.hvl.dat107;

import java.time.LocalDate;
import java.time.Month;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "ansattO1")
public class ProsjektDeltagelse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deltagelseId;

	private LocalDate startdato;
	private int antallTimer;
	private String rolle;

	@ManyToOne
	@JoinColumn(name = "Ansatt")
	private Ansatt ansatt;

	@ManyToOne
	@JoinColumn(name = "Prosjekt")
	private Prosjekt prosjekt;

	public ProsjektDeltagelse() {
	}

	public ProsjektDeltagelse(int dag, Month maane, int aar, int antallTimer, Ansatt ansatt, Prosjekt prosjekt) {

		LocalDate prosjektDato = LocalDate.of(aar, maane, dag);

		this.startdato = prosjektDato;
		this.antallTimer = antallTimer;
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;

	}

	public int getDeltagelsesId() {
		return deltagelseId;
	}

	public void setDeltagelsesId(int deltagelsesId) {
		this.deltagelseId = deltagelsesId;
	}

	public LocalDate getStartdato() {
		return startdato;
	}

	public void setStartdato(LocalDate startdato) {
		this.startdato = startdato;
	}

	public int getAntallTimer() {
		return antallTimer;
	}

	public void setAntallTimer(int antallTimer) {
		this.antallTimer = antallTimer;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public void setAnsatt(Ansatt ansatt) {
		this.ansatt = ansatt;
	}

	public Prosjekt getProsjekt() {
		return prosjekt;
	}

	public void setProsjekt(Prosjekt prosjekt) {
		this.prosjekt = prosjekt;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public void skrivUt() {
		System.out.println("Startdato: " + startdato + "\n AntallTimer: " + antallTimer);
		ansatt.skrivUt();
		System.out.println("\n Rolle: " + rolle);
	}

}