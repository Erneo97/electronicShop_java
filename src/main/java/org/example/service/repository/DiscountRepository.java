package org.example.service.repository;

import org.example.model.discount.Discount;
import org.example.model.product.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DiscountRepository {
    private final List<Discount> discounts = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public DiscountRepository() {
        Discount minus20PercentForAllSmartphones = new Discount ("Minus 20 % na wszystkie telefony!",
                p -> p.getType() == TypeProduct.SMARTPHONE,
                p -> p.getTotalPrice()
                        .subtract(p.getTotalPrice().multiply(BigDecimal.valueOf(0.2))
                        ));

        Discount minus200AllElectronics = new Discount(
                "200 zł na elektronikę",
                p -> p.getType() == TypeProduct.SMARTPHONE,
                p -> p.getTotalPrice()
                        .subtract(BigDecimal.valueOf(200))
        );

        discounts.add(minus20PercentForAllSmartphones);
        discounts.add(minus200AllElectronics);
    }

    public List<Discount> getAllDiscount() {
        lock.readLock().lock();
        List<Discount> allDiscounts = List.copyOf(discounts);
        lock.readLock().unlock();
        return allDiscounts;
    }

    public void addNewDiscount(Discount discount) {
        lock.writeLock().lock();
        this.discounts.add(discount);
        lock.writeLock().unlock();
    }

    public void removeDiscount(Discount discount) {
        lock.writeLock().lock();
        this.discounts.remove(discount);
        lock.writeLock().unlock();

    }
}
