package by.bobrovich.market.service;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.data.MarketOrderEntry;
import by.bobrovich.market.factory.ReceiptFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author Babrovich Siarhey on 17.03.2023
 */
@ExtendWith(MockitoExtension.class)
public class PdfServiceTest {
    @InjectMocks
    private PdfServiceImpl service;
    @Spy
    private InMemoryProductDao productDao;
    @Spy
    private InMemoryDiscountCardDao discountDao;
    @Spy
    private ReceiptFactory receiptFactory;

    @Test
    void checkPathToFile() {
        Order order = MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(1, 5))
                .addOrderEntry(new MarketOrderEntry(2, 5))
                .addOrderEntry(new MarketOrderEntry(3, 5))
                .addOrderEntry(new MarketOrderEntry(4, 5))
                .addOrderEntry(new MarketOrderEntry(5, 5))
                .addOrderEntry(new MarketOrderEntry(6, 5))
                .addOrderEntry(new MarketOrderEntry(7, 5))
                .addOrderEntry(new MarketOrderEntry(8, 5))
                .addOrderEntry(new MarketOrderEntry(9, 5))
                .addOrderEntry(new MarketOrderEntry(10, 5))
                .addOrderEntry(new MarketOrderEntry(11, 5))
                .addOrderEntry(new MarketOrderEntry(12, 5))
                .addOrderEntry(new MarketOrderEntry(13, 2))
                .addOrderEntry(new MarketOrderEntry(14, 2))
                .addOrderEntry(new MarketOrderEntry(15, 2))
                .addOrderEntry(new MarketOrderEntry(16, 2))
                .addOrderEntry(new MarketOrderEntry(17, 2))
                .addOrderEntry(new MarketOrderEntry(18, 2))
                .addOrderEntry(new MarketOrderEntry(19, 2))
                .addOrderEntry(new MarketOrderEntry(20, 2))
                .addOrderEntry(new MarketOrderEntry(21, 2))
                .addOrderEntry(new MarketOrderEntry(22, 2))
                .addOrderEntry(new MarketOrderEntry(23, 2))
                .addOrderEntry(new MarketOrderEntry(24, 2))
                .addOrderEntry(new MarketOrderEntry(25, 2))
                .addOrderEntry(new MarketOrderEntry(26, 2))
                .addOrderEntry(new MarketOrderEntry(27, 2))
                .addOrderEntry(new MarketOrderEntry(28, 2))
                .build();

        Path path = service.getPath(order);

        assertDoesNotThrow(() -> service.getPath(order));
    }

    @Test
    void checkPathToFileDiscountReceipt() {
        Order order = MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(1, 3))
                .addOrderEntry(new MarketOrderEntry(3, 2))
                .addOrderEntry(new MarketOrderEntry(5, 1))
                .addDiscountCard(1234)
                .build();

        assertDoesNotThrow(() -> service.getPath(order));
    }
}
