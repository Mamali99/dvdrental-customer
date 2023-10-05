package entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Address {


        @Id
        private Integer address_id;
        private String address;
        private String address2;
        private String district;
        private Integer city_id;
        private String postal_code;
        private String phone;
        private LocalDateTime last_update;
        // ... Weitere Felder und Methoden ...
}
