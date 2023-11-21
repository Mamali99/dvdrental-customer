package de.ostfalia.bootablejarstarter.dvd_rental_customer.control;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Country;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Controller-Klasse für country
 */
@Stateless
public class CountryController {

    @PersistenceContext
    private EntityManager em;

    /**
     * Liefert das Country mit dem Namen <code>country</code> oder <code>null</code> zurück
     * @param country Name des Country
     * @return Country mit Name <code>country</code> oder <code>null</code>
     */
    public Country getByCountry(String country) {
        try {
            return em.createNamedQuery("getByCountry", Country.class).setParameter("country", country).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}

