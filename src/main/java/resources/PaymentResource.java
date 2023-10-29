package resources;

import entities.Payment;
import entities.PaymentDTO;
import entities.PaymentValue;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.PaymentServices;

import java.util.List;

@Path("/payments")
public class PaymentResource {

    @Inject
    PaymentServices paymentServices;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(PaymentValue payment) {
       Payment p = paymentServices.createPayment(payment);
        return Response.ok(p).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentById(@PathParam("id") int id) {
        PaymentDTO payment = paymentServices.getPaymentById(id);
        return Response.ok(payment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") int id) {
        // Implementierung
        return null;
    }
}