package services;

import entities.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AddressServices {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Address> getFirst10Addresses() {
        TypedQuery<Address> query = entityManager.createQuery("SELECT a FROM Address a", Address.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
