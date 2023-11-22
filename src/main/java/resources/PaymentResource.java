package resources;

import dto.PaymentDTO;
import utils.PaymentValue;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.PaymentServices;

@Path("/payments")
public class PaymentResource {

    @Inject
    private PaymentServices paymentServices;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(PaymentValue payment) {
        return paymentServices.createPayment(payment);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentById(@PathParam("id") int id) {
        PaymentDTO payment = paymentServices.getPaymentById(id);
        if(payment == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Payment not found").build();
        }
        return Response.ok(payment).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") int id) {
        return paymentServices.deletePayment(id);
    }
}