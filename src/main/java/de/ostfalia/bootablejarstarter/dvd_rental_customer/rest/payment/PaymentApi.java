package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.payment;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.control.CustomerController;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.control.PaymentController;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Customer;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Payment;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.Href;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.UrlProperties;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST-API für die Payments
 */
@Path("/payments")
public class PaymentApi {

    @Inject
    private PaymentController paymentController;

    @Inject
    private CustomerController customerController;

    @Inject
    private UrlProperties urlProperties;

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Erstellt ein Payment und persistiert dieses, sofern der Customer existiert und alle Werte des Payments validiert wurden.<p>
     * Wird Country/City nicht gefunden: HTTP Code 404<p>
     * Validierung schlägt fehl: HTTP Code 405<p>
     * Persistierung erfolgreich: HTTP Code 201
     * @param paymentReqBody von der Anfrage gelieferter JSON
     * @return HTTP-Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayment(PaymentReqBody paymentReqBody) {
        Customer c = customerController.get(paymentReqBody.customer);
        if(c != null) {
            Payment payment = new Payment(
                    0,
                    c,
                    paymentReqBody.staff,
                    paymentReqBody.rental,
                    paymentReqBody.amount,
                    paymentReqBody.date
            );
            if(VALIDATOR.validate(payment).isEmpty()) {
                payment = paymentController.merge(payment);
                return Response.status(Response.Status.CREATED).header("Location", urlProperties.getCustomerBase() + "resources/payments/" + payment.getPaymentId()).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Liefert das Payment mit der ID <code>id</code> zurück.<p>
     * Customer nicht gefunden: HTTP Code 404<p>
     * Bei Erfolg: HTTP Code 200
     * @param id
     * @return HTTP-Response mit Payment
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment(@PathParam("id") int id) {
        Payment p = paymentController.get(id);
        if(p != null) {
            PaymentJson res = new PaymentJson(
                    p.getAmount(),
                    new Href(urlProperties.getCustomerBase() + "resources/customers/" + p.getCustomer().getCustomerId()),
                    p.getPaymentId(),
                    new Href(urlProperties.getStoreBase() + "resources/rentals/" + p.getRentalId()),
                    new Href(urlProperties.getStoreBase() + "resources/staff/" + p.getStaffId())
            );
            return Response.ok(res).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Löscht das Payment mit der ID <code>id</code>. Um Lösch-Funktion zu aktivierten, den Kommentar am Anfang von Zeile 101 löschen.<p>
     * Payment nicht gefunden: HTTP Code 404<p>
     * Bei Erfolg: HTTP Code 204
     * @param id
     * @return HTTP-Response
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        Payment p = paymentController.get(id);
        if(p != null) {
            //paymentController.delete(p);  //Kommentar am Anfang entfernen, um richtig zu Loeschen
            System.out.println("Deleted Payment");
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
