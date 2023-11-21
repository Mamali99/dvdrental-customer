package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

    /**
     * REST-API für die Resources
     */
    @Path("")
    public class Resources {

        @Inject
        private UrlProperties urlProperties;

        /**
         * Liefert eine Liste der Hauptresourcen zurück
         * @return Liste der Resourcen
         */
        @GET
        @Path("")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getResources() {
            List<String> resources = new ArrayList<>();
            resources.add(urlProperties.getCustomerBase() + "api/addresses");
            resources.add(urlProperties.getCustomerBase() + "api/customers");
            resources.add(urlProperties.getCustomerBase() + "api/payments");
            return Response.ok(resources.toArray()).build();
        }
    }

