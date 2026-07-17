package org.example;

import org.example.customer.TerminalInterface;
import org.example.model.cart.Cart;
import org.example.model.product.Product;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TerminalInterface terminal = new TerminalInterface();
        List<Product> products = terminal.getProducts();

        products.forEach(System.out::println);

        Cart cart = new Cart();
        Product testProduct = products.getFirst();
        cart.addToCart(testProduct, testProduct.getConfiguration());
        Product testProduc2 = products.get(1);
        cart.addToCart(testProduc2, testProduc2.getConfiguration());

        System.out.println(cart);
    }
}