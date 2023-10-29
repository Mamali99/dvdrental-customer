package entities;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "id", "address", "address2", "district", "phone", "postalCode", "city", "country" })
@Getter
@Setter
public class AddressDTO {

    private int id;
    private String address;
    private String address2;
    private String district;
    private String phone;
    private String postalCode;
    private String city;
    private String country;

}
