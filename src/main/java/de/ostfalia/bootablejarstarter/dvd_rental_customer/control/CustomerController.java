package de.ostfalia.bootablejarstarter.dvd_rental_customer.control;


import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Controller-Klasse für customer
 * Sie stellt Methoden zum Erstellen, Zählen und Abrufen von Kundendaten über den EntityManager bereit.
 */
@Stateless
public class CustomerController {

    /**
     *
     *  @PersistenceContext: Annotation to inject an EntityManager instance
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Persitiert einen Customer
     * @param c zu persistierender Customer
     */
    public void create(Customer c) {

        em.persist(c);
    }

    /**
     * Zählt alle Customer
     * This method uses a named query ("count") to count the number of customers.
     * It returns the count as a long.
     * @return Anzahl der Customer
     */
    public long count() {

        return em.createNamedQuery("count", Long.class).getSingleResult();
    }

    /**
     * Ruft eine Customer-Entität anhand ihrer ID ab.
     * @param id
     * @return Die Customer-Entität mit der angegebenen ID oder null, falls nicht gefunden
     */
    public Customer get(int id) {

        return em.find(Customer.class, id);
    }
}