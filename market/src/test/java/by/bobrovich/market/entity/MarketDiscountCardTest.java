package by.bobrovich.market.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketDiscountCardTest {

    @Test
    void getId() {
        MarketDiscountCard marketDiscountCard = new MarketDiscountCard();
        marketDiscountCard.setId(1111);
        assertEquals(1111, marketDiscountCard.getId());
    }

    @Test
    void getDiscount() {
        MarketDiscountCard marketDiscountCard = new MarketDiscountCard(1111, (byte) 10);
        assertEquals(10, marketDiscountCard.getDiscount());
    }

    @Test
    void setId() {
        MarketDiscountCard marketDiscountCard = new MarketDiscountCard(1111, (byte) 10);
        marketDiscountCard.setId(1234);
        assertEquals(1234, marketDiscountCard.getId());
        assertThrows(IllegalArgumentException.class, () -> marketDiscountCard.setId(null));
    }

    @Test
    void setDiscount() {
        MarketDiscountCard marketDiscountCard = new MarketDiscountCard(1111, (byte) 10);
        marketDiscountCard.setDiscount((byte)50);
        assertEquals(50, marketDiscountCard.getDiscount());
        assertThrows(IllegalArgumentException.class, () -> marketDiscountCard.setDiscount(null));
        assertThrows(IllegalArgumentException.class, () -> marketDiscountCard.setDiscount((byte)-1));
        assertThrows(IllegalArgumentException.class, () -> marketDiscountCard.setDiscount((byte)110));
    }
}