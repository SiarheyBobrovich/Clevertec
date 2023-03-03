package by.bobrovich.market.factory;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.data.MarketBasket;
import by.bobrovich.market.entity.MarketDiscountCard;
import by.bobrovich.market.util.MarketBasketBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptFactoryTest {
    private ReceiptFactory receiptFactory;

    @BeforeEach
    void setUp() {
        receiptFactory = new ReceiptFactory();
    }

    @Test
    void checkCreateMarketReceiptContainsQty() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        Receipt marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("99"));
    }
    @Test
    void checkCreateMarketReceiptContainsDescription() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        Receipt marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("description"));
    }
    @Test
    void checkCreateMarketReceiptContainsPrice() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        Receipt marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$12.12"));
    }
    @Test
    void checkCreateMarketReceiptContainsTaxableTot() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        Receipt marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$996.15"));
    }
    @Test
    void checkCreateMarketReceiptContainsVat() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        Receipt marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$204.03"));
    }
    @Test
    void checkCreateMarketReceiptContainsTotal() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        Receipt marketReceipt = receiptFactory.create(marketBasket, null, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$1200.18"));
    }

    @Test
    void checkCreateMarketDiscountReceiptContainsQty() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("99"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsDescription() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("description"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsPrice() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$12.12"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsTaxableTot() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$996.15"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsVat() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("$204.03"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsDiscount() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("120.02"));
    }
    @Test
    void checkCreateMarketDiscountReceiptContainsTotal() {
        MarketBasket marketBasket = MarketBasketBuilder.builder().build();
        MarketDiscountCard discountCard = new MarketDiscountCard(10, (byte) 10);
        Receipt marketDiscountReceipt = receiptFactory.create(marketBasket, discountCard, 1234);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marketDiscountReceipt.print(new PrintStream(outputStream));
        String printedReceipt = outputStream.toString();

        assertTrue(printedReceipt.contains("1080.16"));
    }
}
