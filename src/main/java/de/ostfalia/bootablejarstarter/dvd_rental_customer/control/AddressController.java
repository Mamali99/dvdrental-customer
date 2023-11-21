package de.ostfalia.bootablejarstarter.dvd_rental_customer.control;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Address;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Controller-Klasse für die addresses
 */
@Stateless
public class AddressController {

    @PersistenceContext
    private EntityManager em;

    /**
     * Aktualisiert oder persistiert eine Address und liefert die aktualisierte Address zurück
     * @param a zu persistierende/aktualisierende Address
     * @return aktualisierte Address
     */
    public Address merge(Address a) {
        return em.merge(a);
    }

    /**
     * Liefert <code>limit</code> Addresses mit Offset <code>offset</code> zurück
     * @param limit
     * @param offset
     * @return Adressliste
     */
    public List<Address> get(int limit, int offset) {
        return em.createNamedQuery("getAll", Address.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    /**
     * Liefert die Address mit der ID <code>id</code> oder <code>null</code>
     * @param id
     * @return Address mit ID <code>id</code> oder <code>null</code>
     */
    public Address get(int id) {

        return em.find(Address.class, id);
    }
}