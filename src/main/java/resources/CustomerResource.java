package resources;

import entities.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
public class CustomerResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers(@QueryParam("page") int page) {
        // Implementierung

        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer, @QueryParam("address") int addressId, @QueryParam("store") int storeId) {
        // Implementierung
        return null;
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerCount() {
        // Implementierung
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int id) {
        // Implementierung
        return null;
    }

    @GET
    @Path("/{id}/payments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentsByCustomerId(@PathParam("id") int id) {
        // Implementierung
        return null;
    }
}