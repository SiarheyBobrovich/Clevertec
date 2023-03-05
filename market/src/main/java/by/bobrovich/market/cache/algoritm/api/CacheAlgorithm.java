package by.bobrovich.market.cache.algoritm.api;

import java.util.Optional;

public interface CacheAlgorithm<ID, T> {
    void put(ID id, T o);
    Optional<T> get(ID id);
    void delete(ID id);
}
