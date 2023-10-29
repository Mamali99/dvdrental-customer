package resources;

import entities.Customer;
import entities.CustomerDTO;
import entities.PaymentDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.CustomerServices;

import java.util.List;

@Path("/customers")
public class CustomerResource {
    @Inject
    CustomerServices customerServices;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers(@QueryParam("page") int page) {
        List<CustomerDTO> customers = customerServices.getFirst20Customers();
        return Response.ok(customers).build();
    }

    //Hier muss noch von Store-Microservice kontrollieren, ob Store-id gibt
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CustomerDTO customerDTO) {
        CustomerDTO c = customerServices.createCustomer(customerDTO);
        return Response.ok(c).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerCount() {
        Integer count = customerServices.getCount();
        return Response.ok(count).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int id) {
        CustomerDTO customerDTO = customerServices.getCustomerById(id);
        return Response.ok(customerDTO).build();
    }

    @GET
    @Path("/{id}/payments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentsByCustomerId(@PathParam("id") int id) {
        List<PaymentDTO> paymentDTOS = customerServices.getPaymentsByCustomerId(id);
        return Response.ok(paymentDTOS).build();
    }
}