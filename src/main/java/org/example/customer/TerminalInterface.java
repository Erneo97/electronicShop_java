package org.example.customer;

import org.example.manager.ProductManager;
import org.example.model.product.Product;

import java.util.List;

public class TerminalInterface {
    private final ProductManager productManager = new ProductManager();

    public List<Product> getProducts() {
        return productManager.getProducts();
    }

    public void addProduct(Product product) {
        productManager.addProduct(product);
    }

    public void removeProduct(Product product) {
        productManager.removeProduct(product);
    }
}
