package com.vintage.domain.model;


import java.math.BigDecimal;
import java.util.Objects;

public class SummarizedPurchase {

    private String customerCpf;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public SummarizedPurchase(final String customerCpf, final int quantity, final BigDecimal price) {
        this.customerCpf = customerCpf;
        this.quantity = quantity;
        this.price = price;
    }

    public SummarizedPurchase() {}

    public void calculateTotal() {
        total = price.multiply(BigDecimal.valueOf(quantity));
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setCustomerCpf(final String customerCpf) {
        this.customerCpf = customerCpf;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummarizedPurchase that = (SummarizedPurchase) o;
        return quantity == that.quantity &&
                Objects.equals(customerCpf, that.customerCpf) &&
                Objects.equals(price, that.price) &&
                Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerCpf, quantity, price, total);
    }
}