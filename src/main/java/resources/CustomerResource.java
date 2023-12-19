package resources;

import dto.CustomerDTO;
import dto.PaymentDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import services.CustomerServices;

import java.net.URI;
import java.util.List;

@Path("/customers")
public class CustomerResource {
    @Inject
    private CustomerServices customerServices;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers(@QueryParam("page") @DefaultValue("1") int page) {
        List<CustomerDTO> customers = customerServices.getCustomersByPage(page);
        if(customers == null || customers.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(customers).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CustomerDTO customerDTO) {
        try {
            CustomerDTO customer = customerServices.createCustomer(customerDTO);
            URI addressUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(customer.getId())).build();
            return Response.created(addressUri).build();
        } catch (WebApplicationException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("store and/or address do not exist.").build();
        }
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
        if(customerDTO==null){
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        return Response.ok(customerDTO).build();
    }

    @GET
    @Path("/{id}/payments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentsByCustomerId(@PathParam("id") int id) {
        if (!customerServices.doesCustomerExist(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }

        List<PaymentDTO> paymentDTOS = customerServices.getPaymentsByCustomerId(id);
        return Response.ok(paymentDTOS).build();
    }

}