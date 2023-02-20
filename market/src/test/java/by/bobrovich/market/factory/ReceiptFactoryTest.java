package by.bobrovich.market.factory;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.data.MarketBasket;
import by.bobrovich.market.entity.MarketDiscountCard;
import by.bobrovich.market.entity.MarketProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptFactoryTest {
    private Receipt marketReceipt;
    private Receipt marketDiscountReceipt;

    @BeforeEach
    void setUp() {
        ReceiptFactory receiptFactory = new ReceiptFactory();
        MarketProduct product = new MarketProduct(
                132,
                "description",
                BigDecimal.valueOf(12.123),
                15,
                true
        );
        MarketBasket marketBasket = new MarketBasket();
        marketBasket.addProduct(product, 99);
        marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);
    }

    @Test
    void checkCreateMarketReceiptContainsQty() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("99"));
    }
    @Test
    void checkCreateMarketReceiptContainsDescription() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("description"));
    }
    @Test
    void checkCreateMarketReceiptContainsPrice() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$12.12"));
    }
    @Test
    void checkCreateMarketReceiptContainsTaxableTot() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$996.15"));
    }
    @Test
    void checkCreateMarketReceiptContainsVat() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$204.03"));
    }
    @Test
    void checkCreateMarketReceiptContainsTotal() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$1200.18"));
    }

//////////////////////////////////////////////////////////////////////////
    @Test
    void checkCreateMarketDiscountReceiptContainsQty() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("99"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsDescription() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("description"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsPrice() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$12.12"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsTaxableTot() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$996.15"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsVat() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$204.03"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsDiscount() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("120.02"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsTotal() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("1080.16"));
    }
}