package org.example.model.server;


import org.example.model.product.Product;

import java.util.List;

public class Server {
    Repository repository = new Repository();

    public List<Product> getProducts() {
        return repository.getAllProducts();
    }

    public void addProduct(Product product) {
        repository.addNewProduct(product);
    }

    public void removeProduct(Product product) {
        repository.removeProduct(product);
    }
}
