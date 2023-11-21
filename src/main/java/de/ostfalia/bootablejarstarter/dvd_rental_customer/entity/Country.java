package de.ostfalia.bootablejarstarter.dvd_rental_customer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity-Klasse für country
 */
@Getter
@Setter
@Entity
@NamedQuery(name = "getByCountry", query = "SELECT c FROM Country c WHERE c.country = :country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "country_country_id_seq")
    @NotNull
    @Column(name = "country_id")
    private int countryId;

    @NotNull
    @Column
    private String country;

    @NotNull
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}