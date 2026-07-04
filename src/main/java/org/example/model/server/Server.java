package org.example.model.server;


import org.example.model.product.Product;

import java.util.List;

public class Server {
    Repository repository = new Repository();

    public List<Product> getProducts() {
        return repository.getAllProducts();
    }

}
