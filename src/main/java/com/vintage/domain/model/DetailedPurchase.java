package com.vintage.domain.model;

import java.util.Objects;

public class DetailedPurchase extends SummarizedPurchase {

    private String customerName;
    private String code;
    private String type;
    private String vintage;
    private int purchaseYear;

    public DetailedPurchase() {
        super();
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getVintage() {
        return vintage;
    }

    public void setVintage(final String vintage) {
        this.vintage = vintage;
    }

    public void setPurchaseYear(final int purchaseYear) {
        this.purchaseYear = purchaseYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DetailedPurchase that = (DetailedPurchase) o;
        return purchaseYear == that.purchaseYear &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(code, that.code) &&
                Objects.equals(type, that.type) &&
                Objects.equals(vintage, that.vintage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerName, code, type, vintage, purchaseYear);
    }
}