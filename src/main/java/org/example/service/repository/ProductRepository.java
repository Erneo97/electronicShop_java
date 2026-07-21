package org.example.service.repository;

import lombok.Getter;
import org.example.model.product.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    Lock lock = new ReentrantLock();

    public ProductRepository() {
        initProductsSmartphone(products);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void addNewProduct(Product product) {
        // TODO: throw jeżeli nie unikalny
        lock.lock();
        products.add(product);
        lock.unlock();
    }

    public void removeProduct(Product product) {
        // TODO: throw jeżeli nie istnieje
        lock.lock();
        products.remove(product);
        lock.unlock();
    }

    public Optional<Product> getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
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
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.COLOR, "Blue", BigDecimal.valueOf(0L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.COLOR, "Red", BigDecimal.valueOf(0L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.MEMORY, "120 GB", BigDecimal.valueOf(1000L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.MEMORY, "1256 GB", BigDecimal.valueOf(1500L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.RAM_SIZE, "16 GB", BigDecimal.valueOf(200L)));
        return variant;
    }

    private ProductConfiguration getTestSmartphoneVariant2() {
        ProductConfiguration variant = new ProductConfiguration();
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.COLOR, "Blue", BigDecimal.valueOf(0L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.MEMORY, "120 GB", BigDecimal.valueOf(100L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.PROCESSOR, "7 GHz", BigDecimal.valueOf(1500L)));
        variant.addParameterToConfiguration(new ConfigurationParameter(TechnicalParameter.RAM_SIZE, "16 GB", BigDecimal.valueOf(100L)));
        return variant;
    }

}
