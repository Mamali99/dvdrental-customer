package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    private Integer customer_id;

    private Integer store_id;
    private String first_name;
    private String last_name;
    private String email;
    private Boolean activebool;
    private Date create_date;
    private LocalDateTime last_update;
    private Integer active;


}
