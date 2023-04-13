package no.hvl.dat107;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class AvdelingDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ansattO1");

	public Avdeling finnAvdelingMedID(int id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Avdeling.class, id);
		} finally {
			em.close();
		}
	}

	public String listAvAvd() {
		String str = "";

		boolean ikkeFunnet = true;
		int i = 1;
		while (ikkeFunnet) {

			Avdeling avdeling = finnAvdelingMedID(i);
			if (avdeling == null && finnAvdelingMedID(i + 1) == null) {
				ikkeFunnet = false;
			} else if (avdeling != null) {
				System.out.println(i + ". " + avdeling.getNavn());
			}
			i++;
		}

		return str;
	}

	public Avdeling lagreNyAvdeling(String navn, Ansatt sjef) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Avdeling avdeling = null;

		try {
			tx.begin();

			avdeling = new Avdeling(navn, sjef);

			em.persist(avdeling);

			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
		
		return avdeling;
	}
	
	public void oppdaterAvdeling(Avdeling avdeling) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.merge(avdeling);

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

}