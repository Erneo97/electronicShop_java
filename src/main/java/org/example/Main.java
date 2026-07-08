package org.example;

import org.example.customer.TerminalInterface;
import org.example.model.cart.Cart;
import org.example.model.product.Product;
import org.example.model.product.VariantProduct;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TerminalInterface terminal = new TerminalInterface();
        List<Product> products = terminal.getProducts();

        products.forEach(System.out::println);

        Cart cart = new Cart();
        VariantProduct testProduct = (VariantProduct) products.getFirst();
        cart.addToCart(testProduct, testProduct.getVariants().stream().findFirst().get());
        VariantProduct testProduc2 = (VariantProduct) products.get(1);
        cart.addToCart(testProduc2, testProduc2.getVariants().stream().findFirst().get());

        System.out.println(cart);
    }
}