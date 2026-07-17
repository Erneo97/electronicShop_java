package org.example.model.product;

import lombok.Data;
import lombok.NonNull;
import org.example.model.cart.CartItem;
import org.example.model.cart.Shoppable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Product implements Shoppable {
    private static AtomicInteger counterID = new AtomicInteger(0);
    private final int id = counterID.getAndIncrement();
    @NonNull
    protected String name, description = "'brak opisu'";
    protected TypeProduct type = TypeProduct.ELECTRONICS;
    @NonNull
    protected BigDecimal price;

    private final ProductConfiguration configuration = new ProductConfiguration();

    public void adddConfiguration(ProductConfiguration configuration) {
        configuration.addAllParameters(configuration);
    }

    @Override
    public CartItem addToCart(ProductConfiguration configuration) {
        return new CartItem(id, name, description, 1, configuration);
    }

    @Override
    public String toString() {
        return String.format("%10s '%s' (%s) Opis: %s\n%s", type.name(), name, id, description, configuration);
    }

    public Optional<ConfigurationParameter> getConfigurationById(int id) {
        return configuration.getParameterById(id);
    }

    public boolean checkProductVariantExists(ProductConfiguration configuration) {
        return configuration.isConfigurationAvaliable(configuration);
    }
}
