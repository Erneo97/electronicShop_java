package org.example.model.product;

import java.util.Arrays;

public enum TypeProduct {
    SMARTPHONE("Smartfon"), COMPUTER("Komputer"), ELECTRONICS("Elektronika");

    private String type;

    private TypeProduct(String type) {
        this.type = type;
    }

    public TypeProduct fromString(String type) {
        return Arrays.stream(TypeProduct.values())
                .filter(typeProduct -> typeProduct.type.equals(type))
                .findFirst()
                .orElse(ELECTRONICS);
    }
}
