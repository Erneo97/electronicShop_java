package org.example.service.repository;

import lombok.Getter;
import org.example.model.product.Product;
import org.example.model.product.ProductConfiguration;
import org.example.model.product.TechnicalParameter;
import org.example.model.product.ConfigurationParameter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private void initProductsSmartphone(List<Product> products) {
        Product smartphone1 = new Product("Smamsung galaxy s20");
        Product smartphone2 = new Product("Apple 12 PRO");
        smartphone1.addVariant(getTestSmartphoneVariant1());
        smartphone1.addVariant(getTestSmartphoneVariant2());
        smartphone2.addVariant(getTestSmartphoneVariant2());
        products.add(smartphone1);
        products.add(smartphone2);
    }

    private ProductConfiguration getTestSmartphoneVariant1() {
        ProductConfiguration variant = new ProductConfiguration();
        variant.setPrice(BigDecimal.valueOf(1234.43));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.COLOR, "Blue"));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.MEMORY, "120 GB"));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variant;
    }

    private ProductConfiguration getTestSmartphoneVariant2() {
        ProductConfiguration variant = new ProductConfiguration();
        variant.setPrice(BigDecimal.valueOf(2500));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.COLOR, "Blue"));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.MEMORY, "120 GB"));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.PROCESSOR, "5 GHz"));
        variant.addOrChangeParameterToVariant(new ConfigurationParameter(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variant;
    }

}
