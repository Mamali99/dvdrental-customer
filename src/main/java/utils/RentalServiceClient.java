package utils;

import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
public class RentalServiceClient {
    private static final String RENTAL_SERVICE_URL = "http://localhost:8082/rentals/";

    private final Client client;

    public RentalServiceClient() {
        this.client = ClientBuilder.newClient();
    }

    public boolean checkRentalExists(int rentalId) {
        WebTarget target = client.target(RENTAL_SERVICE_URL + rentalId);

        // Ausgabe der angeforderten URL
        System.out.println("Angeforderte URL: " + target.getUri().toString());

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
