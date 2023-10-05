package services;

import entities.Customer;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
@Stateless
@Named
public class CustomerServices {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> getFirst10Customers() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
