package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq_gen")
    @SequenceGenerator(name = "country_seq_gen", sequenceName = "public.country_country_id_seq", allocationSize = 1)
    private Integer country_id;

    private String country;

    private Timestamp last_update = Timestamp.valueOf(LocalDateTime.now());


}
