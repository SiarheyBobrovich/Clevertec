package by.bobrovich.market.service;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.data.MarketOrderEntry;
import by.bobrovich.market.data.receipt.MarketReceipt;
import by.bobrovich.market.entity.MarketDiscountCard;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.factory.ReceiptFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

class MarketServiceTest {
    private static final MarketService service;
    private static final InMemoryProductDao productDao;
    private static final InMemoryDiscountCardDao discountDao;

    static {
        productDao = Mockito.mock(InMemoryProductDao.class);
        discountDao = Mockito.mock(InMemoryDiscountCardDao.class);
        service = new MarketService(
                null,
                productDao,
                discountDao,
                new ReceiptFactory()
        );
    }

    @Test
    void getBillWithoutCard() {
        initProductDao();

        MarketReceipt receipt = (MarketReceipt)service.getReceipt(getOrder());

        String result = getRecieptAsString(receipt);

        Assertions.assertTrue(result.contains("$1327.87"));
        Assertions.assertTrue(result.contains("$271.97"));
        Assertions.assertTrue(result.contains("$1599.84"));
    }

    @Test
    void getBillWithCard() {
        initProductDao();
        Mockito.when(discountDao.getById(1234)).thenReturn(Optional.of(new MarketDiscountCard(1234, (byte)10)));
        Receipt receipt = service.getReceipt(getOrderWithCard());

        String result = getRecieptAsString(receipt);

        Assertions.assertTrue(result.contains("$1327.87"));
        Assertions.assertTrue(result.contains("$271.97"));
        Assertions.assertTrue(result.contains("$48.88"));
        Assertions.assertTrue(result.contains("$1550.96"));
    }

    private String getRecieptAsString(Receipt receipt) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        receipt.print(new PrintStream(buffer));
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private void initProductDao(){
        Mockito.when(productDao.exists(Mockito.anyInt())).thenReturn(true);
        Mockito.when(productDao.isExistsAndQuantityAvailable(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        for (int i = 0; i < 3; i++) {
            Mockito.doReturn(Optional.ofNullable(getProducts().get(i))).when(productDao).getById(i + 1);
        }
    }

    private Order getOrder() {
        return MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(1, 1))
                .addOrderEntry(new MarketOrderEntry(2, 22))
                .addOrderEntry(new MarketOrderEntry(3, 33))
                .build();
    }

    private List<MarketProduct> getProducts() {
        return List.of(
                new MarketProduct(1, "first", BigDecimal.valueOf(11.11), 10, false),
                new MarketProduct(2, "second", BigDecimal.valueOf(22.22), 23, true),
                new MarketProduct(3, "third", BigDecimal.valueOf(33.33), 34, false)
        );
    }

    private Order getOrderWithCard() {
        return MarketOrder.builder()
                .addDiscountCard(1234)
                .addOrderEntry(new MarketOrderEntry(1, 1))
                .addOrderEntry(new MarketOrderEntry(2, 22))
                .addOrderEntry(new MarketOrderEntry(3, 33))
                .build();
    }
}