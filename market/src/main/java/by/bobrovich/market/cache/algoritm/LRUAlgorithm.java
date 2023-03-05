package by.bobrovich.market.cache.algoritm;

import java.util.Map;
import java.util.LinkedHashMap;

public class LRUAlgorithm<ID, T> implements CacheAlgorithm<ID, T> {

    private final Map<ID, T> cache;

    public LRUAlgorithm(int size) {
        this.cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<ID, T> eldest) {
                return size < cache.size();
            }
        };
    }

    @Override
    public void put(ID id, T o) {
        if (id == null || o == null) return;
        cache.put(id, o);
    }

    @Override
    public T get(ID id) {
        final T current = cache.remove(id);
        if (current == null) return null;
        cache.put(id, current);
        return current;
    }

    @Override
    public void delete(ID id) {
        cache.remove(id);
    }
}
