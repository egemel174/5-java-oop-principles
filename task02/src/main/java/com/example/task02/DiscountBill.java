package com.example.task02;

public class DiscountBill extends Bill {

    private final int discount;

    public DiscountBill(int discount) {
        this.discount = discount;
    }

    /**
     * Возвращает размер скидки в процентах
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Возвращает абсолютное значение скидки
     */
    public long getDiscountAmount() {
        return super.getPrice() * discount / 100;
    }

    /**
     * Возвращает итоговую стоимость со скидкой
     */
    @Override
    public long getPrice() {
        return super.getPrice() - getDiscountAmount();
    }
}