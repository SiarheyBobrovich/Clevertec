package by.bobrovich.market.cache.algoritm;

import by.bobrovich.market.cache.algoritm.api.CacheAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Optional;

@Component
@ConditionalOnProperty(
        name = "cache.algorithm",
        havingValue = "lfu"
)
@Scope("prototype")
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
    public LFUAlgorithm(@Value("${cache.size}") int size) {
        this.cache = new LinkedHashMap<>();
        this.size = size;
    }

    @Override
    public void put(ID id, T o) {
        if (id == null || o == null) return;
        Optional<Node> node = Optional.ofNullable(cache.get(id));
        cache.put(id, node.orElseGet(() -> {
            removeOldestElement();
            return new Node(o);
        }));
    }

    @Override
    public Optional<T> get(ID id) {
        final Node node = cache.get(id);
        return node == null ? Optional.empty() : Optional.of(node.incrementAndGet());
    }

    @Override
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
