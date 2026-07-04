package org.example;

import org.example.model.cart.Cart;
import org.example.model.product.Product;
import org.example.model.product.VariantProduct;
import org.example.model.product.variant.Smartphone;
import org.example.model.product.variant.TechnicalParameter;
import org.example.model.product.variant.Variant;
import org.example.model.product.variant.VariantItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        initProductsSmartphone(products);
        products.forEach(System.out::println);

        Cart cart = new Cart();

        VariantProduct testProduct = (VariantProduct) products.getFirst();
        cart.addToCart(testProduct, testProduct.getVariants().stream().findFirst().get());

        VariantProduct testProduc2 = (VariantProduct) products.get(1);
        cart.addToCart(testProduc2, testProduc2.getVariants().stream().findFirst().get());

        System.out.println(cart);

    }

    private static void initProductsSmartphone(List<Product> products) {
        Smartphone smartphone1 = new Smartphone("Smart1", "Smamsung galaxy s20");
        Smartphone smartphone2 = new Smartphone("Smart2", "Apple 12 PRO");
        smartphone1.addVariant(getTestSmartphoneVariant1());
        smartphone1.addVariant(getTestSmartphoneVariant2());
        smartphone2.addVariant(getTestSmartphoneVariant2());
//        smartphone2.addVariant(getTestSmartphoneVariant1());
        products.add(smartphone1);
        products.add(smartphone2);
    }

    private static Variant getTestSmartphoneVariant1() {
        Variant variant = new Variant();
        variant.setPrice(BigDecimal.valueOf(1234.43));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.COLOR, "Blue"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.MEMORY, "120 GB"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variant;
    }

    private static Variant getTestSmartphoneVariant2() {
        Variant variant = new Variant();
        variant.setPrice(BigDecimal.valueOf(2500));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.COLOR, "Blue"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.MEMORY, "120 GB"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.PROCESSOR, "5 GHz"));
        variant.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variant;
    }
}