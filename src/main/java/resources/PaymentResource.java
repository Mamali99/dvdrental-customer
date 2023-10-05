package resources;

import entities.Payment;
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
    public Response createPayment(Payment payment) {
       return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentById(@PathParam("id") int id) {
        List<Payment> payments = paymentServices.getFirst10Payments();
        return Response.ok(payments).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") int id) {
        // Implementierung
        return null;
    }
}