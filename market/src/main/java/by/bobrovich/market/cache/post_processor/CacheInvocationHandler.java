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

    /**
     * If the cache contains an object, returns the object from the cache
     * or caches the object caches to the specified algorithm.
     *
     * @param o       the proxy instance that the method was invoked on
     * @param method  Method marked @Cache
     * @param objects an array of objects containing the values of the
     *                arguments passed in the method invocation on the proxy instance,
     *                or {@code null} if interface method takes no arguments.
     *                Arguments of primitive types are wrapped in instances of the
     *                appropriate primitive wrapper class, such as
     *                {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return if an object contains in cache return the cached object
     *                else calls a real method, save result in cache and return
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        final Method realMethod = target.getClass()
                .getMethod(method.getName(), method.getParameterTypes());
        final Object result;

        if (realMethod.isAnnotationPresent(Cache.class)) {
            final String idFieldName = realMethod.getAnnotation(Cache.class).id();

            //Check parameters contain object with field 'id' like save, update
            final Optional<Object> objectToSave = Arrays.stream(objects)
                    .filter(o1 -> ReflectionUtils.findField(o1.getClass(), idFieldName) != null)
                    .findFirst();
            //methods: public * save(Object)
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
                //methods: public Object get(id)
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
                //method: public void delete(id)
            } else {
                result = realMethod.invoke(target, objects);
                algorithm.delete(objects[0]);
            }
            return result;
        }
        return method.invoke(target, objects);
    }
}
