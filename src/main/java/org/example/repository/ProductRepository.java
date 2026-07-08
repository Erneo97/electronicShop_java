package org.example.repository;

import lombok.Getter;
import org.example.model.product.Product;
import org.example.model.product.variant.Smartphone;
import org.example.model.product.variant.TechnicalParameter;
import org.example.model.product.variant.Variant;
import org.example.model.product.variant.VariantItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class ProductRepository {
    private static List<Product> products;
    Lock lock = new ReentrantLock();

    public ProductRepository() {
        lock.lock();
        if (products == null) {
            products = new ArrayList<>();
            initProductsSmartphone(products);
        }
        lock.unlock();
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
        Smartphone smartphone1 = new Smartphone("Smart1", "Smamsung galaxy s20");
        Smartphone smartphone2 = new Smartphone("Smart2", "Apple 12 PRO");
        smartphone1.addVariant(getTestSmartphoneVariant1());
        smartphone1.addVariant(getTestSmartphoneVariant2());
        smartphone2.addVariant(getTestSmartphoneVariant2());
        products.add(smartphone1);
        products.add(smartphone2);
    }

    private Variant getTestSmartphoneVariant1() {
        Variant variant = new Variant();
        variant.setPrice(BigDecimal.valueOf(1234.43));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.COLOR, "Blue"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.MEMORY, "120 GB"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variant;
    }

    private Variant getTestSmartphoneVariant2() {
        Variant variant = new Variant();
        variant.setPrice(BigDecimal.valueOf(2500));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.COLOR, "Blue"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.MEMORY, "120 GB"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.PROCESSOR, "5 GHz"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variant;
    }

}
