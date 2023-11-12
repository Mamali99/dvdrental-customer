package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import utils.AddressHref;
import utils.StoreHref;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class CustomerDTO {

    private Integer id;
    private Integer active;
    private Boolean activebool;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String email;
    private String firstName;
    private String lastName;
    private StoreHref store;
    private AddressHref address;

}
