package utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressHref {

    private String href;

    public AddressHref(){}

    public AddressHref(String s) {
        this.href = s;
    }
}
