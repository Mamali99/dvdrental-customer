package services;

import entities.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PaymentServices {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Payment> getFirst10Payments() {
        TypedQuery<Payment> query = entityManager.createQuery("SELECT p FROM Payment p", Payment.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

}
