package by.bobrovich.market.cache.post_processor;

import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.cache.factory.AlgorithmFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class CacheBeanPostProcessor implements BeanPostProcessor {

    private final AlgorithmFactory algorithmFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final AtomicReference<Object> result = new AtomicReference<>(bean);
        Arrays.stream(bean.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Cache.class))
                .findFirst()
                .ifPresent(m -> {
                    final Class<?> aClass = bean.getClass();
                    final Class<?>[] interfaces = aClass.getInterfaces();
                    final ClassLoader classLoader = aClass.getClassLoader();
                    final CacheInvocationHandler invocationHandler = getInvocationHandler(bean, algorithmFactory);
                        assert invocationHandler != null;
                    final Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
                    result.set(proxyInstance);
                });

        return result.get();
    }

    @Lookup
    public CacheInvocationHandler getInvocationHandler(Object bean, AlgorithmFactory algorithmFactory) {
        return null;
    }
}
