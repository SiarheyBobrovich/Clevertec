package by.bobrovich.market.cache.algoritm;

import java.util.Map;
import java.util.LinkedHashMap;

public class LFUAlgorithm<ID, T> implements CacheAlgorithm<ID, T> {

    private class Node {

        private final T object;
        private long frequency;
        Node(T o) {
            this.object = o;
            this.frequency = 1;
        }
        private T incrementAndGet() {
            this.frequency++;
            return object;
        }
    }
    private final Map<ID, Node> cache;
    private final int size;
    public LFUAlgorithm(int size) {
        this.cache = new LinkedHashMap<>();
        this.size = size;
    }

    public void put(ID id, T o) {
        if (id == null || o == null) return;
        Node node = cache.get(id);
        if (node == null) {
            removeOldestElement();
            cache.put(id, new Node(o));
        }
    }
    public T get(ID id) {
        Node node = cache.get(id);
        return node == null ? null : node.incrementAndGet();
    }

    public void delete(ID id) {
        cache.remove(id);
    }

    private void removeOldestElement() {
        if (cache.size() < size) return;
        cache.values().stream()
                .map(node -> node.frequency)
                .min(Long::compareTo)
                .flatMap(min -> cache.entrySet().stream()
                        .filter(idNode -> idNode.getValue().frequency == min)
                        .map(Map.Entry::getKey)
                        .findFirst()).ifPresent(cache::remove);
    }
}
