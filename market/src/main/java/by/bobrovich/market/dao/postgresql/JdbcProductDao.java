package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;
import static by.bobrovich.market.dao.postgresql.ProductQueryUtil.SELECT_PRODUCT_BY_ID_QUERY;
import static by.bobrovich.market.dao.postgresql.ProductQueryUtil.SELECT_PRODUCT_BY_ID_AND_QUANTITY_QUERY;
import static by.bobrovich.market.dao.postgresql.ProductQueryUtil.UPDATE_PRODUCT_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {

    protected final PostgresqlDataSource dataSource;

    public JdbcProductDao() {
        this.dataSource = PostgresqlDataSource.getInstance();
    }

    @Override
    public Optional<MarketProduct> getById(Integer id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(map(resultSet));
            }
        }catch (SQLException e) {
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
            throw new IllegalStateException("Data base is not available");
        }
    }


    private MarketProduct map(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        return new MarketProduct(
                resultSet.getInt("id"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("quantity"),
                resultSet.getBoolean("is_discount")
        );
    }
}
