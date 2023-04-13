package no.hvl.dat107;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(schema = "ansattO1")
public class Avdeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int avdelingId;

    private String navn;

    @OneToMany(mappedBy = "Avdeling")
    private List<Ansatt> ansatte = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "sjef_id")
    private Ansatt sjefId;

    public Avdeling() {
    }

    public Avdeling(String navn, Ansatt sjefId) {
        this.navn = navn;
        this.sjefId = sjefId;
    }

    public int getAvdelingId() {
        return avdelingId;
    }

    public void setAvdelingId(int avdelingId) {
        this.avdelingId = avdelingId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public List<Ansatt> getAnsatte() {
        return ansatte;
    }

    public void setAnsatte(List<Ansatt> ansatte) {
        this.ansatte = ansatte;
    }

    public Ansatt getSjefId() {
        return sjefId;
    }

    public void setSjefId(Ansatt sjefId) {
        this.sjefId = sjefId;
    }

    public void skrivUt() {

        String str = " navn: " + navn + "\n ansatte: " + ansatte.size() + "\n sjefId: " + sjefId.getFornavn() + " "
                + sjefId.getEtternavn() + "\n\n";
        System.out.println(str);
    }

    public void skrivUtMedAnsatte() {
        skrivUt();

        ansatte.forEach(Ansatt::skrivUt);
    }

}