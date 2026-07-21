package org.example.model.invoice;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PolishInvoice implements Invoice {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", new Locale("pl", "PL"));

    @Override
    public String generateOrderInvoice(Order order) {
        StringBuilder invoice = new StringBuilder();

        invoice.append("=========================================\n");
        invoice.append("              FAKTURA VAT\n");
        invoice.append("=========================================\n\n");

        invoice.append("Nr zamówienia: ").append(order.hashCode()).append("\n");
        invoice.append("Data wystawienia: ")
                .append(order.createdAt().format(DATE_FORMATTER))
                .append("\n");
        invoice.append("Id klienta: ")
                .append(order.idUser())
                .append("\n\n");

        invoice.append("-----------------------------------------\n");
        invoice.append("Pozycje zamówienia\n");
        invoice.append("-----------------------------------------\n");

        int lp = 1;

        for (OrderItem item : order.products()) {
            invoice.append(lp++)
                    .append(". Produkt ID: ")
                    .append(item.idProduct())
                    .append("\n");

            if (item.parameters().isEmpty()) {
                invoice.append("   Brak konfiguracji\n");
            } else {
                invoice.append("   Parametry:\n");

                for (ParameterOfOrder parameter : item.parameters()) {
                    invoice.append("      - Parametr ID: ")
                            .append(parameter.idParameter())
                            .append(", ilość: ")
                            .append(parameter.quantity())
                            .append("\n");
                }
            }
            invoice.append("\n");
        }

        invoice.append("-----------------------------------------\n");
        invoice.append("Łączna wartość: ")
                .append(order.price().setScale(2))
                .append(" zł\n");
        invoice.append("-----------------------------------------\n\n");

        invoice.append("Dziękujemy za dokonanie zakupu!\n");
        invoice.append("Zapraszamy ponownie.\n");

        return invoice.toString();
    }
}
