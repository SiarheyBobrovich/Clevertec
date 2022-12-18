package by.bobrovich.market.service;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.data.MarketOrderEntry;
import by.bobrovich.market.exceptions.ProductQuantityIsNotAvailable;
import by.bobrovich.market.factory.ReceiptFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class ThreadsMarketServiceTest {
    private static final MarketService service;
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger failedCount = new AtomicInteger(0);


    static {
        service = new MarketService(
                null,
                new InMemoryProductDao(),
                new InMemoryDiscountCardDao(),
                new ReceiptFactory()
        );
    }

    @Test
    void getBillWithoutCardThreads() {
        int threadCount = 600;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(getRunnable(), "Thread: " + i));
        }

        threads.forEach(Thread::start);
        long threadAlive;

        do {
            threads = threads.stream().filter(Thread::isAlive).collect(Collectors.toList());
            threadAlive = threads.size();
        }while (threadAlive != 0);

        Assertions.assertEquals(500, count.get());
        Assertions.assertEquals(threadCount - 500, failedCount.get());
    }

    private Runnable getRunnable() {
        return () -> {
            try {
                service.getReceipt(getThreadOrder());

            }catch (ProductQuantityIsNotAvailable e) {
                failedCount.incrementAndGet();
                return;
            }

            count.incrementAndGet();
        };
    }

    private Order getThreadOrder() {
        return MarketOrder.builder()
                .addOrderEntry(new MarketOrderEntry(500, 1))
                .build();
    }
}