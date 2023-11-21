package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest.payment;

import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDateTime;

/**
 * POJO für Request-JSON
 */
@NoArgsConstructor
public class PaymentReqBody {
    public double amount;
    public int rental;
    public int customer;
    public int staff;
    @JsonbDateFormat("yyyy-MM-dd HH:mm") public LocalDateTime date;
}
