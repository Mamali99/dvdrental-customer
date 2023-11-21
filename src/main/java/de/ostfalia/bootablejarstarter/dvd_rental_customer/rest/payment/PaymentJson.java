package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.payment;


import de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.Href;
import lombok.AllArgsConstructor;

    /**
     * POJO für Response-JSON
     */
    @AllArgsConstructor
    public class PaymentJson {
        public double amount;
        public Href customer;
        public int id;
        public Href rental;
        public Href staff;
    }

