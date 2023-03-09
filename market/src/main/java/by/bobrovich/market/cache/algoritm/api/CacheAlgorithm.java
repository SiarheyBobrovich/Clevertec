package by.bobrovich.market.cache.algoritm.api;

import java.util.Optional;

public interface CacheAlgorithm<ID, T> {
    /**
     * Put an object into the cache
     * @param id object ID
     * @param o object to cache
     */
    void put(ID id, T o);

    /**
     * Get an object by ID
     * @param id object ID
     * @return the Optional of object
     */
    Optional<T> get(ID id);

    /**
     * Delete an object from the cache
     * @param id object ID
     */
    void delete(ID id);
}
