package com.vintage.domain.service;

import com.vintage.domain.exception.ResourceNotFoundException;
import com.vintage.domain.model.*;
import com.vintage.port.in.CustomerApiInputPort;
import com.vintage.port.out.CustomerApiOutputPort;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerService implements CustomerApiInputPort {

    private CustomerApiOutputPort customerApiOutputPort;
    private ProductService productService;
    private int loyalCustomerLimit;

    public CustomerService() {}

    public CustomerService(final CustomerApiOutputPort customerApiOutputPort,
                           final ProductService productService,
                           final int loyalCustomerLimit) {
        this.customerApiOutputPort = customerApiOutputPort;
        this.productService = productService;
        this.loyalCustomerLimit = loyalCustomerLimit;
    }

    public List<Customer> listCustomers() {
        return customerApiOutputPort.listCustomers();
    }

    public List<LoyalCustomer> listMostLoyalCustomers() {
        final List<Product> products = productService.list();
        return listCustomers()
                .stream()
                .map(customer -> {
                    final Map<String, BigDecimal> purchasesGroupedByCpf = customer.purchases()
                            .stream()
                            .map(purchase -> filterProductByCode(products, purchase.getCode())
                                    .map(product -> {
                                        var summarizedPurchase = new SummarizedPurchase(
                                                customer.cpf(),
                                                purchase.getQuantity(),
                                                product.price()
                                        );
                                        summarizedPurchase.calculateTotal();
                                        return summarizedPurchase;
                                    })
                                    .findFirst())
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(groupPurchasesByCustomerCpf());
                    return new LoyalCustomer(
                            customer.cpf(),
                            customer.name(),
                            purchasesGroupedByCpf.get(customer.cpf())
                    );
                })
                .sorted(Comparator.comparing(LoyalCustomer::totalPurchases).reversed())
                .limit(loyalCustomerLimit)
                .toList();
    }

    private Collector<SummarizedPurchase, ?, Map<String, BigDecimal>> groupPurchasesByCustomerCpf() {
        return Collectors.groupingBy(
                SummarizedPurchase::getCustomerCpf,
                Collectors.mapping(
                        SummarizedPurchase::getTotal,
                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                )
        );
    }

    public List<DetailedPurchase> listPurchases() {
        return listPurchasesFor(productService.list())
                .sorted(Comparator.comparing(DetailedPurchase::getTotal))
                .toList();
    }

    public Optional<String> findBiggestPurchaseCodeFrom(final String customerInfo) {
        return listCustomers().stream()
                .filter(c -> customerInfo.equals(c.cpf()) || customerInfo.equals(c.name()))
                .findFirst()
                .flatMap(c -> c.purchases().stream().max(Comparator.comparing(CustomerPurchase::getQuantity)))
                .map(CustomerPurchase::getCode);
    }

    public DetailedPurchase getBiggestYearPurchase(final int year) {
        final Optional<DetailedPurchase> detailedPurchaseOptional = listPurchasesFor(productService.filterByYear(year))
                .max(Comparator.comparing(DetailedPurchase::getTotal));

        if (detailedPurchaseOptional.isPresent()) {
            return detailedPurchaseOptional.get();
        }

        throw new ResourceNotFoundException("Compra não encontrada.");
    }

    private Stream<DetailedPurchase> listPurchasesFor(final List<Product> products) {
        return listCustomers().stream()
                .flatMap(customer -> customer.purchases()
                        .stream()
                        .map(purchase -> filterProductByCode(products, purchase.getCode())
                                .map(product -> {
                                    var detailedPurchase = new DetailedPurchase();
                                    detailedPurchase.setCustomerName(customer.name());
                                    detailedPurchase.setCustomerCpf(customer.cpf());
                                    detailedPurchase.setCode(purchase.getCode());
                                    detailedPurchase.setQuantity(purchase.getQuantity());
                                    detailedPurchase.setType(product.type());
                                    detailedPurchase.setPrice(product.price());
                                    detailedPurchase.setVintage(product.vintage());
                                    detailedPurchase.setPurchaseYear(product.purchaseYear());
                                    detailedPurchase.calculateTotal();
                                    return detailedPurchase;
                                })
                                .findFirst())
                        .filter(Optional::isPresent)
                        .map(Optional::get));
    }

    private Stream<Product> filterProductByCode(final List<Product> products, final String code) {
        return products.stream().filter(product -> product.code() == Integer.parseInt(code));
    }
}