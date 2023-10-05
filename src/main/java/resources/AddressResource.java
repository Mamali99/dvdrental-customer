package resources;

import entities.Address;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/addresses")
public class AddressResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses(@QueryParam("page") int page) {
        // Implementierung
        return null;
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

