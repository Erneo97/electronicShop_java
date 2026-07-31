package org.example;

import org.example.customer.UserCommandLineInterface;
import org.example.customer.TerminalInterfaceEnum;
import org.example.model.cart.Cart;
import org.example.model.invoice.Invoice;
import org.example.model.invoice.PolishInvoice;
import org.example.model.order.Order;
import org.example.model.order.exeptions.ProductNotExistsExeptions;
import org.example.model.order.exeptions.SelectedParametersNotAvaliableExeption;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.service.repository.DiscountRepository;
import org.example.service.repository.ProductRepository;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final ProductManager productManager = new ProductManager(productRepository);
    private static final OrderProcesor orderProcessor = new OrderProcesor(productRepository);
    private static final Invoice polishInvoice = new PolishInvoice();
    private static final DiscountRepository discountRepository = new DiscountRepository();

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        UserCommandLineInterface terminal = new UserCommandLineInterface(productManager, orderProcessor, polishInvoice, discountRepository);
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();

        System.out.println("""
                Sklep internetowy z elektrtoniką
                \tW czym możemy pomóc?""");

        TerminalInterfaceEnum operation = TerminalInterfaceEnum.LIST_PRODUCT;

        while (operation != TerminalInterfaceEnum.EXIT) {
            displayMenu();
            operation = TerminalInterfaceEnum.from(scanner.nextInt());
            performSelectedOperation(operation, terminal, cart);
        }
    }

    private static void displayMenu() {
        System.out.printf("""
                        Dozwolone komendy w sklepie:
                        %d - Wyświetl listę produktów
                        %d - Wyświetl koszyk
                        %d - Dodaj produkt do koszyka
                        %d - Usuń produkt z koszyka
                        %d - Złóż zamówienie
                        %d - Wyświetl fakturę za ostatie zamówienie
                        %d - Dodaj zniżkę
                        %d - Zamknij sklep
                        Twój wybór:""",
                TerminalInterfaceEnum.LIST_PRODUCT.getOperation(),
                TerminalInterfaceEnum.DISPLAY_CART.getOperation(),
                TerminalInterfaceEnum.ADD_TO_CART.getOperation(),
                TerminalInterfaceEnum.REMOVE_FROM_CART.getOperation(),
                TerminalInterfaceEnum.ORDER_CART.getOperation(),
                TerminalInterfaceEnum.INVOICE_LAST_ORDER.getOperation(),
                TerminalInterfaceEnum.ADD_DISCOUNT.getOperation(),
                TerminalInterfaceEnum.EXIT.getOperation());
    }

    private static void performSelectedOperation(TerminalInterfaceEnum operation, UserCommandLineInterface terminal, Cart cart) {
        switch (operation) {
            case LIST_PRODUCT -> terminal.displayProducts();
            case DISPLAY_CART -> System.out.println(cart);
            case ADD_TO_CART -> handleADDCart(terminal, cart);
            case REMOVE_FROM_CART -> handleRemoveCart(cart);
            case ORDER_CART -> handleOrderCart(terminal, cart);
            case INVOICE_LAST_ORDER -> handleInvoiceLastOrder(terminal);
            case ADD_DISCOUNT -> handleDiscount(terminal, cart);
            case EXIT -> System.out.println("Dziękujemy za zakupy zapraszamy ponownnie");
        }
    }

    private static void handleDiscount(UserCommandLineInterface terminal, Cart cart) {
        Scanner scanner = new Scanner(System.in);
        AtomicInteger indexDisplay = new AtomicInteger(0);
        System.out.print("Dostępne zniżki\n");
        terminal.getDiscounts().forEach(discount ->
                System.out.printf("%d) %s%n", indexDisplay.getAndIncrement(), discount)
        );
        System.out.println("Wybrany index zniżki: ");
        int selecteDiscountdIndex = scanner.nextInt();
        cart.addDiscount(terminal.getDiscounts().get(selecteDiscountdIndex));
        cart.setDiscountPrice(terminal.getDiscountPriceForCart(cart));
    }

    private static void handleRemoveCart(Cart cart) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(cart);
        System.out.print("Wybierz produkt z listy do usuniecia:");
        int index = scanner.nextInt();
        cart.removeFromCartByIndex(index);
    }

    private static void handleADDCart(UserCommandLineInterface terminal, Cart cart) {
        System.out.println("Wybierz produkt z listy:");
        terminal.displayProducts();
        terminal.selectProduct().forEach(cart::addToCart);
    }

    private static void handleOrderCart(UserCommandLineInterface terminal, Cart cart) {
        System.out.println(cart);
        try {
            Future<Order> orderResponse = terminal.makeOrder(cart);
            terminal.setLastOrder(orderResponse.get(500, TimeUnit.SECONDS));
            System.out.println("Złożone zamówienie:" + terminal.getLastOrder());
        } catch (ExecutionException | InterruptedException | TimeoutException e) { // TODO: sprawdzić
            Throwable cause = e.getCause();
            if (cause instanceof ProductNotExistsExeptions || cause instanceof SelectedParametersNotAvaliableExeption) {
                System.err.println(e.getMessage());
            }
        } catch (ProductNotExistsExeptions | SelectedParametersNotAvaliableExeption e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleInvoiceLastOrder(UserCommandLineInterface terminal) {
        if (terminal.getLastOrder() != null) {
            System.out.println("\tWygenerowana faktura:\n" + terminal.generateInvoiceByOrder(terminal.getLastOrder()));
        } else {
            System.out.println("Nie złożono zamówienia");
        }
    }
}