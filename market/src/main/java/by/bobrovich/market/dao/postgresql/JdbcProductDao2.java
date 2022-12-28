package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.api.ProductDaoExistsAllId;
import by.bobrovich.market.exceptions.ServiceNotAvailableNow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static by.bobrovich.market.dao.postgresql.ProductQueryUtil.*;

public class JdbcProductDao2 extends JdbcProductDao implements ProductDaoExistsAllId {

    @Override
    public boolean existsAllId(Collection<Integer> ids) {
        if (ids == null || ids.size() == 0) return false;

        final int size = ids.size();
        int index = 1;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(buildQuery(size))) {

            for (Integer id : ids) {
                statement.setInt(index++, id);
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") == size;
            }

        }catch (SQLException e) {
            throw new ServiceNotAvailableNow("Db is not available");
        }
    }

    private String buildQuery(int countId) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < countId; i++) {
            builder.append("?");
            if (i != countId - 1) {
                builder.append(",");
            }
        }

        return SELECT_PRODUCT_BY_ID_IN_QUERY.replace("?", builder.toString());
    }
}
