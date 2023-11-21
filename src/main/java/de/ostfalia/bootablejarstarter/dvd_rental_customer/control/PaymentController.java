package de.ostfalia.bootablejarstarter.dvd_rental_customer.control;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Customer;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Payment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Controller-Klasse für payment
 */
@Stateless
public class PaymentController {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persitiert ein Payment
     * @param p zu persistierendes Payment
     */
    public void create(Payment p) {
        em.persist(p);
    }

    /**
     * Aktualisiert oder persistiert ein Payment und liefert das aktualisierte Payment zurück
     * @param p zu persistierendes/aktualisierendes Payment
     * @return aktualisiertes Payment
     */
    public Payment merge(Payment p) {
        return em.merge(p);
    }

    /**
     * Liefert das Payment mit der ID <code>id</code> oder <code>null</code> zurück
     * @param id
     * @return Payment mit ID <code>id</code> oder <code>null</code>
     */
    public Payment get(int id) {
        return em.find(Payment.class, id);
    }

    /**
     * Liefert alle Payments des Customers <code>c</code> zurück
     * @param c Customer, dessen Payments geliefert werden sollen
     * @return Liste mit allen Payments
     */
    public List<Payment> getByCustomer(Customer c) {

        return em.createNamedQuery("getByCustomer", Payment.class).setParameter("c", c).getResultList();
    }

    /**
     * Löscht das Payment <code>p</code>
     * @param p
     */
    public void delete(Payment p) {
        em.remove(em.merge(p));
    }
}

