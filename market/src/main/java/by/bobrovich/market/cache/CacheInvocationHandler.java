package by.bobrovich.market.cache;

import by.bobrovich.market.cache.algoritm.LFUAlgorithm;
import by.bobrovich.market.cache.annotation.Cache;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class CacheInvocationHandler implements InvocationHandler {

    private final LFUAlgorithm<Object, Object> algorithm;
    private final Object target;

    public CacheInvocationHandler(int size, Object target) {
        this.algorithm = new LFUAlgorithm<>(size);
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        final Class<?>[] params = Arrays.stream(objects)
                .map(Object::getClass)
                .toArray(Class[]::new);
        final Method realMethod = target.getClass().getMethod(method.getName(), params);
        final Object current;

        if (realMethod.isAnnotationPresent(Cache.class)) {
            final String idFieldName = realMethod.getAnnotation(Cache.class).id();
            final boolean isVoid = realMethod.getReturnType() == void.class;
            final Optional<Object> objectToSave = Arrays.stream(objects)
                    .filter(o1 -> ReflectionUtils.findField(o1.getClass(), idFieldName) != null)
                    .findFirst();
            final int paramIndex = (int) Arrays.stream(method.getParameters())
                    .takeWhile(p -> !p.getName().equals(idFieldName))
                    .count() - 1;

            if (objectToSave.isPresent()) {
                current = realMethod.invoke(target, objects);
                Object object = objectToSave.get();
                objectToSave.stream()
                        .map(o1 -> ReflectionUtils.findField(object.getClass(), idFieldName))
                        .filter(field -> !Objects.isNull(field))
                        .map(f -> {
                            ReflectionUtils.makeAccessible(f);
                            return f;
                        }).findFirst()
                        .ifPresent(f -> {
                            Object key = ReflectionUtils.getField(f, object);
                            algorithm.put(key, object);
                        });
            } else if (!isVoid) {
                Object currentId = objects[paramIndex];
                Object currentEntity = algorithm.get(currentId);
                if (currentEntity == null) {
                    currentEntity = ReflectionUtils.invokeMethod(method, target, objects);
                    algorithm.put(currentId, currentEntity);
                }
                current = currentEntity;
            } else {
                current = realMethod.invoke(target, objects);
                algorithm.delete(objects[paramIndex]);
            }
            return current;
        }
        return method.invoke(target, objects);
    }
}
