package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class City {
    @Id
    private Integer city_id;
    private String city;
    private Integer country_id;
    private LocalDateTime last_update;


}