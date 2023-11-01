package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen", sequenceName = "public.customer_customer_id_seq", allocationSize = 1)
    private Integer customer_id;

    private Integer store_id;
    private String first_name;
    private String last_name;
    private String email;
    @ManyToOne
    @JoinColumn(name="address_id")
    @JsonbTransient
    private Address address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonbTransient
    private List<Payment> payments;

    private Boolean activebool;
    private Date create_date;
    private Timestamp last_update = Timestamp.valueOf(LocalDateTime.now());
    private Integer active;
}
