package de.ostfalia.bootablejarstarter.dvd_rental_customer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity-Klasse für payment
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "getByCustomer", query = "SELECT p FROM Payment p WHERE p.customer = :c")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payment_payment_id_seq")
    @NotNull
    @Column(name = "payment_id")
    private int paymentId;

    @NotNull
    @ManyToOne
    // foreign key
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Column(name = "staff_id")
    private int staffId;

    @NotNull
    @Column(name = "rental_id")
    private int rentalId;

    @NotNull
    @Column
    private double amount;

    //hier auch
    @NotNull
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
}
