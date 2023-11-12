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

import java.math.BigDecimal;

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
        return convertToDTO(payment);
    }

    @Transactional
    public PaymentDTO createPayment(PaymentValue paymentValue) {

        Payment payment = new Payment();
        payment.setAmount(paymentValue.getAmount());
        payment.setRentalId(paymentValue.getRental());
        Customer customer = customerServices.getEntityManager().find(Customer.class, paymentValue.getCustomer());
        payment.setCustomer(customer);
        payment.setStaffId(paymentValue.getStaff());
        payment.setPaymentDate(paymentValue.getDate());
        entityManager.merge(payment);
        return convertToDTO(payment);
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

    public PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getPaymentId());
        paymentDTO.setAmount(payment.getAmount().doubleValue());
        paymentDTO.setStaff(new StaffHref("http://localhost:8082/staff/" + payment.getStaffId()));
        paymentDTO.setRental(new RentalHref("http://localhost:8082/rentals/" + payment.getRentalId()));
        paymentDTO.setCustomer(new CustomerHref("http://localhost:8083/customers/" + payment.getCustomer().getCustomer_id()));
        return paymentDTO;
    }


}