package org.example.model.order.exeptions;

public class ProductNotExists extends Exception {
    public ProductNotExists(String message) {
        super(message);
    }
}
