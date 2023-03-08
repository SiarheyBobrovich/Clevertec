package by.bobrovich.market.service;

import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.exceptions.ProductQuantityIsNotAvailable;
import by.bobrovich.market.factory.ReceiptFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ThreadsReceiptServiceImplTest {
    private static ReceiptServiceImpl service;
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger failedCount = new AtomicInteger(0);

    @BeforeAll
    static void init() {
        service = new ReceiptServiceImpl(
                new InMemoryProductDao(),
                new InMemoryDiscountCardDao(),
                new ReceiptFactory()
        );
    }

    @Test
    void getBillWithoutCardThreads() {
        int threadCount = 600;
        Set<Thread> threads = Stream.generate(() -> new Thread(getRunnable()))
                .limit(threadCount)
                .collect(Collectors.toSet());
        threads.forEach(Thread::start);
        while (threads.stream().anyMatch(Thread::isAlive)){}

        Assertions.assertEquals(500, count.get());
        Assertions.assertEquals(threadCount - 500, failedCount.get());
    }

    private Runnable getRunnable() {
        return () -> {
            try {
                service.getReceipt(MarketOrder.builder()
                        .addItemsId(List.of(500))
                        .build());

            }catch (ProductQuantityIsNotAvailable e) {
                failedCount.incrementAndGet();
                return;
            }

            count.incrementAndGet();
        };
    }
}
