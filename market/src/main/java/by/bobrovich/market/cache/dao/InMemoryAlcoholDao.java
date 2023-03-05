package by.bobrovich.market.cache.dao;

import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.cache.entity.Alcohol;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryAlcoholDao implements AlcoholDao {
    private final Map<Long, Alcohol> alcohols;

    public InMemoryAlcoholDao() {
        this.alcohols = new HashMap<>();
        init();
    }

    @Override
    @Cache
    public Alcohol get(Long id) {
        return alcohols.get(id);
    }

    @Override
    @Cache
    public void save(Alcohol alcohol) {
        alcohols.put(alcohol.getId(), alcohol);
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
                Alcohol.builder().id(1L).name("Whisky").country("USA").vol(40.0).price(BigDecimal.valueOf(123.4)).build(),
                Alcohol.builder().id(2L).name("Vino").country("Moldova").vol(20).price(BigDecimal.valueOf(55.3)).build(),
                Alcohol.builder().id(3L).name("Vodka").country("Russia").vol(40.0).price(BigDecimal.valueOf(1.4)).build(),
                Alcohol.builder().id(4L).name("Night").country("Russia").vol(35).price(BigDecimal.valueOf(14.2)).build(),
                Alcohol.builder().id(5L).name("Absent").country("Belarus").vol(65).price(BigDecimal.valueOf(99.9)).build(),
                Alcohol.builder().id(6L).name("Moonshine").country("Ukraine").vol(40.0).price(BigDecimal.valueOf(999.9)).build(),
                Alcohol.builder().id(7L).name("Tekla").country("Mexico").vol(41).price(BigDecimal.valueOf(76)).build(),
                Alcohol.builder().id(8L).name("Shamanism").country("Belarus").vol(13).price(BigDecimal.valueOf(16)).build(),
                Alcohol.builder().id(9L).name("Every Year").country("Belarus").vol(15).price(BigDecimal.valueOf(14)).build(),
                Alcohol.builder().id(10L).name("ChaCha").country("Georgia").vol(99).price(BigDecimal.valueOf(0.5)).build(),
                Alcohol.builder().id(11L).name("Rom").country("Portugal").vol(40.0).price(BigDecimal.valueOf(52)).build()
        ).collect(Collectors.toMap(Alcohol::getId, x -> x)));
    }
}
