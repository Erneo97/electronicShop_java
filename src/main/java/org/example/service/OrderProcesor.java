package org.example.service;

import org.example.service.repository.ProductRepository;

public class OrderProcesor {
    ProductRepository repository;

    public OrderProcesor(ProductRepository productRepository) {
        this.repository = productRepository;
    }
}
