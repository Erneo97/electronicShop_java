package org.example.customer;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;

@AllArgsConstructor
public enum TerminalInterfaceEnum {
    LIST_PRODUCT(1),
    DISPLAY_CART(2),
    ADD_CART(3),
    EXIT(15),
    NOT_RECOGNIZE_OPERATION(-1);

    @NonNull
    private final int operation;

    public static TerminalInterfaceEnum from(int operation) {
        return Arrays.stream(TerminalInterfaceEnum.values())
                .filter(terminalInterfaceEnum ->  terminalInterfaceEnum.operation == operation)
                .findFirst()
                .orElse(NOT_RECOGNIZE_OPERATION);
    }
}
