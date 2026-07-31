package org.example.service.repository;

import lombok.Getter;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.product.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A class that allows you to manage products available in the store.
 */
@Getter
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ProductRepository() {
        initProductsSmartphone(products);
    }

    public List<Product> getAllProducts() {
        lock.readLock().lock();
        List<Product> allProducts = List.copyOf(products);
        lock.readLock().unlock();
        return allProducts;
    }

    public void addNewProduct(Product product) {
        lock.writeLock().lock();
        products.add(product);
        lock.writeLock().unlock();
    }

    public void removeProduct(Product product) {
        lock.writeLock().lock();
        products.remove(product);
        lock.writeLock().unlock();
    }

    public void decreaseStock(List<OrderItem> orderItems) {
        lock.writeLock().lock();
        try {
            for (OrderItem orderItem : orderItems) {
                Product product = getProductById(orderItem.idProduct())
                        .orElseThrow();

                for (ParameterOfOrder parameter : orderItem.parameters()) {
                    ConfigurationParameter configuration = product.getConfigurationById(parameter.idParameter())
                            .orElseThrow();
                    configuration.setQuantity(
                            configuration.getQuantity() - parameter.quantity()
                    );
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Optional<Product> getProductById(int id) {
        lock.readLock().lock();
        Optional<Product> optFoundProduct = products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
        lock.readLock().unlock();
        return optFoundProduct;

    }

    private void initProductsSmartphone(List<Product> products) {
        Product smartphone1 = new Product("Smamsung galaxy s20", new BigDecimal(3500));
        smartphone1.setType(TypeProduct.SMARTPHONE);
        Product smartphone2 = new Product("Apple 12 PRO", new BigDecimal(5000));
        smartphone2.setType(TypeProduct.SMARTPHONE);
        smartphone1.addConfiguration(getTestSmartphoneVariant1());
        smartphone2.addConfiguration(getTestSmartphoneVariant2());
        products.add(smartphone1);
        products.add(smartphone2);
    }

    private ProductConfiguration getTestSmartphoneVariant1() {
        ProductConfiguration variant = new ProductConfiguration();
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.COLOR, "Blue", BigDecimal.valueOf(0L), 1));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.COLOR, "Red", BigDecimal.valueOf(0L), 3));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.MEMORY, "120 GB", BigDecimal.valueOf(1000L), 3));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.MEMORY, "1256 GB", BigDecimal.valueOf(1500L), 3));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.RAM_SIZE, "16 GB", BigDecimal.valueOf(200L), 3));
        return variant;
    }

    private ProductConfiguration getTestSmartphoneVariant2() {
        ProductConfiguration variant = new ProductConfiguration();
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.COLOR, "Blue", BigDecimal.valueOf(0L), 5));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.MEMORY, "120 GB", BigDecimal.valueOf(100L), 2));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.PROCESSOR, "7 GHz", BigDecimal.valueOf(1500L), 13));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.RAM_SIZE, "16 GB", BigDecimal.valueOf(100L), 3));
        return variant;
    }
}
