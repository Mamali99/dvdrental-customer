package entities;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class CustomerDTO {

    private Integer id;
    private Integer active;
    private Boolean activebool;
    private Date createDate;
    private String email;
    private String firstName;
    private String lastName;
    private StoreHref store;
    private AddressHref address;

}
