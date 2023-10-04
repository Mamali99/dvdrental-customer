package de.ostfalia.bootablejarstarter;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/")
public class Test {
    @Inject
    service s;
    @GET
    @Path("/test")
    @Produces("text/plain")
    public String t(){

        return s.suche();
    }
}
