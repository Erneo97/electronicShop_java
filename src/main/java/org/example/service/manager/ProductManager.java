package org.example.service.manager;


import org.example.model.product.Product;
import org.example.service.repository.ProductRepository;

import java.util.List;

public class ProductManager {
    ProductRepository repository;

    public ProductManager(ProductRepository productRepository) {
        this.repository = productRepository;
    }

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
