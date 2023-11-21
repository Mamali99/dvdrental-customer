package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.address;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.control.AddressController;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.control.CityController;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.control.CountryController;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Address;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.City;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.entity.Country;
import de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.UrlProperties;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST-API für die Addresses
 */
@Path("/addresses")
public class AddressApi {

    @Inject
    private AddressController addressController;

    @Inject
    private CityController cityController;

    @Inject
    private CountryController countryController;

    @Inject
    private UrlProperties urlProperties;

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Liefert <code>limit</code> Addresses mit Offset <code>offset</code> oder <code>null</code> zurück, falls keine Addresses gefunden wurden.<p>
     * Liefert HTTP-Code 200 bei Erfolg
     * @param limit Default-Value: 20; max: 100
     * @param offset Default-Value: 0
     * @return Liste der entsprechenden Addresses
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdresses(@DefaultValue("20") @QueryParam("limit") int limit, @DefaultValue("0") @QueryParam("offset") int offset) {
        if(limit > 100) limit = 100;
        List<Address> addressList = addressController.get(limit, offset);
        if(addressList != null && !addressList.isEmpty()) {
            return Response.ok(buildResponseList(addressList)).build();
        }
        return null;
    }

    /**
     * Erstellt eine Address und persistiert diese, sofern die City und das Country existiert und alle Werte der Address validiert wurden.<p>
     * Wird Country/City nicht gefunden: HTTP Code 404<p>
     * Validierung schlägt fehl: HTTP Code 405<p>
     * Persistierung erfolgreich: HTTP Code 201
     * @param addressJson von der Anfrage gelieferter JSON
     * @return HTTP-Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAddress(AddressJson addressJson) {
        Country country = countryController.getByCountry(addressJson.country);
        City city = cityController.getByCity(addressJson.city);
        if(country != null && city != null) {
            Address address = new Address(
                    addressJson.id,
                    addressJson.address,
                    addressJson.address2,
                    addressJson.district,
                    city,
                    addressJson.postalCode,
                    addressJson.phone,
                    LocalDateTime.now()
            );
            if(VALIDATOR.validate(address).isEmpty()) {
                address = addressController.merge(address);
                return Response
                        .status(Response.Status.CREATED)
                        .header("Location", urlProperties.getCustomerBase() + "resources/addresses/" + address.getAddressId())
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Liefert die Address mit der ID <code>id</code> zurück.<p>
     * Address nicht gefunden: HTTP Code 404<p>
     * Bei Erfolg: HTTP Code 200
     * @param id
     * @return HTTP-Response mit Address
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddress(@PathParam("id") int id) {
        Address a = addressController.get(id);
        if(a != null) {
            AddressJson res = new AddressJson(
                    a.getAddress(),
                    a.getAddress2(),
                    a.getCity().getCity(),
                    a.getCity().getCountry().getCountry(),
                    a.getDistrict(),
                    a.getAddressId(),
                    a.getPhone(),
                    a.getPostalCode()
            );
            return Response.ok(res).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Wandelt eine Liste von Addresses in eine Liste von AddressJsons um.
     * @param addressList Address-Liste
     * @return AddressJson-Liste
     */
    private List<AddressJson> buildResponseList(List<Address> addressList) {
        return addressList.stream().map(a -> new AddressJson(
                a.getAddress(),
                a.getAddress2(),
                a.getCity().getCity(),
                a.getCity().getCountry().getCountry(),
                a.getDistrict(),
                a.getAddressId(),
                a.getPhone(),
                a.getPostalCode()
        )).toList();
    }
}