package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreHref {

    private String href;

    public StoreHref() {

    }
    public StoreHref(String s) {
        this.href = s;
    }

}
