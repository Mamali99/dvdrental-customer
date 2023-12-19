package dto;

import utils.AddressHref;
import utils.StoreHref;

import java.sql.Date;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Boolean getActivebool() {
        return activebool;
    }

    public void setActivebool(Boolean activebool) {
        this.activebool = activebool;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StoreHref getStore() {
        return store;
    }

    public void setStore(StoreHref store) {
        this.store = store;
    }

    public AddressHref getAddress() {
        return address;
    }

    public void setAddress(AddressHref address) {
        this.address = address;
    }
}
