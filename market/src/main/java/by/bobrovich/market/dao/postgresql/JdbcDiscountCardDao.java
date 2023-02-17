package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.entity.MarketDiscountCard;

import javax.sql.DataSource;

import static by.bobrovich.market.dao.postgresql.util.DiscountCardQueryUtil.SELECT_DISCOUNT_CARD_BY_ID_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcDiscountCardDao implements DiscountCardDao {

    private final DataSource dataSource;

    public JdbcDiscountCardDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<MarketDiscountCard> getById(Integer id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_DISCOUNT_CARD_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(map(resultSet));
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Data base is not available");
        }
    }

    private MarketDiscountCard map(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        return new MarketDiscountCard(
                resultSet.getInt("card_number"),
                resultSet.getByte("discount")
        );
    }
}