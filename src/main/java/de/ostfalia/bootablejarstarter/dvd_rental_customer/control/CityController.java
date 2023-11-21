package de.ostfalia.bootablejarstarter.dvd_rental_customer.control;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.City;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Controller-Klasse für city
 */
@Stateless
public class CityController {

    @PersistenceContext
    private EntityManager em;

    /**
     * Liefert die City mit dem Namen <code>city</code> oder <code>null</code> zurück
     * @param city Name der City
     * @return City mit Name <code>city</code> oder <code>null</code>
     */
    public City getByCity(String city) {
        try {
            return em.createNamedQuery("getByCity", City.class).setParameter("city", city).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}