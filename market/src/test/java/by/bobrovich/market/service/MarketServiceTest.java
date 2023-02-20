package by.bobrovich.market.service;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.data.MarketBasket;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.data.MarketOrderEntry;
import by.bobrovich.market.data.receipt.MarketDiscountReceipt;
import by.bobrovich.market.data.receipt.MarketReceipt;
import by.bobrovich.market.decorator.BasketDiscountDecorator;
import by.bobrovich.market.entity.MarketDiscountCard;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.exceptions.ProductQuantityIsNotAvailable;
import by.bobrovich.market.factory.ReceiptFactory;
import by.bobrovich.market.util.DiscountCardBuilder;
import by.bobrovich.market.util.MarketProductBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketServiceTest {

    private MarketService service;
    @Mock
    private InMemoryProductDao productDao;
    @Mock
    private InMemoryDiscountCardDao discountDao;
    @Mock
    private ReceiptFactory receiptFactory;

    @BeforeEach
    void setUp(){
        service = new MarketService(
                productDao,
                discountDao,
                receiptFactory
        );
    }

    @Test
    void checkGetReceiptMarketReceipt() {
        MarketProduct product = MarketProductBuilder.builder().build();
        Order order = MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(product.getId(), product.getQuantity()))
                .build();
        MarketReceipt expectedReceipt = new MarketReceipt(LocalDateTime.now(), new MarketBasket(), 1234);

        doReturn(true).when(productDao).exists(product.getId());
        doReturn(true).when(productDao)
                .isExistsAndQuantityAvailable(product.getId(), product.getQuantity());
        doReturn(Optional.of(product)).when(productDao).getById(product.getId());
        doReturn(expectedReceipt).when(receiptFactory).create(any(), any(), anyInt());

        Receipt receipt = service.getReceipt(order);

        assertEquals(expectedReceipt, receipt);
    }

    @Test
    void checkGetReceiptMarketDiscountReceipt() {
        BasketDiscountDecorator basketDiscountDecorator = new BasketDiscountDecorator(new MarketBasket());
        MarketDiscountCard card = DiscountCardBuilder.builder().build();
        MarketProduct product = MarketProductBuilder.builder().build();
        Order order = MarketOrder.builder()
                .addDiscountCard(card.getId())
                .addOrderEntry(new MarketOrderEntry(product.getId(), product.getQuantity()))
                .build();
        MarketDiscountReceipt expectedReceipt = new MarketDiscountReceipt(
                LocalDateTime.now(), basketDiscountDecorator, 4321, card);

        doReturn(true).when(productDao).exists(product.getId());
        doReturn(true).when(productDao)
                .isExistsAndQuantityAvailable(product.getId(), product.getQuantity());
        doReturn(Optional.of(product)).when(productDao).getById(product.getId());
        doReturn(Optional.of(card)).when(discountDao).getById(card.getId());
        doReturn(expectedReceipt).when(receiptFactory).create(any(), any(), anyInt());

        Receipt receipt = service.getReceipt(order);

        assertEquals(expectedReceipt, receipt);
    }

    @Test
    void checkGetReceiptProductNotExist() {
        int productId = 1;
        int productQty = 1;
        Order order = MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(productId, productQty))
                .build();

        when(productDao.exists(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> service.getReceipt(order));
    }

    @Test
    void checkGetReceiptDiscountCardNotExist() {
        int productId = 1;
        int productQty = 1;
        int discountCardNumber = 1234;
        Order order = MarketOrder.builder()
                .addDiscountCard(discountCardNumber)
                .addOrderEntry(new MarketOrderEntry(productId, productQty))
                .build();
        when(discountDao.getById(discountCardNumber)).thenReturn(Optional.empty());

        assertThrows(DiscountCardNotFoundException.class, () -> service.getReceipt(order));
    }
    @Test
    void checkGetReceiptProductQuantityIsNotAvailable() {
        int productId = 1;
        int productQty = 1;
        Order order = MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(productId, productQty))
                .build();

        when(productDao.exists(productId)).thenReturn(true);
        when(productDao.isExistsAndQuantityAvailable(productId, productQty)).thenReturn(false);

        assertThrows(ProductQuantityIsNotAvailable.class, () -> service.getReceipt(order));
    }
}