package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.dao.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.ProductSqlException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.bobrovich.market.dao.postgresql.util.ProductQueryUtil.*;


@Repository
@ConditionalOnProperty(
        name = "spring.product.database",
        havingValue= "jdbc"
)
@RequiredArgsConstructor
@Log4j2
public class JdbcProductDao implements ProductDao {

    private final DataSource dataSource;

    @Override
    public Optional<MarketProduct> findById(Integer id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(map(resultSet));
            }
        }catch (SQLException e) {
            log.warn("Find by id", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    @Override
    public boolean exists(Integer id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException e) {
            log.warn("Exists", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    @Override
    public boolean isExistsAndQuantityAvailable(Integer id, Integer quantity) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID_AND_QUANTITY_QUERY)
        ) {
            statement.setInt(1, id);
            statement.setInt(2, quantity);

            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }catch (SQLException e) {
            log.warn("Is exists and quantity available", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    @Override
    public void update(MarketProduct product) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)
        ) {
            statement.setString(1, product.getDescription());
            statement.setBigDecimal(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setBoolean(4, product.isDiscount());
            statement.setInt(5, product.getId());

            statement.executeUpdate();
        }catch (SQLException e) {
            log.warn("Update", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    @Override
    public void delete(Integer id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_BY_ID)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            log.warn("Delete", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    @Override
    public void save(MarketProduct product) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    INSERT_NEW_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, product.getDescription());
            statement.setBigDecimal(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setBoolean(4, product.isDiscount());

            int count = statement.executeUpdate();
            if (count == 0) throw new ProductSqlException("Creating product failed, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) throw new ProductSqlException("Creating alcohol failed, no ID obtained.");
            product.setId(generatedKeys.getInt("id"));

        }catch (SQLException e) {
            log.warn("Save", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    @Override
    public List<MarketProduct> findAll() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS)
        ) {
            try(ResultSet resultSet = statement.executeQuery()) {
                final List<MarketProduct> products = new ArrayList<>();
                MarketProduct p;
                while ((p = map(resultSet)) != null) {
                    products.add(p);
                }
                return products;
            }
        }catch (SQLException e) {
            log.warn("Find all", e);
            throw new IllegalStateException("Data base is not available");
        }
    }

    private MarketProduct map(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        return MarketProduct.builder()
                .id(resultSet.getInt("id"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .quantity(resultSet.getInt("quantity"))
                .isDiscount(resultSet.getBoolean("is_discount"))
                .build();
    }
}
