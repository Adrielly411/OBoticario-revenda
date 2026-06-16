package br.com.oboticariorevenda.oboticario_revenda.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.oboticariorevenda.oboticario_revenda.dto.ProductCreateRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.repository.ProductRepository;

public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    String imagekit_private;

    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productService = new ProductService(productRepository, imagekit_private);
    }

    @Test
    void shouldSaveProductSuccessfully() {
        ProductCreateRequestDto product = ProductCreateRequestDto.builder()
            .name("Product")
            .price(20.50)
            .discountedPrice(10.50)
            .quantity(5)
            .build();

        assertNotNull(product);
        assertTrue(product.getName().equals("Product"));
        assertTrue(product.getPrice() == 20.50);
        assertTrue(product.getDiscountedPrice() == 10.50);
        assertTrue(product.getQuantity() == 5);
        assertDoesNotThrow(()->productService.saveProduct(product));
    }
}
