package de.ostfalia.bootablejarstarter;

import de.ostfalia.entity.Country;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Named
public class service {
    @PersistenceContext
    EntityManager em;
    public String suche() {
        try {
            Country c = em.find(Country.class, 2);
            if (c != null) {
                return c.getCountry();
            } else {
                return "Land nicht gefunden";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Dies gibt den Stacktrace aus, sodass Sie sehen können, was schief gelaufen ist.
            return "Fehler bei der Datenbankabfrage: " + e.getMessage();
        }
    }



}
