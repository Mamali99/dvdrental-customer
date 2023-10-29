package entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RentalHref {

    String href;

    public RentalHref(){}

    public RentalHref(String s) {
        this.href = s;
    }
}
