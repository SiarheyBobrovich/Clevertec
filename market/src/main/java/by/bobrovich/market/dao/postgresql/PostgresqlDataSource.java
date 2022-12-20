package by.bobrovich.market.dao.postgresql;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.el.PropertyNotFoundException;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresqlDataSource implements AutoCloseable {
    private final DataSource dataSource;
    private final String driver;
    private final String url;
    private final String userName;
    private final String password;

    private PostgresqlDataSource() {
        Path applicationProperties = Paths.get("market/src/main/resources/application.properties");
        Path applicationPropertiesTest = Paths.get("src/main/resources/application.properties");
        Path dockerProperties = Paths.get("/app/application.properties");

        Properties properties;

        if (Files.exists(applicationProperties)) {
            properties = loadProperties(applicationProperties);
        }else if (Files.exists(applicationPropertiesTest)) {
            properties = loadProperties(applicationPropertiesTest);
        }else if (Files.exists(dockerProperties)) {
            properties = loadProperties(dockerProperties);
        }else {
            throw new PropertyNotFoundException("Properties not found");
        }

        url = properties.getProperty("spring.datasource.url");
        driver = properties.getProperty("spring.datasource.Driver");
        userName = properties.getProperty("spring.datasource.username");
        password = properties.getProperty("spring.datasource.password");

        this.dataSource = getPool();
    }

    private Properties loadProperties(Path applicationProperties) {
        Properties properties = new Properties();
        try(BufferedReader reader = Files.newBufferedReader(applicationProperties)) {
            properties.load(reader);

        } catch (IOException e) {
            throw new RuntimeException("Properties not found");
        }

        return properties;
    }

    private static final class PostgresqlDataSourceHolder {
        private static final PostgresqlDataSource postgresqlDataSource = new PostgresqlDataSource();
    }

    /**
     * Singleton's getter
     * @return - link to DataSource object
     */
    public static PostgresqlDataSource getInstance() {
        return PostgresqlDataSourceHolder.postgresqlDataSource;
    }

    /**
     * Getter for connect to JDBC
     * @return - connecting from pool
     * @throws SQLException - if connect has been closed
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Create and connect pool to JDBC
     * @return new data source object
     */
    private DataSource getPool() {
        ComboPooledDataSource pool = new ComboPooledDataSource();

        try {
            pool.setDriverClass(this.driver);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Проверьте правильность драйвера");
        }

        pool.setJdbcUrl(this.url);
        pool.setUser(this.userName);
        pool.setPassword(this.password);

        return pool;
    }

    @Override
    public void close() {
        ((ComboPooledDataSource) dataSource).close();
    }
}