package by.bobrovich.market.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MarketProductTest {

    @Test
    void setId() {
        MarketProduct marketProduct = new MarketProduct();
        marketProduct.setId(1);

        assertEquals(1, marketProduct.getId());
    }

    @Test
    void setDescription() {
        MarketProduct marketProduct = new MarketProduct();
        marketProduct.setDescription("test");

        assertEquals("test", marketProduct.getDescription());
    }

    @Test
    void setPrice() {
        MarketProduct marketProduct = new MarketProduct();
        marketProduct.setPrice(new BigDecimal(12));

        assertEquals(new BigDecimal(12), marketProduct.getPrice());
    }

    @Test
    void setQuantity() {
        MarketProduct marketProduct = new MarketProduct();
        marketProduct.setQuantity(1);

        assertEquals(1, marketProduct.getQuantity());
        assertThrows(IllegalArgumentException.class, () -> marketProduct.setQuantity(-1));
    }

    @Test
    void setDiscount() {
        MarketProduct marketProduct = new MarketProduct();
        marketProduct.setDiscount(true);

        assertTrue(marketProduct.isDiscount());
    }
}