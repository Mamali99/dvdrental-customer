package resources;

import entities.Address;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.AddressServices;

import java.util.List;

@Path("/addresses")
public class AddressResource {

    @Inject
    AddressServices addressServices;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses(@QueryParam("page") int page) {
        List<Address> addresses = addressServices.getFirst10Addresses();

        return Response.ok(addresses).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(Address address) {
        // Implementierung
        return null;
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressCount() {
        // Implementierung
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressById(@PathParam("id") int id) {
        // Implementierung
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) {
        // Implementierung
        return null;
    }
}

