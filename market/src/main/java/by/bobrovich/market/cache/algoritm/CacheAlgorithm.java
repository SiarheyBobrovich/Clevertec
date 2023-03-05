package by.bobrovich.market.cache.algoritm;

public interface CacheAlgorithm<ID, T> {
    void put(ID id, T o);
    T get(ID id);
    void delete(ID id);
}
