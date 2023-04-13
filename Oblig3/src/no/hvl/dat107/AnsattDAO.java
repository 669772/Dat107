package no.hvl.dat107;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class AnsattDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ansattO1");
	private AvdelingDAO avdelingDAO = new AvdelingDAO();
	private ProsjektDAO prosjektDAO = new ProsjektDAO();

	public Ansatt finnAnsattMedID(int id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Ansatt.class, id);
		} finally {
			em.close();
		}
	}

	public boolean erSjef(Ansatt ansatt) {

		int i = 1;
		boolean ferdig = false;
		Avdeling avdeling = avdelingDAO.finnAvdelingMedID(i);

		while (!ferdig && avdelingDAO.finnAvdelingMedID(i + 1) != null) {
			avdeling = avdelingDAO.finnAvdelingMedID(i);
			if (avdeling.getSjefId().getBrukernavn() == ansatt.getBrukernavn()) {
				ferdig = true;
			}
			i++;
		}
		return ferdig;
	}

	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {

		EntityManager em = emf.createEntityManager();
		boolean ikkeFunnet = true;

		try {

			int i = 1;
			while (ikkeFunnet) {

				Ansatt ansatt = finnAnsattMedID(i);

				if (ansatt.getBrukernavn().equals(brukernavn)) {
					return ansatt;
				}

				if (finnAnsattMedID(i + 1) == null) {
					ikkeFunnet = false;
					System.out.println("Fant ikke den ansatte du ser etter");
					return null;
				}

				i++;
			}
			return null;

		} finally {
			em.close();
		}
	}

	private List<Ansatt> hentAlleAnsatte() {

		List<Ansatt> tab = new ArrayList<Ansatt>();

		boolean ikkeFunnet = true;
		int i = 1;
		while (ikkeFunnet) {

			Ansatt ansatt = finnAnsattMedID(i);
			if (ansatt == null && finnAnsattMedID(i + 1) == null) {
				ikkeFunnet = false;
			} else if (ansatt != null) {
				tab.add(ansatt);
			}
			i++;
		}
		return tab;
	}

	public void skrivUtAlle() {
		List<Ansatt> tab = hentAlleAnsatte();

		for (Ansatt a : tab) {
			a.skrivUt();
		}
	}

	public void oppdaterAnsatt(Ansatt ansatt) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.merge(ansatt);

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void lagreNyAnsatt(String brukernavn, String fornavn, String etternavn, int år, Month maane, int dag,
			String stilling, int maanedslon, Avdeling avdeling) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Ansatt ansatt = new Ansatt(brukernavn, fornavn, etternavn, år, maane, dag, stilling, maanedslon, avdeling);

			if (finnAnsattMedBrukernavn(brukernavn) == null && brukernavn != "") {
				em.persist(ansatt);
			}

			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void meny() {
		Scanner scanner = new Scanner(System.in);
		boolean ferdig = false;

		while (!ferdig) {

			System.out.println(
					" 1. Se alle Ansatte \n " + "2. Legg til ny Ansatt \n " + "3. Skriv ut eller endre på Ansatt \n "
							+ "4. Se alle avdelinger \n " + "5. Skriv ny Avdeling \n " + "6. Opprett Nytt Prosjekt \n "
							+ "7. Søk etter Prosjekt \n " + "8. Før ny Deltagelse \n " + "9. Slutt");

			int sv1 = scanner.nextInt();
			scanner.nextLine();

			switch (sv1) {

			case 1:
				skrivUtAlle();
				break;

			case 2:

				System.out.println("Brukernavn: ");
				String brukernavn = scanner.nextLine();

				System.out.println("Fornavn: ");
				String fornavn = scanner.nextLine();

				System.out.println("Etternavn: ");
				String etternavn = scanner.nextLine();

				System.out.println("Ansatt år: ");
				int anAA = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Ansatt Måned: ");
				String anmaa = scanner.nextLine().toUpperCase();
				// scanner.nextLine();

				System.out.println("Ansatt dag: ");
				int anDa = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Stilling: ");
				String still = scanner.nextLine();
				// scanner.nextLine();

				System.out.println("Månedslønn: ");
				int maanedslon = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Avdeling: ");
				int avdI = scanner.nextInt();
				scanner.nextLine();

				Avdeling avd = avdelingDAO.finnAvdelingMedID(avdI);

				lagreNyAnsatt(brukernavn, fornavn, etternavn, anAA, Month.valueOf(anmaa), anDa, still, maanedslon, avd);

				break;

			case 3:

				System.out.println(" Ønsker du å søke med: \n 1. ID \n 2. Brukernavn");
				int sv2 = scanner.nextInt();
				scanner.nextLine();

				Ansatt valgt = null;

				if (sv2 == 1) {

					System.out.println("Hvilke ID?");
					int id = scanner.nextInt();
					scanner.nextLine();

					valgt = finnAnsattMedID(id);

				} else {

					System.out.println("Hvilke Brukernavn?");
					String bruk = scanner.nextLine();

					valgt = finnAnsattMedBrukernavn(bruk);
				}

				valgt.skrivUt();

				System.out.println("Ønsker du å redigere? \n 1. Stilling \n 2. Lønn \n 3. Avdeling \n 4. Ingen av de");
				int sv3 = scanner.nextInt();
				scanner.nextLine();

				if (sv3 == 1) {

					System.out.println("Skriv inn nytt Stilling: ");
					String nyS = scanner.nextLine();

					valgt.setStilling(nyS);

				} else if (sv3 == 2) {

					System.out.println("Skriv inn ny Lønn: ");
					int nyL = scanner.nextInt();
					scanner.nextLine();

					valgt.setMaanedslon(nyL);

				} else if (sv3 == 3) {

					System.out.println("Skriv inn ny avdelings nummer: ");
					int aNum = scanner.nextInt();

					if (!erSjef(valgt)) {
						valgt.setAvdeling(avdelingDAO.finnAvdelingMedID(aNum));
					} else {
						System.out.println("Denne personen er sjef for " + valgt.getAvdeling().getNavn()
								+ " og kan derfor ikke bytte");
					}

				} else {
					break;
				}

				oppdaterAnsatt(valgt);
				valgt.skrivUt();

				break;

			case 4:

				System.out.println("Hva hvil du se?");
				System.out.println(avdelingDAO.listAvAvd());
				int sv4 = scanner.nextInt();
				scanner.nextLine();

				Avdeling avdeling = avdelingDAO.finnAvdelingMedID(sv4);

				if (avdeling == null) {
					break;
				}

				System.out.println("Hvil du se \n 1. Med ansatte \n 2. Uten ansatte");
				int sv5 = scanner.nextInt();
				scanner.nextLine();

				if (sv5 == 1) {
					avdeling.skrivUtMedAnsatte();
				} else {
					avdeling.skrivUt();
				}

				break;

			case 5:

				System.out.println("Navn på avdeling: ");
				String navn = scanner.nextLine();

				System.out.println("Brukernavn på sjef: ");
				Ansatt sjef = finnAnsattMedBrukernavn(scanner.nextLine());

				Avdeling nyAv = avdelingDAO.lagreNyAvdeling(navn, sjef);
				sjef.setAvdeling(nyAv);
				oppdaterAnsatt(sjef);
				nyAv.getAnsatte().add(sjef);

				avdelingDAO.oppdaterAvdeling(nyAv);

				nyAv.skrivUtMedAnsatte();
				break;

			case 6:

				System.out.println("Hva er navnet på Prosjektet?");
				String navnP = scanner.nextLine();

				System.out.println("Beskrivelse: ");
				String beskr = scanner.nextLine();

				prosjektDAO.lagreNyttProsjekt(navnP, beskr);
				break;

			case 7:

				System.out.println("Hvilke Prosjekt nummer?");
				int Pn = scanner.nextInt();

				prosjektDAO.finnProsjektMedID(Pn).skrivUtMedAnsatte();
				break;

			case 8:

				System.out.println("start dag:");
				int dagP = scanner.nextInt();
				scanner.nextLine();

				System.out.println("start måned");
				String maaneP = scanner.nextLine().toUpperCase();

				System.out.println("start år: ");
				int aarP = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Antall timer: ");
				int At = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Rolle: ");
				String roP = scanner.nextLine();

				System.out.println("Ansatt nummer: ");
				int An = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Prosjekt nummer: ");
				int ProN = scanner.nextInt();
				scanner.nextLine();

				prosjektDAO.lagreNytProsjektDeltagelse(dagP, Month.valueOf(maaneP), aarP, At, finnAnsattMedID(An),
						prosjektDAO.finnProsjektMedID(ProN));

				break;

			default:

				System.out.println("Avslutter...");

				ferdig = true;
				scanner.close();
			}
		}
	}
}