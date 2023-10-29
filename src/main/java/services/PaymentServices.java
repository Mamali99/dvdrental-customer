package services;

import entities.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

public class PaymentServices {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private CustomerServices customerServices;


    public PaymentDTO getPaymentById(int id) {
        Payment payment = entityManager.find(Payment.class, id);

        if(payment == null) {
            return null; // oder werfen Sie eine Ausnahme, wenn keine Zahlung mit der gegebenen ID gefunden wird.
        }

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getPaymentId());
        paymentDTO.setAmount(payment.getAmount().doubleValue());

        // Setzen Sie die Hrefs basierend auf Ihren Endpunkt-URLs
        paymentDTO.setCustomer(new CustomerHref("path_to_customer/" + payment.getCustomer().getCustomer_id()));
        paymentDTO.setStaff(new StaffHref("path_to_staff/" + payment.getStaffId()));
        paymentDTO.setRental(new RentalHref("path_to_rental/" + payment.getRentalId()));

        return paymentDTO;
    }

    @Transactional
    public Payment createPayment(PaymentValue paymentValue) {

        // Erstellen Sie ein neues Payment Objekt und setzen Sie die Werte von PaymentValue
        Payment payment = new Payment();
        payment.setAmount(paymentValue.getAmount());
        payment.setRentalId(paymentValue.getRental());  // Annahme, dass die Methode setRentalId() in der Payment Klasse existiert
        Customer customer = customerServices.getEntityManager().find(Customer.class, paymentValue.getCustomer());
        payment.setCustomer(customer);
        payment.setStaffId(paymentValue.getStaff());  // Annahme, dass die Methode setStaffId() in der Payment Klasse existiert
        payment.setPaymentDate(paymentValue.getDate());  // Annahme, dass die Methode setPaymentDate() in der Payment Klasse existiert

        // Persistieren Sie das Payment Objekt in der Datenbank
        entityManager.persist(payment);
        return payment;
    }
}
