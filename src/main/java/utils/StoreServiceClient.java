package utils;

import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
public class StoreServiceClient {
    private static final String STORE_SERVICE_URL = "http://localhost:8082/stores/";

    private final Client client;

    public StoreServiceClient() {
        this.client = ClientBuilder.newClient();
    }

    public boolean checkStoreExists(int storeId) {
        WebTarget target = client.target(STORE_SERVICE_URL + storeId);

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
