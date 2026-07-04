package org.example.model.product.variant;

public enum TechnicalParameter {
    RAM_SIZE("Rozmiar RAM"), COLOR("Kolor"), PROCESSOR("Procesor"), MEMORY("Pamięć"), BATTERY_CAPACITY("Pojemność baterii"), OTHER("Other");

    private final String parameter;

    TechnicalParameter(String parameter) {
        this.parameter = parameter;
    }

   public String getParameter() {return this.parameter;}
}
