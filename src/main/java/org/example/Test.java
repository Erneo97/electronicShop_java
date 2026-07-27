package org.example;

import org.example.customer.UserCommandLineInterface;
import org.example.model.discount.Discount;
import org.example.model.invoice.Invoice;
import org.example.model.invoice.PolishInvoice;
import org.example.model.product.Product;
import org.example.model.product.TypeProduct;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.service.repository.DiscountRepository;
import org.example.service.repository.ProductRepository;

import java.math.BigDecimal;

public class Test {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final ProductManager productManager = new ProductManager(productRepository);
    private static final OrderProcesor orderProcessor = new OrderProcesor(productRepository);
    private static final Invoice polishInvoice = new PolishInvoice();
    private static final DiscountRepository discountRepository = new DiscountRepository();


    public static void main(String[] args){
        UserCommandLineInterface terminal = new UserCommandLineInterface(productManager, orderProcessor, polishInvoice, discountRepository);

        Discount minus20PercentForAllSmartphones = new Discount(
                "Minus 20 % na wszystkie telefony!"
                ,p -> p.getType() == TypeProduct.SMARTPHONE,
                p -> p.getTotalPrice()
                        .subtract(p.getTotalPrice().multiply(BigDecimal.valueOf(0.2))
                        ));

        Product first = terminal.getProducts().get(0);

        System.out.println("Cena przed obniżką: " + first.getTotalPrice());
        System.out.println("Cena po obniżce : " + minus20PercentForAllSmartphones.applyDiscount(first));
    }
}
