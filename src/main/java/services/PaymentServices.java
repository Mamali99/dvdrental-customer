package services;

import dto.PaymentDTO;
import entities.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import utils.CustomerHref;
import utils.PaymentValue;
import utils.RentalHref;
import utils.StaffHref;

public class PaymentServices {


    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private CustomerServices customerServices;


    public PaymentDTO getPaymentById(int id) {
        Payment payment = entityManager.find(Payment.class, id);

        if (payment == null) {
            return null;
        }
        /*

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getPaymentId());
        paymentDTO.setAmount(payment.getAmount().doubleValue());

        // Setzen Sie die Hrefs basierend auf Ihren Endpunkt-URLs
        paymentDTO.setCustomer(new CustomerHref("http://localhost:8083/customers/" + payment.getCustomer().getCustomer_id()));
        paymentDTO.setStaff(new StaffHref("http://localhost:8082/staff/" + payment.getStaffId()));
        paymentDTO.setRental(new RentalHref("http://localhost:8082/rentals/" + payment.getRentalId()));


         */
        return convertToDTO(payment);
    }

    @Transactional
    public PaymentDTO createPayment(PaymentValue paymentValue) {

        // Erstellen Sie ein neues Payment Objekt und setzen Sie die Werte von PaymentValue
        Payment payment = new Payment();
        payment.setAmount(paymentValue.getAmount());
        payment.setRentalId(paymentValue.getRental());  // Annahme, dass die Methode setRentalId() in der Payment Klasse existiert
        Customer customer = customerServices.getEntityManager().find(Customer.class, paymentValue.getCustomer());
        payment.setCustomer(customer);
        payment.setStaffId(paymentValue.getStaff());  // Annahme, dass die Methode setStaffId() in der Payment Klasse existiert
        payment.setPaymentDate(paymentValue.getDate());  // Annahme, dass die Methode setPaymentDate() in der Payment Klasse existiert
        entityManager.merge(payment);
        return convertToDTO(payment);
    }

    public PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getPaymentId());
        paymentDTO.setAmount(payment.getAmount().doubleValue());
        paymentDTO.setStaff(new StaffHref("http://localhost:8082/staff/" + payment.getStaffId()));
        paymentDTO.setRental(new RentalHref("http://localhost:8082/rentals/" + payment.getRentalId()));
        paymentDTO.setCustomer(new CustomerHref("http://localhost:8083/customers/" + payment.getCustomer().getCustomer_id()));
        return paymentDTO;
    }

    public Payment convertFromDTO(PaymentDTO dto){
        return null;
    }


    @Transactional
    public Response deletePayment(int id) {
        Payment payment = entityManager.find(Payment.class, id);
        if (payment == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Payment not found.").build();
        }

        // Aktualisieren der Beziehung auf der Customer-Seite
        Customer customer = payment.getCustomer();
        customer.getPayments().remove(payment);
        entityManager.merge(customer);

        // Löschen der Payment-Entität
        entityManager.remove(payment);
        entityManager.flush();

        return Response.noContent().build();
    }

}