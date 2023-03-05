package by.bobrovich.market.cache.post_processor;

import by.bobrovich.market.cache.algoritm.LFUAlgorithm;
import by.bobrovich.market.cache.dao.api.AlcoholDao;
import by.bobrovich.market.cache.dao.InMemoryAlcoholDao;
import by.bobrovich.market.cache.entity.Alcohol;
import by.bobrovich.market.cache.factory.AlgorithmFactory;
import by.bobrovich.market.exceptions.AlcoholNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CacheInvocationHandlerTest {

    private AlcoholDao proxy;
    private InMemoryAlcoholDao inMemoryAlcoholDao;
    @BeforeEach
    void setUp() {
        Class<InMemoryAlcoholDao> alcoholDaoClass = InMemoryAlcoholDao.class;
        inMemoryAlcoholDao = Mockito.mock(alcoholDaoClass);
        AlgorithmFactory algorithmFactory = mock(AlgorithmFactory.class);
        doReturn(new LFUAlgorithm<>(2)).when(algorithmFactory).getAlgorithm();

        ClassLoader classLoader = alcoholDaoClass.getClassLoader();
        Class<?>[] interfaces = alcoholDaoClass.getInterfaces();
        CacheInvocationHandler proxy = new CacheInvocationHandler(inMemoryAlcoholDao, algorithmFactory);
        this.proxy = (AlcoholDao) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

    @Test
    void CheckInvokeGet() {
        Alcohol expected = Alcohol.builder().build();
        doReturn(expected, expected, null)
                .when(inMemoryAlcoholDao)
                .get(any());

        proxy.get(2L);
        proxy.get(3L);
        Alcohol alcohol2 = proxy.get(3L);

        assertThat(alcohol2).isEqualTo(expected);
    }

    @Test
    void CheckInvokeDELETE() {
        Alcohol alcohol = Alcohol.builder().id(5L).build();
        Alcohol expected = Alcohol.builder().build();
        doReturn(alcohol, expected).when(inMemoryAlcoholDao)
                .get(5L);
        doNothing().when(inMemoryAlcoholDao)
                .delete(5L);

        proxy.get(5L);
        proxy.delete(5L);
        Alcohol actual = proxy.get(5L);

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void CheckInvokePost() {
        Alcohol alcohol = Alcohol.builder().id(5L).build();
        doThrow(AlcoholNotFoundException.class).when(inMemoryAlcoholDao)
                .get(5L);
        doReturn(123L).when(inMemoryAlcoholDao)
                .save(alcohol);

        proxy.save(alcohol);
        Alcohol actual = proxy.get(5L);

        assertThat(actual).isEqualTo(alcohol);
    }
    @Test
    void CheckInvokePut() {
        Alcohol alcohol = Alcohol.builder().id(5L).build();
        doThrow(AlcoholNotFoundException.class).when(inMemoryAlcoholDao)
                .get(5L);
        doNothing().when(inMemoryAlcoholDao)
                .update(alcohol);

        proxy.update(alcohol);
        Alcohol actual = proxy.get(5L);

        assertThat(actual).isEqualTo(alcohol);
    }
}