package by.bobrovich.market.cache.post_processor;

import by.bobrovich.market.cache.algoritm.api.CacheAlgorithm;
import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.cache.factory.CacheAlgorithmFactory;
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

    public CacheInvocationHandler(Object target, CacheAlgorithmFactory algorithmFactory) {
        this.algorithm = algorithmFactory.getAlgorithm();
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        final Method realMethod = target.getClass()
                .getMethod(method.getName(), method.getParameterTypes());
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
                Object objectId = objects[0];
                boolean isOptional = realMethod.getReturnType() == Optional.class;
                Object cacheObject = algorithm.get(objectId)
                        .orElseGet(() -> {
                            Object methodObject = ReflectionUtils.invokeMethod(method, target, objects);
                            Object o1 = isOptional && methodObject != null ?
                                    ((Optional<?>) methodObject).orElse(null) : methodObject;
                            algorithm.put(objectId, o1);
                            return o1;
                        });
                result = isOptional ? Optional.ofNullable(cacheObject) : cacheObject;
            } else {
                result = realMethod.invoke(target, objects);
                algorithm.delete(objects[0]);
            }
            return result;
        }
        return method.invoke(target, objects);
    }
}
