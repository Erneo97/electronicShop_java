package org.example.model.product;

import lombok.Data;

@Data
public abstract class Product {
    protected final String id;
    protected String name, description = "'brak opisu'";
    protected TypeProduct type = TypeProduct.ELECTRONICS;

    @Override
    public String toString() {
        return String.format("'%s' (%s) Opis: %s\n", name, id, description);
    }
}
