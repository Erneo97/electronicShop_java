package org.example;

import org.example.model.product.Product;
import org.example.model.server.Server;

import java.util.List;

public class APIClient {
    Server server = new Server();

    public List<Product> getProducts() {
        return server.getProducts();
    }

    public void addProduct(Product product) {
        server.addProduct(product);
    }

    public void removeProduct(Product product) {
        server.removeProduct(product);
    }

}
