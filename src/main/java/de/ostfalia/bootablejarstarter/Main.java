package de.ostfalia.bootablejarstarter;

import de.ostfalia.entity.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Country c = em.find(Country.class, 1);
            //em.getTransaction().commit();

            System.out.println(c.getCountry());
        } finally {
            em.close();
        }




    }
}
