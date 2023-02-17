package by.bobrovich.market.config;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.dao.FileDiscountCardDao;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.postgresql.JdbcDiscountCardDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class CardDaoConfig {
    @Value("${spring.card.filename}")
    private String cardFileName;
    @Bean
    DiscountCardDao discountCardDao() {
        return new InMemoryDiscountCardDao();
    }
//    @Bean
//    DiscountCardDao discountCardDao() throws IOException {
//        return new FileDiscountCardDao(cardFileName);
//    }
//    @Bean
//    DiscountCardDao discountCardDao(DataSource dataSource) {
//        return new JdbcDiscountCardDao(dataSource);
//    }
}