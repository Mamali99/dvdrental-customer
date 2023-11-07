package dto;

import utils.CustomerHref;
import utils.RentalHref;
import utils.StaffHref;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    private Integer id;
    private Double amount;
    private StaffHref staff;
    private RentalHref rental;
    private CustomerHref customer;

}
