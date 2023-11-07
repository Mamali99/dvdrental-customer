package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_city_id_seq")
    @SequenceGenerator(name = "city_city_id_seq", sequenceName = "public.city_city_id_seq", allocationSize = 1)
    private Integer city_id;

    @Column(nullable = false, length = 50)
    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonbTransient
    private Country country;

    @Column(name = "last_update", nullable = false)
    private Timestamp last_update = Timestamp.valueOf(LocalDateTime.now());

    //@OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "city")
    @JsonbTransient
    private List<Address> addresses;

}
