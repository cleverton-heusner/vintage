package com.vintage.domain.service;

import com.vintage.domain.model.Customer;
import com.vintage.domain.model.CustomerPurchase;
import com.vintage.domain.model.Product;
import com.vintage.domain.service.CustomerService;
import com.vintage.domain.service.ProductService;
import com.vintage.port.out.ProductApiOutputPort;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductApiOutputPort productApiOutputPort;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ProductService productService;

    @Test
    void whenProductsAreAvailable_thenTheyAreReturned() {
        // Arrange
        final List<Product> expectedProducts = Instancio.ofList(Product.class).size(2).create();
        when(productApiOutputPort.list()).thenReturn(expectedProducts);

        // Act
        final List<Product> actualProducts = productService.list();

        // Assert
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productApiOutputPort).list();
    }

    @Test
    void whenProductsAreAvailable_thenTheyAreFilteredByYear() {
        // Arrange
        final List<Product> allProducts = Instancio.ofList(Product.class).size(2).create();
        final Product productToFilter = allProducts.get(0);
        final List<Product> expectedProducts = List.of(productToFilter);

        when(productApiOutputPort.list()).thenReturn(allProducts);

        // Act
        final List<Product> actualProducts = productService.filterByYear(productToFilter.purchaseYear());

        // Assert
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productApiOutputPort).list();
    }

    @Test
    void whenProductsIsAvailable_andMostPurchasedByCustomer_thenItsRecommended() {
        // Arrange
        final Product expectedRecommendProduct = Instancio.of(Product.class)
                .set(field(Product::code), 1)
                .create();
        final Product product2 = Instancio.of(Product.class)
                .set(field(Product::code), 2)
                .create();
        final Product product3 = Instancio.of(Product.class)
                .set(field(Product::code), 3)
                .create();
        final Product product4 = Instancio.of(Product.class)
                .set(field(Product::code), 4)
                .create();

        final CustomerPurchase customerPurchase1 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "1")
                .set(field(CustomerPurchase::getQuantity), 10)
                .create();
        final CustomerPurchase customerPurchase2 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "2")
                .set(field(CustomerPurchase::getQuantity), 9)
                .create();

        final Customer customer1 = Instancio.of(Customer.class)
                .set(field(Customer::cpf), "03763001590")
                .set(field(Customer::purchases), List.of(customerPurchase1, customerPurchase2))
                .create();

        final List<Product> allProducts = List.of(expectedRecommendProduct, product2, product3, product4);
        when(productApiOutputPort.list()).thenReturn(allProducts);

        final Optional<String> biggestPurchaseCodeOptional = Optional.of("1");
        when(customerService.findBiggestPurchaseCodeFrom(customer1.cpf())).thenReturn(biggestPurchaseCodeOptional);

        // Act
        final Product actualRecommendProduct = productService.recommend(customer1.cpf());

        // Assert
        assertThat(actualRecommendProduct).isEqualTo(expectedRecommendProduct);
        verify(productApiOutputPort).list();
        verify(customerService).findBiggestPurchaseCodeFrom(anyString());
    }
}