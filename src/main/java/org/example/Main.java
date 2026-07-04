package org.example;

import org.example.model.product.Product;
import org.example.model.product.VariantProduct;
import org.example.model.product.variant.Smartphone;
import org.example.model.product.variant.TechnicalParameter;
import org.example.model.product.variant.VariantElectronic;
import org.example.model.product.variant.VariantItem;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        initProductsSmartphone(products);

        products.forEach(System.out::println);
    }

    private static void initProductsSmartphone(List<Product> products) {
        Smartphone smartphone1 = new Smartphone("Smart1", "Smamsung galaxy s20");
        Smartphone smartphone2 = new Smartphone("Smart2", "Apple 12 PRO");
        smartphone1.addVariant(getTestSmartphoneVariant1());
        smartphone1.addVariant(getTestSmartphoneVariant2());
        smartphone2.addVariant(getTestSmartphoneVariant1());
        smartphone2.addVariant(getTestSmartphoneVariant2());
        products.add(smartphone1);
        products.add(smartphone2);
    }

    private static VariantElectronic getTestSmartphoneVariant1() {
        VariantElectronic variantElectronic = new VariantElectronic();
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.COLOR, "Blue"));
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.MEMORY, "120 GB"));
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variantElectronic;
    }

    private static VariantElectronic getTestSmartphoneVariant2() {
        VariantElectronic variantElectronic = new VariantElectronic();
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.COLOR, "Blue"));
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.MEMORY, "120 GB"));
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.PROCESSOR, "5 GH"));
        variantElectronic.addOrChangeParameterToVariant(new VariantItem(TechnicalParameter.RAM_SIZE, "16 GB"));
        return variantElectronic;
    }
}