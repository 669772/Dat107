package no.hvl.dat107;

import java.time.Month;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ProsjektDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ansattO1");

	public Prosjekt finnProsjektMedID(int id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Prosjekt.class, id);
		} finally {
			em.close();
		}
	}

	public ProsjektDeltagelse finnProsjektDeltagelseMedID(int id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(ProsjektDeltagelse.class, id);
		} finally {
			em.close();
		}
	}

	public String listAvProsjekter() {
		String str = "";

		boolean ikkeFunnet = true;
		int i = 1;
		while (ikkeFunnet) {

			Prosjekt prosjekt = finnProsjektMedID(i);
			if (prosjekt == null && finnProsjektMedID(i + 1) == null) {
				ikkeFunnet = false;
			} else if (prosjekt != null) {
				System.out.println(i + ". " + prosjekt.getNavn());
			}
			i++;
		}

		return str;
	}

	public Prosjekt lagreNyttProsjekt(String navn, String beskrivelse) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Prosjekt prosjekt = null;

		try {
			tx.begin();

			prosjekt = new Prosjekt(navn, beskrivelse);

			em.persist(prosjekt);

			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}

		return prosjekt;
	}

	// For Deltagelse
	public ProsjektDeltagelse lagreNytProsjektDeltagelse(int dag, Month maane, int aar, int antallTimer, Ansatt ansatt,
			Prosjekt prosjekt) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		ProsjektDeltagelse prosjektD = null;

		try {
			tx.begin();

			prosjektD = new ProsjektDeltagelse(dag, maane, aar, antallTimer, ansatt, prosjekt);

			em.persist(prosjektD);

			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}

		return prosjektD;
	}

	public void oppdaterProsjekt(Prosjekt prosjekt) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.merge(prosjekt);

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

	public void oppdaterProsjektDeltagelse(ProsjektDeltagelse prosjektD) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.merge(prosjektD);

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