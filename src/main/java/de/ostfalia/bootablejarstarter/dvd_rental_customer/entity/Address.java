package de.ostfalia.bootablejarstarter.dvd_rental_customer.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity-Klasse für address
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQuery(name = "getAll", query = "SELECT a FROM Address a")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "address_address_id_seq")
    @NotNull
    @Column(name = "address_id")
    private int addressId;

    @NotNull
    @Column
    private String address;

    @Column
    private String address2;

    @NotNull
    @Column
    private String district;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "postal_code")
    private String postalCode;

    @NotNull
    @Column
    private String phone;

    @NotNull
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

}