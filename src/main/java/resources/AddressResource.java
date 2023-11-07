package resources;

import dto.AddressDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import services.AddressServices;

import java.net.URI;
import java.util.List;

@Path("/addresses")
public class AddressResource {

    @Inject
    private AddressServices addressServices;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses(@QueryParam("page") @DefaultValue("1") int page) {
        List<AddressDTO> addresses = addressServices.getAddressesByPage(page);
        if(addresses == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(addresses).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(AddressDTO addressDTO) {
        try {
            AddressDTO newAddress = addressServices.createAddress(addressDTO);
            URI addressUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newAddress.getId())).build();
            return Response.created(addressUri).entity(newAddress).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

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
        if(addressDTO == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Address not found for address-id: "+id).build();
        }
        return Response.ok(addressDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) {

        return addressServices.deleteAddress(id);
    }
}

