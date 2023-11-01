package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_address_id_seq")
        @SequenceGenerator(name = "address_address_id_seq", sequenceName = "public.address_address_id_seq", allocationSize = 1)
        private Integer address_id;

        @Column(nullable = false, length = 50)
        private String address;

        @Column(length = 50)
        private String address2;

        @Column(nullable = false, length = 20)
        private String district;

        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "city_id", nullable = false)
        @JsonbTransient
        private City city;

        @Column(length = 10)
        private String postal_code;

        @Column(nullable = false, length = 20)
        private String phone;

        @Column(name = "last_update", nullable = false)
        private Timestamp last_update = Timestamp.valueOf(LocalDateTime.now());

}
