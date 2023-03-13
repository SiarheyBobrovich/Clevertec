package by.bobrovich.market.cache.aspect;

import by.bobrovich.market.cache.algoritm.api.CacheAlgorithm;
import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.cache.factory.CacheAlgorithmFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Log4j2
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "cache.proxy",
        havingValue = "aspect"
)
public class CacheAspect {

    private final Map<Class<?>, CacheAlgorithm<Object, Object>> cacheMap = new ConcurrentHashMap<>();
    private final CacheAlgorithmFactory algorithmFactory;

    @Pointcut("@annotation(by.bobrovich.market.cache.annotation.Cache)")
    public void cachePointcut(){}

    @Around(value = "cachePointcut() && @annotation(by.bobrovich.market.cache.annotation.Cache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        final Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
        final String methodName = joinPoint.getSignature().getName();
        final Object[] params = joinPoint.getArgs();
        final Class<?>[] paramClasses = Arrays.stream(params)
                .map(Object::getClass)
                .toArray(Class[]::new);
        final Method method = ReflectionUtils.findMethod(declaringType, methodName, paramClasses);
        final String fieldName = Objects.requireNonNull(method).getAnnotation(Cache.class).id();

        final boolean isOptional = Objects.requireNonNull(method).getReturnType() == Optional.class;
        final boolean isGet = Arrays.stream(method.getParameters())
                .map(Parameter::getName)
                .anyMatch(fieldName::equals);
        final boolean isDelete = isGet && method.getReturnType() == void.class;

        final int parameterIndex = (int)Arrays.stream(method.getParameters())
                .takeWhile(parameter -> fieldName.equals(parameter.getName()))
                .count() - 1;

        final Object result;
        final Object arg0 = params[Math.max(parameterIndex, 0)];
        if (isDelete) {
            result = joinPoint.proceed();
            getAlgorithm(declaringType).delete(arg0);
        }else if (isGet) {
            result = get(joinPoint, arg0, isOptional);
        }else {
            result = joinPoint.proceed();
            Field field = ReflectionUtils.findField(arg0.getClass(), fieldName);
            assert field != null;
            ReflectionUtils.makeAccessible(field);
            Object ids = ReflectionUtils.getField(field, arg0);
            getAlgorithm(declaringType).put(ids, arg0);
        }
        return result;
    }

    private CacheAlgorithm<Object, Object> getAlgorithm(Class<?> clazz) {
        if (!cacheMap.containsKey(clazz)) {
            cacheMap.put(clazz, algorithmFactory.getAlgorithm());
        }
        return cacheMap.get(clazz);
    }

    private Object get(ProceedingJoinPoint joinPoint, Object id, boolean isOptional) throws Throwable {
        final Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
        final CacheAlgorithm<Object, Object> algorithm = getAlgorithm(declaringType);
        final Optional<Object> objectOptional = algorithm.get(id);
        if (objectOptional.isPresent()) {
            return isOptional ? objectOptional : objectOptional.get();
        }
        final Object proceed = joinPoint.proceed();
        algorithm.put(id, isOptional ? ((Optional<?>)proceed).orElse(null) : proceed);
        return proceed;
    }
}
