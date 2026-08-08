package org.example.model.order.exeptions;

public class ProductNotExistsExeptions extends RuntimeException {
    public ProductNotExistsExeptions(String message) {
        super(message);
    }
}
