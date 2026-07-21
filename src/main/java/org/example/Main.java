package org.example;

import org.example.customer.TerminalInterface;
import org.example.model.cart.Cart;
import org.example.model.product.Product;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.service.repository.ProductRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final ProductManager productManager = new ProductManager(productRepository);
    private static final OrderProcesor orderProcessor = new OrderProcesor(productRepository);

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        TerminalInterface terminal = new TerminalInterface(productManager, orderProcessor);
        List<Product> products = terminal.getProducts();

        products.forEach(System.out::println);

        Cart cart = new Cart();
        Product testProduct = products.getFirst();
        cart.addToCart(testProduct, testProduct.getConfiguration());
        Product testProduc2 = products.get(1);
        cart.addToCart(testProduc2, testProduc2.getConfiguration());

        System.out.println(cart);
        Future<Boolean> orderResponse = terminal.makeOrder(cart);
        System.out.println("Złożenie zamówienia: " + (orderResponse.get(500, TimeUnit.MICROSECONDS) ? "udana" : "Nie udana"));

        System.out.println(cart);
    }
}