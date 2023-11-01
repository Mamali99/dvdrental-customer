package resources;

import entities.Address;
import entities.AddressDTO;
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
        List<AddressDTO> addresses = addressServices.getFirst100Addresses();
        return Response.ok(addresses).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(AddressDTO address) {

        AddressDTO addressDTO = addressServices.createAddress(address);
        return Response.ok(addressDTO).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressCount() {
        Integer count = addressServices.getCount();
        return Response.ok(count).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressById(@PathParam("id") int id) {
        AddressDTO addressDTO = addressServices.getAddressById(id);
        return Response.ok(addressDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) {

        return addressServices.deleteAddress(id);
    }
}

