package com.vintage.domain.service;

import com.vintage.domain.model.*;
import com.vintage.port.out.CustomerApiOutputPort;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerApiOutputPort customerApiOutputPort;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(customerService, "loyalCustomerLimit", 3);
    }

    @Test
    void whenCustomersAreAvailable_thenTheyAreReturned() {
        // Arrange
        final List<Customer> expectedCustomers = Instancio.ofList(Customer.class).size(2).create();
        when(customerApiOutputPort.listCustomers()).thenReturn(expectedCustomers);

        // Act
        final List<Customer> actualCustomers = customerService.listCustomers();

        // Assert
        assertThat(actualCustomers).isEqualTo(expectedCustomers);
        verify(customerApiOutputPort).listCustomers();
    }

    @Test
    void whenProductsAreAvailable_andCustomersAreAvailable_thenLoyalCustomersAreShowed() {
        // Arrange
        final var purchase1 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "1")
                .set(field(CustomerPurchase::getQuantity), 2)
                .create();
        final var purchase2 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "2")
                .set(field(CustomerPurchase::getQuantity), 2)
                .create();
        final var purchase3 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "3")
                .set(field(CustomerPurchase::getQuantity), 2)
                .create();
        final var purchase4 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "4")
                .set(field(CustomerPurchase::getQuantity), 1)
                .create();

        final var customer1 = Instancio.of(Customer.class)
                .set(field(Customer::cpf), "03763001590")
                .set(field(Customer::purchases), List.of(purchase1))
                .create();
        final var customer2 = Instancio.of(Customer.class)
                .set(field(Customer::cpf), "88901767767")
                .set(field(Customer::purchases), List.of(purchase2))
                .create();
        final var customer3 = Instancio.of(Customer.class)
                .set(field(Customer::cpf), "1051252612")
                .set(field(Customer::purchases), List.of(purchase3))
                .create();
        final var customer4 = Instancio.of(Customer.class)
                .set(field(Customer::cpf), "20634031392")
                .set(field(Customer::purchases), List.of(purchase4))
                .create();

        final var product1 = Instancio.of(Product.class)
                .set(field(Product::code), 1)
                .set(field(Product::price), BigDecimal.valueOf(40))
                .create();
        final var product2 = Instancio.of(Product.class)
                .set(field(Product::code), 2)
                .set(field(Product::price), BigDecimal.valueOf(40))
                .create();
        final var product3 = Instancio.of(Product.class)
                .set(field(Product::code), 3)
                .set(field(Product::price), BigDecimal.valueOf(40))
                .create();
        final var product4 = Instancio.of(Product.class)
                .set(field(Product::code), 4)
                .set(field(Product::price), BigDecimal.valueOf(50))
                .create();

        final List<Customer> allCustomers = List.of(customer1, customer2, customer3, customer4);
        when(customerApiOutputPort.listCustomers()).thenReturn(allCustomers);

        final List<Product> allProducts = List.of(product1, product2, product3, product4);
        when(productService.list()).thenReturn(allProducts);

        final List<LoyalCustomer> expectedLoyalCustomers = Stream.of(customer1, customer2, customer3)
                .map(c -> Instancio.of(LoyalCustomer.class)
                        .set(field(LoyalCustomer::cpf), c.cpf())
                        .set(field(LoyalCustomer::name), c.name())
                        .set(field(LoyalCustomer::totalPurchases), BigDecimal.valueOf(80))
                        .create()
                ).toList();

        // Act
        final List<LoyalCustomer> actualLoyalCustomers = customerService.listMostLoyalCustomers();

        // Assert
        assertThat(actualLoyalCustomers).isEqualTo(expectedLoyalCustomers);
        verify(customerApiOutputPort).listCustomers();
        verify(productService).list();
    }

    @Test
    void whenPurchasesAreAvailable_thenTheyAreReturned() {
        // Arrange
        final var product1 = Instancio.of(Product.class)
                .set(field(Product::code), 1)
                .set(field(Product::price), BigDecimal.valueOf(10))
                .create();
        final var product2 = Instancio.of(Product.class)
                .set(field(Product::code), 2)
                .set(field(Product::price), BigDecimal.valueOf(20))
                .create();
        final var product3 = Instancio.of(Product.class)
                .set(field(Product::code), 3)
                .set(field(Product::price), BigDecimal.valueOf(30))
                .create();
        final var product4 = Instancio.of(Product.class)
                .set(field(Product::code), 4)
                .set(field(Product::price), BigDecimal.valueOf(40))
                .create();
        final var product5 = Instancio.of(Product.class)
                .set(field(Product::code), 5)
                .set(field(Product::price), BigDecimal.valueOf(50))
                .create();

        final List<Product> allProducts = List.of(product1, product2, product3, product4, product5);
        when(productService.list()).thenReturn(allProducts);

        final var purchase1 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "1")
                .set(field(CustomerPurchase::getQuantity), 1)
                .create();
        final var purchase2 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "2")
                .set(field(CustomerPurchase::getQuantity), 1)
                .create();
        final var purchase3 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "3")
                .set(field(CustomerPurchase::getQuantity), 1)
                .create();
        final var purchase4 = Instancio.of(CustomerPurchase.class)
                .set(field(CustomerPurchase::getCode), "4")
                .set(field(CustomerPurchase::getQuantity), 1)
                .create();

        final var customer1 = Instancio.of(Customer.class)
                .set(field(Customer::purchases), List.of(purchase1, purchase2))
                .create();
        final var customer2 = Instancio.of(Customer.class)
                .set(field(Customer::purchases), List.of(purchase3, purchase4))
                .create();
        final List<Customer> allCustomers = List.of(customer1, customer2);
        when(customerApiOutputPort.listCustomers()).thenReturn(allCustomers);

        final var detailedPurchase1 = new DetailedPurchase();
        detailedPurchase1.setPurchaseYear(product1.purchaseYear());
        detailedPurchase1.setCode(String.valueOf(product1.code()));
        detailedPurchase1.setPrice(product1.price());
        detailedPurchase1.setType(product1.type());
        detailedPurchase1.setVintage(product1.vintage());
        detailedPurchase1.setQuantity(purchase1.getQuantity());
        detailedPurchase1.calculateTotal();
        detailedPurchase1.setCustomerCpf(customer1.cpf());
        detailedPurchase1.setCustomerName(customer1.name());

        final var detailedPurchase2 = new DetailedPurchase();
        detailedPurchase2.setPurchaseYear(product2.purchaseYear());
        detailedPurchase2.setCode(String.valueOf(product2.code()));
        detailedPurchase2.setPrice(product2.price());
        detailedPurchase2.setType(product2.type());
        detailedPurchase2.setVintage(product2.vintage());
        detailedPurchase2.setQuantity(purchase2.getQuantity());
        detailedPurchase2.calculateTotal();
        detailedPurchase2.setCustomerCpf(customer1.cpf());
        detailedPurchase2.setCustomerName(customer1.name());

        final var detailedPurchase3 = new DetailedPurchase();
        detailedPurchase3.setPurchaseYear(product3.purchaseYear());
        detailedPurchase3.setCode(String.valueOf(product3.code()));
        detailedPurchase3.setPrice(product3.price());
        detailedPurchase3.setType(product3.type());
        detailedPurchase3.setVintage(product3.vintage());
        detailedPurchase3.setQuantity(purchase3.getQuantity());
        detailedPurchase3.calculateTotal();
        detailedPurchase3.setCustomerCpf(customer2.cpf());
        detailedPurchase3.setCustomerName(customer2.name());

        final var detailedPurchase4 = new DetailedPurchase();
        detailedPurchase4.setPurchaseYear(product4.purchaseYear());
        detailedPurchase4.setCode(String.valueOf(product4.code()));
        detailedPurchase4.setPrice(product4.price());
        detailedPurchase4.setType(product4.type());
        detailedPurchase4.setVintage(product4.vintage());
        detailedPurchase4.setQuantity(purchase4.getQuantity());
        detailedPurchase4.calculateTotal();
        detailedPurchase4.setCustomerCpf(customer2.cpf());
        detailedPurchase4.setCustomerName(customer2.name());

        final List<DetailedPurchase> expectedPurchases = List.of(
                detailedPurchase1,
                detailedPurchase2,
                detailedPurchase3,
                detailedPurchase4
        );

        // Act
        final List<DetailedPurchase> actualPurchases = customerService.listPurchases();

        // Assert
        assertThat(actualPurchases).isEqualTo(expectedPurchases);
        verify(productService).list();
        verify(customerApiOutputPort).listCustomers();
    }
}