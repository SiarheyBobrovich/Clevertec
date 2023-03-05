package by.bobrovich.market.cache.post_processor;

import by.bobrovich.market.cache.algoritm.api.CacheAlgorithm;
import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.cache.factory.AlgorithmFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
@Scope("prototype")
public class CacheInvocationHandler implements InvocationHandler {

    private final CacheAlgorithm<Object, Object> algorithm;
    private final Object target;

    public CacheInvocationHandler(Object target, AlgorithmFactory algorithmFactory) {
        this.algorithm = algorithmFactory.getAlgorithm();
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        final Class<?>[] params = Arrays.stream(objects)
                .map(Object::getClass)
                .toArray(Class[]::new);
        final Method realMethod = target.getClass().getMethod(method.getName(), params);
        final Object result;

        if (realMethod.isAnnotationPresent(Cache.class)) {
            final String idFieldName = realMethod.getAnnotation(Cache.class).id();
            final Optional<Object> objectToSave = Arrays.stream(objects)
                    .filter(o1 -> ReflectionUtils.findField(o1.getClass(), idFieldName) != null)
                    .findFirst();
            if (objectToSave.isPresent()) {
                result = realMethod.invoke(target, objects);
                Object object = objectToSave.get();
                objectToSave.stream()
                        .map(o1 -> ReflectionUtils.findField(o1.getClass(), idFieldName))
                        .filter(field -> !Objects.isNull(field))
                        .map(field -> {
                            ReflectionUtils.makeAccessible(field);
                            return field;
                        }).findFirst()
                        .ifPresent(f -> {
                            Object key = ReflectionUtils.getField(f, object);
                            algorithm.put(key, object);
                        });
            } else if (realMethod.getReturnType() != void.class) {
                Object currentId = objects[0];
                result = algorithm.get(currentId).orElseGet(() -> {
                    Object invokeMethod = ReflectionUtils.invokeMethod(method, target, objects);
                    algorithm.put(currentId, invokeMethod);
                    return invokeMethod;
                });
            } else {
                result = realMethod.invoke(target, objects);
                algorithm.delete(objects[0]);
            }
            return result;
        }
        return method.invoke(target, objects);
    }
}
