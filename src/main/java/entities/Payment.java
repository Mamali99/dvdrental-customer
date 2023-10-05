package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Payment {

    @Id
    private Integer payment_id;
    private Integer customer_id;
    private Integer staff_id;
    private Integer rental_id;
    private BigDecimal amount;
    private LocalDateTime payment_date;


}
