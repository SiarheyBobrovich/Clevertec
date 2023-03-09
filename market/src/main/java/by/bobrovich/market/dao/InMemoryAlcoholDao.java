package by.bobrovich.market.dao;

import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.dao.api.AlcoholDao;
import by.bobrovich.market.entity.Alcohol;
import by.bobrovich.market.exceptions.AlcoholNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConditionalOnProperty(
        name = "spring.alcohol.database",
        havingValue = "memory"
)
public class InMemoryAlcoholDao implements AlcoholDao {
    private final Map<Long, Alcohol> alcohols;
    private final AtomicLong id;

    public InMemoryAlcoholDao() {
        this.alcohols = new HashMap<>();
        this.id = new AtomicLong(1);
        init();
    }

    @Override
    @Cache
    public Alcohol get(Long id) {
        Alcohol alcohol = alcohols.get(id);
        if (alcohol == null) throw new AlcoholNotFoundException("Not found");
        return alcohol;
    }

    @Override
    public List<Alcohol> getAll() {
        return alcohols.values().stream().toList();
    }

    @Override
    @Cache
    public Long save(Alcohol alcohol) {
        long alcoholId = id.incrementAndGet();
        alcohol.setId(alcoholId);
        alcohols.put(alcohol.getId(), alcohol);
        return alcoholId;
    }

    @Override
    @Cache
    public void update(Alcohol alcohol) {
        alcohols.put(alcohol.getId(), alcohol);
    }

    @Override
    @Cache
    public void delete(Long id) {
        alcohols.remove(id);
    }

    private void init() {
        alcohols.putAll(Stream.of(
                Alcohol.builder().id(1L).name("Whisky").country("USA").vol(40.0).price(BigDecimal.valueOf(123.4)).quantity(10).build(),
                Alcohol.builder().id(2L).name("Vino").country("Moldova").vol(20).price(BigDecimal.valueOf(55.3)).quantity(10).build(),
                Alcohol.builder().id(3L).name("Vodka").country("Russia").vol(40.0).price(BigDecimal.valueOf(1.4)).quantity(10).build(),
                Alcohol.builder().id(4L).name("Night").country("Russia").vol(35).price(BigDecimal.valueOf(14.2)).quantity(10).build(),
                Alcohol.builder().id(5L).name("Absent").country("Belarus").vol(65).price(BigDecimal.valueOf(99.9)).quantity(10).build(),
                Alcohol.builder().id(6L).name("Moonshine").country("Ukraine").vol(40.0).price(BigDecimal.valueOf(999.9)).quantity(10).build(),
                Alcohol.builder().id(7L).name("Tekla").country("Mexico").vol(41).price(BigDecimal.valueOf(76)).quantity(10).build(),
                Alcohol.builder().id(8L).name("Shamanism").country("Belarus").vol(13).price(BigDecimal.valueOf(16)).quantity(10).build(),
                Alcohol.builder().id(9L).name("Every Year").country("Belarus").vol(15).price(BigDecimal.valueOf(14)).quantity(10).build(),
                Alcohol.builder().id(10L).name("ChaCha").country("Georgia").vol(99).price(BigDecimal.valueOf(0.5)).quantity(10).build(),
                Alcohol.builder().id(11L).name("Rom").country("Portugal").vol(40.0).price(BigDecimal.valueOf(52)).quantity(10).build()
        ).collect(Collectors.toMap(Alcohol::getId, x -> x)));
        id.set(alcohols.size());
    }
}
