package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.customer;

import de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.Href;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * POJO für Request- und Response-JSON
 */
@AllArgsConstructor
@NoArgsConstructor
public class CustomerJson {
    public int active;
    public boolean activebool;
    public Href address;
    public LocalDate createDate;
    public String email;
    public String firstName;
    public int id;
    public String lastName;
    public Href store;
}