package org.example;

import org.example.customer.TerminalInterface;
import org.example.customer.TerminalInterfaceEnum;
import org.example.model.cart.Cart;
import org.example.model.invoice.Invoice;
import org.example.model.invoice.PolishInvoice;
import org.example.model.order.Order;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.service.repository.ProductRepository;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final ProductManager productManager = new ProductManager(productRepository);
    private static final OrderProcesor orderProcessor = new OrderProcesor(productRepository);
    private static final Invoice polishInvoice = new PolishInvoice();

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        TerminalInterface terminal = new TerminalInterface(productManager, orderProcessor, polishInvoice);
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();

        System.out.println("""
                Sklep internetowy z elektrtoniką
                \tW czym możemy pomóc?""");

        TerminalInterfaceEnum operation = TerminalInterfaceEnum.LIST_PRODUCT;

        while (operation != TerminalInterfaceEnum.EXIT) {
            System.out.print("""
                    Dozwolone komendy w sklepie:
                    1 - Wyświetl listę produktów
                    2 - Wyświetl koszyk
                    3 - Dodaj produkt do koszyka
                    4 - Złóż zamówienie
                    5 - Wyświetl fakturę za ostatie zamówienie
                    15 - Zamknij sklep
                    Twój wybór:""");

            operation = TerminalInterfaceEnum.from(scanner.nextInt());
            performSelectedOperation(operation, terminal, cart);
        }
    }

    private static void performSelectedOperation(TerminalInterfaceEnum operation, TerminalInterface terminal, Cart cart) {
        switch (operation) {
            case LIST_PRODUCT -> terminal.displayProducts();
            case DISPLAY_CART -> System.out.println(cart);
            case ADD_CART -> handleADDCart(terminal, cart);
            case ORDER_CART -> handleOrderCart(terminal, cart);
            case INVOICE_LAST_ORDER -> handleInvoiceLastOrder(terminal);
            case EXIT -> System.out.println("Dziękujemy za zakupy zapraszamy ponownnie");
        }
    }

    private static void handleADDCart(TerminalInterface terminal, Cart cart) {
        System.out.println("Wybierz produkt z listy:");
        terminal.displayProducts();
        terminal.selectProduct().forEach(cart::addToCart);
    }

    private static void handleOrderCart(TerminalInterface terminal, Cart cart) {
        System.out.println(cart);
        Future<Order> orderResponse = terminal.makeOrder(cart);
        try {
            terminal.setLastOrder(orderResponse.get(500, TimeUnit.SECONDS));
            System.out.println("Złożone zamówienie: " + terminal.getLastOrder());
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleInvoiceLastOrder(TerminalInterface terminal) {
        if (terminal.getLastOrder() != null) {
            System.out.println("\tWygenerowana faktura:\n" + terminal.generateInvoicByOrder(terminal.getLastOrder()));
        } else {
            System.out.println("Nie złożono zamówienia");
        }
    }
}