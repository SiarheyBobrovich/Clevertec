package by.bobrovich.market.config;

import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.dao.FileProductDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.dao.postgresql.JdbcProductDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class ProductDaoConfig {

    @Value("${spring.product.filename}")
    private String productFileName;
    @Bean
    ProductDao productDao() {
        return new InMemoryProductDao();
    }

//    @Bean
//    ProductDao productDao() throws IOException {
//        return new FileProductDao(productFileName);
//    }
}