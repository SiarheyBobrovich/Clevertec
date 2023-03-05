package by.bobrovich.market.cache.algoritm;

import by.bobrovich.market.cache.algoritm.api.CacheAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Optional;


@Component
@ConditionalOnProperty(
        name = "cache.algorithm",
        havingValue = "lru"
)
public class LRUAlgorithm<ID, T> implements CacheAlgorithm<ID, T> {

    private final Map<ID, T> cache;

    public LRUAlgorithm(@Value("${cache.size}") int size) {
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
    public Optional<T> get(ID id) {
        final T currentObj = cache.remove(id);
        final Optional<T> result = Optional.ofNullable(currentObj);
        result.ifPresent(o -> cache.put(id, o));
        return result;
    }

    @Override
    public void delete(ID id) {
        cache.remove(id);
    }
}
