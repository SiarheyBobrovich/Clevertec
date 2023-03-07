package by.bobrovich.market.cache.dao;

import by.bobrovich.market.cache.annotation.Cache;
import by.bobrovich.market.cache.dao.api.AlcoholDao;
import by.bobrovich.market.cache.entity.Alcohol;
import by.bobrovich.market.cache.exception.AlcoholNotFoundException;
import by.bobrovich.market.cache.exception.AlcoholSqlException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.bobrovich.market.cache.dao.util.AlcoholQueryUtil.*;

@RequiredArgsConstructor
@Repository
@ConditionalOnProperty(
        name = "spring.alcohol.database",
        havingValue = "jdbc"
)
public class JdbcAlcoholDao implements AlcoholDao {

    private final DataSource dataSource;

    @Override
    public List<Alcohol> getAll() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ALCOHOLS)
        ) {
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Alcohol> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(map(resultSet));
                }
                return result;
            }
        }catch (SQLException e) {
            throw new AlcoholSqlException("Data base is not available", e);
        }
    }

    @Override
    @Cache
    public Alcohol get(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALCOHOL_BY_ID)
        ) {
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    return map(resultSet);
                }
                throw new AlcoholNotFoundException("Not found");
            }
        }catch (SQLException e) {
            throw new AlcoholSqlException("Data base is not available", e);
        }
    }

    @Override
    @Cache
    public Long save(Alcohol alcohol) {
        final long id;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    INSERT_NEW_ALCOHOL, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, alcohol.getName());
            statement.setBigDecimal(2, alcohol.getPrice());
            statement.setString(3, alcohol.getCountry());
            statement.setDouble(4, alcohol.getVol());
            statement.setInt(5, alcohol.getQuantity());

            int row = statement.executeUpdate();
            if (row == 0) throw new AlcoholSqlException("Creating alcohol failed, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) throw new AlcoholSqlException("Creating alcohol failed, no ID obtained.");
            id = generatedKeys.getLong("id");
            alcohol.setId(id);

        }catch (SQLException e) {
            throw new AlcoholSqlException("Data base is not available", e);
        }
        return id;
    }

    @Override
    @Cache
    public void update(Alcohol alcohol) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ALCOHOL_BY_ID)
        ) {
            statement.setString(1, alcohol.getName());
            statement.setBigDecimal(2, alcohol.getPrice());
            statement.setString(3, alcohol.getCountry());
            statement.setDouble(4, alcohol.getVol());
            statement.setInt(5, alcohol.getQuantity());
            statement.setLong(6, alcohol.getId());

            statement.executeUpdate();
        }catch (SQLException e) {
            throw new AlcoholSqlException("Data base is not available", e);
        }
    }

    @Override
    @Cache
    public void delete(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ALCOHOL_BY_ID)
        ) {
            statement.setLong(1, id);

            statement.executeUpdate();
        }catch (SQLException e) {
            throw new AlcoholSqlException("Data base is not available", e);
        }
    }

    private Alcohol map(ResultSet resultSet) throws SQLException {
        return Alcohol.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .country(resultSet.getString("country"))
                .price(resultSet.getBigDecimal("price"))
                .quantity(resultSet.getInt("quantity"))
                .vol(resultSet.getDouble("vol"))
                .build();
    }
}
