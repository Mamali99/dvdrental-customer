package entities;

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
