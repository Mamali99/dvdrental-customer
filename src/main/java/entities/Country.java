package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Country {
    @Id
    private Integer country_id;
    private String country;
    private LocalDateTime last_update;


}
