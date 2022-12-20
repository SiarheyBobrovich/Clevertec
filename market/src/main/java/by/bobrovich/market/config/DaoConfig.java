package by.bobrovich.market.config;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.dao.postgresql.JdbcDiscountCardDao;
import by.bobrovich.market.dao.postgresql.JdbcProductDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {

    @Bean
    ProductDao productDao() {
        return new JdbcProductDao();
    }

    @Bean
    DiscountCardDao discountCardDao() {
        return new JdbcDiscountCardDao();
    }
}
