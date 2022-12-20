package by.bobrovich.market.config;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {

    @Bean
    ProductDao productDao() {
        return new InMemoryProductDao();
    }

    @Bean
    DiscountCardDao discountCardDao() {
        return new InMemoryDiscountCardDao();
    }
}
