package org.example;

import org.example.customer.TerminalInterface;
import org.example.customer.TerminalInterfaceEnum;
import org.example.model.cart.Cart;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.service.repository.ProductRepository;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final ProductManager productManager = new ProductManager(productRepository);
    private static final OrderProcesor orderProcessor = new OrderProcesor(productRepository);

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        TerminalInterface terminal = new TerminalInterface(productManager, orderProcessor);
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
                    15 - Zamknij sklep
                    Twój wybór:""");

            operation = TerminalInterfaceEnum.from(scanner.nextInt());
            performSelectedOperation(operation, terminal, cart);
        }

//        Product testProduct = products.getFirst();
//        cart.addToCart(testProduct, testProduct.getConfiguration());
//        Product testProduc2 = products.get(1);
//        cart.addToCart(testProduc2, testProduc2.getConfiguration());
//
//        System.out.println(cart);
//        Future<Boolean> orderResponse = terminal.makeOrder(cart);
//        System.out.println("Złożenie zamówienia: " + (orderResponse.get(500, TimeUnit.MICROSECONDS) ? "udana" : "Nie udana"));
//
//        System.out.println(cart);
    }

    private static void performSelectedOperation(TerminalInterfaceEnum operation, TerminalInterface terminal, Cart cart) {
        switch (operation) {
            case LIST_PRODUCT -> terminal.getProducts().forEach(System.out::println);
            case DISPLAY_CART -> System.out.println(cart);
            case EXIT -> System.out.println("Dziękujemy za zakupy zapraszamy ponownnie");
        }
    }

}