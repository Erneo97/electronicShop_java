package org.example.model.product;

import lombok.Data;
import lombok.NonNull;
import org.example.model.cart.CartItem;
import org.example.model.cart.Shoppable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class representing a product. A product consists of an ID, available configuration for a given model, name, description, and the type of product being sold.
 */
@Data
public class Product implements Shoppable {
    private static AtomicInteger counterID = new AtomicInteger(0);
    private final int id;
    @NonNull
    protected String name, description = "'brak opisu'";
    protected TypeProduct type = TypeProduct.ELECTRONICS;
    @NonNull
    protected BigDecimal price;

    private final ProductConfiguration configuration = new ProductConfiguration();

    public Product(String name, BigDecimal price) {
        this.id = counterID.getAndIncrement();
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
        this.type = product.type;
        this.description = product.description;
    }

    @Override
    public CartItem addToCart() {
        return new CartItem(id, name, description, 1, configuration);
    }

    @Override
    public String toString() {
        return String.format("%5s) '%s' (%10s) Opis: %s\n%s", id, name, type.name(), description, configuration);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return price.add(configuration.getTotalPrice());
    }

    public void addConfiguration(ProductConfiguration configuration) {
        this.configuration.addAllParameters(configuration);
    }

    public Optional<ConfigurationParameter> getConfigurationById(int id) {
        return configuration.getParameterById(id);
    }

    public boolean checkProductVariantExists(ProductConfiguration configuration) {
        return this.configuration.isConfigurationAvailable(configuration);
    }

    public Product copyWithConfiguration(ProductConfiguration configuration) {
        Product copy = new Product(this);
        copy.configuration.clearConfiguration();
        copy.configuration.addAllParameters(configuration);
        return copy;
    }
}
