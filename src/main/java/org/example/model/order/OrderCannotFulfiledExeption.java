package org.example.model.order;

public class OrderCannotFulfiledExeption extends Exception {
    public OrderCannotFulfiledExeption(String message) {
        super(message);
    }
}
