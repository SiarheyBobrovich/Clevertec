package by.bobrovich.market.cache.dao.api;

import by.bobrovich.market.cache.entity.Alcohol;

public interface AlcoholDao {
    Alcohol get(Long id);
    Long save(Alcohol alcohol);
    void update(Alcohol alcohol);
    void delete(Long id);
}
