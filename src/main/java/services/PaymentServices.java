package services;

import dto.PaymentDTO;
import entities.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import utils.*;

import java.math.BigDecimal;

public class PaymentServices {


    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private CustomerServices customerServices;

    @Inject
    private StoreServiceClient storeServiceClient;
    @Inject
    private RentalServiceClient rentalServiceClient;




    public PaymentDTO getPaymentById(int id) {
        Payment payment = entityManager.find(Payment.class, id);
        if (payment == null) {
            return null;
        }
        return convertToDTO(payment);
    }

    /*
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

     */

    // Hier muss noch 400 Only allowed: amount (decimal), customer (int), rental (int), staff (int), date (yyyy-MM-dd HH:mm) implementieren
    @Transactional
    public Response createPayment(PaymentValue paymentValue) {
        // Validierung der Eingabedaten
        if (paymentValue == null || !isValidPaymentValue(paymentValue)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Some involved entity does not exist. See message body.").build();
        }
        // Überprüfen der Existenz von Customer, Staff und Rental
        if (!doesCustomerExist(paymentValue.getCustomer()) ||
                !storeServiceClient.checkStoreExists(paymentValue.getStaff()) ||
                !rentalServiceClient.checkRentalExists(paymentValue.getRental())) {
            return Response.status(Response.Status.NOT_FOUND).entity("Some involved entity does not exist. See message body.").build();
        }

        Payment payment = new Payment();
        payment.setAmount(paymentValue.getAmount());
        payment.setRentalId(paymentValue.getRental());
        Customer customer = customerServices.getEntityManager().find(Customer.class, paymentValue.getCustomer());
        payment.setCustomer(customer);
        payment.setStaffId(paymentValue.getStaff());
        payment.setPaymentDate(paymentValue.getDate());

        entityManager.persist(payment);

        PaymentDTO createdPayment = convertToDTO(payment);
        return Response.status(Response.Status.CREATED)
                .header("Location", "http://localhost:8083/payments/" + payment.getPaymentId())
                .entity(createdPayment)
                .build();
    }

    private boolean isValidPaymentValue(PaymentValue paymentValue) {
        if (paymentValue == null) {
            return false;
        }

        // Überprüfen des Betrags: sollte nicht negativ sein
        if (paymentValue.getAmount() == null || paymentValue.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // Überprüfen der ID-Werte: sollten positive Zahlen sein
        if (paymentValue.getCustomer() == null || paymentValue.getCustomer() <= 0 ||
                paymentValue.getRental() == null || paymentValue.getRental() <= 0 ||
                paymentValue.getStaff() == null || paymentValue.getStaff() <= 0) {
            return false;
        }

        // Datum: Die @JsonFormat-Annotation kümmert sich um das Format, also nur auf null prüfen
        if (paymentValue.getDate() == null) {
            return false;
        }

        return true;
    }




    private boolean doesCustomerExist(int customerId) {
        return customerServices.doesCustomerExist(customerId);
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