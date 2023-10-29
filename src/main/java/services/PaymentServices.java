package services;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PaymentServices {

    @PersistenceContext
    private EntityManager entityManager;


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
}
