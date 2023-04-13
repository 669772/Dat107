package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "ansattO1")
public class Prosjekt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ProsjektId;

	private String navn;
	private String beskrivelse;
	
	@OneToMany(mappedBy = "Prosjekt")
	private List<ProsjektDeltagelse> deltagere = new ArrayList<ProsjektDeltagelse>();
	
	public Prosjekt() {}
	
	public Prosjekt(String navn, String beskrivelse) {
		this.navn = navn;
		this.beskrivelse = beskrivelse;
	}
	
	public int getProsjektId() {
		return ProsjektId;
	}

	public void setProsjektId(int prosjektId) {
		ProsjektId = prosjektId;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public List<ProsjektDeltagelse> getDeltagere() {
		return deltagere;
	}

	public void setDeltagere(List<ProsjektDeltagelse> deltagere) {
		this.deltagere = deltagere;
	}

	public void skrivUt() {
		String str =" Navn: " + navn + "\n beskrivelse: " + beskrivelse + "\n Antall deltagere: " + deltagere.size();
		
		int totalT = 0;
		
		for (ProsjektDeltagelse a : deltagere) {
			totalT += a.getAntallTimer();
		}
		
		str += "\n AntallTimmer: " + totalT;
		System.out.println(str);
	}
	
	public void skrivUtMedAnsatte() {
		skrivUt();
		
		for (ProsjektDeltagelse a : deltagere) {
			a.skrivUt();
		}
	}
	
}