package utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffHref {

    String href;

    public StaffHref(){}
    public StaffHref(String s) {
        this.href = s;
    }
}
