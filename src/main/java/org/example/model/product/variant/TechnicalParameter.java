package org.example.model.product.variant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalParameter {
    RAM_SIZE("Rozmiar RAM"), COLOR("Kolor"), PROCESSOR("Procesor"), MEMORY("Pamięć"), BATTERY_CAPACITY("Pojemność baterii"), HEIGHT("Wysokość"), WIDTH("Szerokość"), OTHER("Other");

    private final String parameter;
}
