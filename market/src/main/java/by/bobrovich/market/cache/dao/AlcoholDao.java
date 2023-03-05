package by.bobrovich.market.cache.dao;

import by.bobrovich.market.cache.entity.Alcohol;

public interface AlcoholDao {
    Alcohol get(Long id);
    void save(Alcohol alcohol);
    void update(Alcohol alcohol);
    void delete(Long id);
}
