package org.example.model.server;


import org.example.model.product.Product;
import org.example.repository.ProductRepository;

import java.util.List;

public class Server {
    ProductRepository repository = new ProductRepository();

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
