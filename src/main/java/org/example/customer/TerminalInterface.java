package org.example.customer;

import org.example.model.cart.Cart;
import org.example.model.order.Order;
import org.example.model.product.ConfigurationParameter;
import org.example.model.product.ProductConfiguration;
import org.example.model.product.TechnicalParameter;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.model.product.Product;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class TerminalInterface {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    private final int userId = nextId.getAndIncrement();
    private final ProductManager productManager;
    private final OrderProcesor orderProcesor;

    public TerminalInterface(ProductManager productManager, OrderProcesor orderProcesor) {
        this.productManager = productManager;
        this.orderProcesor = orderProcesor;
    }

    public List<Product> getProducts() {
        return productManager.getProducts();
    }

    public void displayProducts() {
        getProducts().forEach(System.out::println);
    }

    public Product selectProduct() {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = getProducts();
        int productId = -1;

        while (productId < 0 || productId >= products.size()) {
            System.out.print("Podaj nr produktu któy chcesz personalizwoać: ");
            productId = scanner.nextInt();
        }
        System.out.println("Wybrany produkt: " + products.get(productId));
        return selectProductConfiguration(scanner, products.get(productId));
    }

    public void addProduct(Product product) {
        productManager.addProduct(product);
    }

    public void removeProduct(Product product) {
        productManager.removeProduct(product);
    }

    public Future<Boolean> makeOrder(Cart cart) {
        // TODO: wyjątek jaka operacja nie udana
        LocalDateTime createdAt = LocalDateTime.now();
        Order newOrder = new Order(userId, cart.getProductsToOrder(), createdAt, cart.getTotalPrice());
        cart.clearCart();
        return orderProcesor.makeOrder(newOrder);
    }

    private Product selectProductConfiguration(Scanner scanner, Product product) {
        ProductConfiguration configuration = product.getConfiguration();
        ProductConfiguration newConfiguration = new ProductConfiguration();
        Set<TechnicalParameter> categories = configuration.getCategories();

        categories.forEach(
                category -> {
                    int selectedIndex = -1;
                    List<ConfigurationParameter> parameters = configuration.getParametersByCategory(category);
                    displayParametersOnCategory(category, parameters);
                    while (selectedIndex < 1 || selectedIndex > parameters.size()) {
                        System.out.print("Wybierz konfigurację ");
                        selectedIndex = scanner.nextInt();
                    }
                    System.out.println("Wybrano: " + parameters.get(selectedIndex - 1).getValue());
                    newConfiguration.addParameterToConfiguration(parameters.get(selectedIndex - 1));
                }
        );
        System.out.println("Wybrana konfiguracja: " + newConfiguration);

        return product.copyWithConfiguration(newConfiguration);
    }

    private void displayParametersOnCategory(TechnicalParameter category, List<ConfigurationParameter> parameters) {
        System.out.println("Kategoria: " + category);
        AtomicInteger counter = new AtomicInteger(1);
        parameters.forEach(parametr -> System.out.printf("\t%d) %s\n", counter.getAndIncrement(), parametr.getValue()));
    }

    private Optional<Product> getProductById(int id) {
        return getProducts().stream().filter(product -> product.getId() == id).findFirst();
    }

}
