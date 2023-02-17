package by.bobrovich.market.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

//@Configuration
public class DataSourceConfig {
    @Bean
    DataSource dataSource(Environment env) {
        ComboPooledDataSource pool = new ComboPooledDataSource();
        try {
            pool.setDriverClass(env.getProperty("spring.datasource.Driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Проверьте правильность драйвера");
        }

        pool.setJdbcUrl(env.getProperty("spring.datasource.url"));
        pool.setUser(env.getProperty("spring.datasource.username"));
        pool.setPassword(env.getProperty("spring.datasource.password"));

        return pool;
    }
}