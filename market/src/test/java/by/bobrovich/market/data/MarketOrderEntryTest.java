package by.bobrovich.market.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketOrderEntryTest {



    @Test
    void testEquals() {
        MarketOrderEntry marketOrderEntry = new MarketOrderEntry(1, 1);
        MarketOrderEntry marketOrderEntry1 = new MarketOrderEntry(1, 1);
        MarketOrderEntry marketOrderEntry2 = new MarketOrderEntry(1, 3);

        assertEquals(marketOrderEntry, marketOrderEntry1);
        assertFalse(marketOrderEntry.equals(marketOrderEntry2));
    }

    @Test
    void testHashCode() {
        MarketOrderEntry marketOrderEntry = new MarketOrderEntry(1, 1);
        assertTrue(marketOrderEntry.hashCode() != 0);
    }

    @Test
    void testToString() {
        MarketOrderEntry marketOrderEntry = new MarketOrderEntry(1, 1);
        assertEquals("MarketOrderEntry{id=1, quantity=1}", marketOrderEntry.toString());
    }
}