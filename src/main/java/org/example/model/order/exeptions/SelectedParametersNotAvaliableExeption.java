package org.example.model.order.exeptions;

public class SelectedParametersNotAvaliableExeption extends RuntimeException {
    public SelectedParametersNotAvaliableExeption(String message) {
        super(message);
    }
}
