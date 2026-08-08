package org.example.model.invoice;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class generates an invoice for an order in Polish
 */
public class PolishInvoice implements Invoice {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", new Locale("pl", "PL"));

    @Override
    public String generateOrderInvoice(Order order) {
        StringBuilder invoice = new StringBuilder();

        addMainHeaderToInvoice(invoice);
        addOrderInformationToInvoice(invoice, order);
        addOrderElementsTOInvoice(invoice, order);
        addCostToInvoice(invoice, order);
        
        return invoice.toString();
    }

    private void addMainHeaderToInvoice(StringBuilder invoice) {
        invoice.append("=========================================\n");
        invoice.append("              FAKTURA VAT\n");
        invoice.append("=========================================\n\n");
    }

    private void addOrderInformationToInvoice(StringBuilder invoice, Order order) {
        invoice.append("Nr zamówienia: ").append(Math.abs(order.hashCode())).append("\n");
        invoice.append("Data wystawienia: ")
                .append(order.createdAt().format(DATE_FORMATTER))
                .append("\n");
        invoice.append("Id klienta: ")
                .append(order.idUser())
                .append("\n\n");
    }

    private void addOrderElementsTOInvoice(StringBuilder invoice, Order order) {
        invoice.append("-----------------------------------------\n");
        invoice.append("Pozycje zamówienia\n");
        invoice.append("-----------------------------------------\n");

        AtomicInteger lp = new AtomicInteger(0);
        order.products().forEach(item -> {
            invoice.append(lp.getAndIncrement())
                    .append(". Produkt ID: ")
                    .append(item.idProduct())
                    .append("\n");

            addProductConfiuguration(invoice, item);
            invoice.append("\n");
        });
    }

    private void addProductConfiuguration(StringBuilder invoice, OrderItem item) {
        if (item.parameters().isEmpty()) {
            invoice.append("   Brak konfiguracji\n");
        } else {
            invoice.append("   Parametry:\n");
            addAllParametersConfigurationOrderItem(invoice, item);
        }
    }

    private void addAllParametersConfigurationOrderItem(StringBuilder invoice, OrderItem item) {
        item.parameters().forEach(parameter -> {
            invoice.append("      - Parametr ID: ")
                    .append(parameter.idParameter())
                    .append(", ilość: ")
                    .append(parameter.quantity())
                    .append("\n");
        });
    }

    private void addCostToInvoice(StringBuilder invoice, Order order) {
        invoice.append("-----------------------------------------\n");
        invoice.append("Łączna wartość: ")
                .append(order.price().setScale(2))
                .append(" zł\n");
        invoice.append("-----------------------------------------\n\n");
    }
}
