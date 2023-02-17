package by.bobrovich.market.validation;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.data.MarketOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    private OrderValidator orderValidator;
    @Mock
    private ProductDao productDao;
    @Mock
    private DiscountCardDao discountCardDao;
    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator(productDao, discountCardDao);
    }

    @Test
    void checkIsValidNull() {
        assertFalse(orderValidator.isValid(null, null));
    }

    @Test
    void checkIsValidEmpty() {
        Order order = MarketOrder.builder().build();
        assertFalse(orderValidator.isValid(order, null));
    }

    @Test
    void checkIsValidPositiveItemId() {
        Order order = MarketOrder.builder()
                .addItemsId(List.of(12))
                .build();
        assertTrue(orderValidator.isValid(order, null));
    }
    @Test
    void checkIsValidNegativeItemId() {
        Order order = MarketOrder.builder()
                .addItemsId(List.of(-19827414))
                .build();
        assertFalse(orderValidator.isValid(order, null));
    }

    @Test
    void checkIsValidCardWithoutItemId() {
        Order order = MarketOrder.builder()
                .addDiscountCard(12)
                .build();
        assertFalse(orderValidator.isValid(order, null));
    }
    @Test
    void checkIsValidPositiveCard() {
        Order order = MarketOrder.builder()
                .addItemsId(List.of(19))
                .addDiscountCard(12)
                .build();
        assertTrue(orderValidator.isValid(order, null));
    }
    @Test
    void checkIsValidNegativeCard() {
        Order order = MarketOrder.builder()
                .addItemsId(List.of(19))
                .addDiscountCard(-12)
                .build();
        assertFalse(orderValidator.isValid(order, null));
    }
}