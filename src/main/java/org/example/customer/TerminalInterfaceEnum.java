package org.example.customer;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;

@AllArgsConstructor
public enum TerminalInterfaceEnum {
    LIST_PRODUCT(1),
    DISPLAY_CART(2),
    ADD_CART(3),
    ORDER_CART(4),
    INVOICE_LAST_ORDER(5),
    EXIT(15),
    NOT_RECOGNIZE_OPERATION(0);

    @NonNull
    private final int operation;

    public static TerminalInterfaceEnum from(int operation) {
        return Arrays.stream(TerminalInterfaceEnum.values())
                .filter(terminalInterfaceEnum ->  terminalInterfaceEnum.operation == operation)
                .findFirst()
                .orElse(NOT_RECOGNIZE_OPERATION);
    }
}
