package dto;

import utils.CustomerHref;
import utils.RentalHref;
import utils.StaffHref;

public class PaymentDTO {

    private Integer id;
    private Double amount;
    private StaffHref staff;
    private RentalHref rental;
    private CustomerHref customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public StaffHref getStaff() {
        return staff;
    }

    public void setStaff(StaffHref staff) {
        this.staff = staff;
    }

    public RentalHref getRental() {
        return rental;
    }

    public void setRental(RentalHref rental) {
        this.rental = rental;
    }

    public CustomerHref getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerHref customer) {
        this.customer = customer;
    }
}
