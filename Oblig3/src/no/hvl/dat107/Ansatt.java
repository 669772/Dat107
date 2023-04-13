package no.hvl.dat107;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.*;

@Entity
@Table(schema = "ansattO1")
public class Ansatt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansattId;

	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansattDato;
	private String stilling;
	private int maanedslon;
	
	@ManyToOne
    @JoinColumn(name = "Avdeling")
	private Avdeling avdeling;

	public Ansatt() {
	}

	public Ansatt(String brukernavn, String fornavn, String etternavn, int år, Month maane , int dag, String stilling,
			int maanedslon, Avdeling avdeling) {
		
		LocalDate ansattDato = LocalDate.of(år, maane, dag);
		
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansattDato = ansattDato;
		this.stilling = stilling;
		this.maanedslon = maanedslon;
		this.avdeling = avdeling;
	}

	public Avdeling getAvdeling() {
		return avdeling;
	}

	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}

	public int getAnsattId() {
		return ansattId;
	}

	public void setAnsattId(int ansattId) {
		this.ansattId = ansattId;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	public String getFornavn() {
		return fornavn;
	}

	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}

	public LocalDate getAnsattDato() {
		return ansattDato;
	}

	public void setAnsattDato(LocalDate ansattDato) {
		this.ansattDato = ansattDato;
	}

	public String getStilling() {
		return stilling;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public int getMaanedslon() {
		return maanedslon;
	}

	public void setMaanedslon(int maanedslon) {
		this.maanedslon = maanedslon;
	}

	public void skrivUt() {
		String str = "\n AnsattId: " + ansattId + "\n Brukernavn: " + brukernavn + "\n Fornavn: " + fornavn
				+ "\n Etternavn: " + etternavn + "\n AnsattDato: " + ansattDato + "\n Stilling: " + stilling
				+ "\n Månedslønn: " + maanedslon + "\n Avdeling: " + avdeling.getNavn() + "\n";
		System.out.println(str);
	}

}