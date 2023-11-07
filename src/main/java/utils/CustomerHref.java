package utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerHref {

    String href;

    public CustomerHref(){}

    public CustomerHref(String s) {
        this.href = s;
    }
}
