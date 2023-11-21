package de.ostfalia.bootablejarstarter.dvd_rental_customer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity-Klasse für city
 */
@Getter
@Setter
@Entity
@NamedQuery(name = "getByCity", query = "SELECT c FROM City c WHERE c.city = :city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_city_id_seq")
    @NotNull
    @Column(name = "city_id")
    private int cityId;

    @NotNull
    @Column
    private String city;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @NotNull
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}
