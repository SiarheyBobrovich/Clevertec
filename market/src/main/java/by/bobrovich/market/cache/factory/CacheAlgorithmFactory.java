package by.bobrovich.market.cache.factory;

import by.bobrovich.market.cache.algoritm.api.CacheAlgorithm;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class CacheAlgorithmFactory {

    /**
     * Create a cache algorithm and return
     * @return cache algorithm
     */
    public CacheAlgorithm<Object, Object> getAlgorithm() {
        return getCacheAlgorithm();
    }

    @Lookup
    abstract CacheAlgorithm<Object, Object> getCacheAlgorithm();
}
