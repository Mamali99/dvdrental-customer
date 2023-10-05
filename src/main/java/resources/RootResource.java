package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class RootResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoot() {
        // Implementierung
        return null;
    }
}