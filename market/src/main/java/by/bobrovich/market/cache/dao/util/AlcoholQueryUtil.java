package by.bobrovich.market.cache.dao.util;

public class AlcoholQueryUtil {
    private AlcoholQueryUtil(){}
    public static String SELECT_ALCOHOL_BY_ID =
            """
            SELECT
                id,
                name,
                price,
                country,
                vol,
                quantity
            FROM
                market.alcohol
                WHERE id = ?;
            """;
    public static String SELECT_ALL_ALCOHOLS =
            """
            SELECT
                id,
                name,
                price,
                country,
                vol,
                quantity
            FROM
                market.alcohol;
            """;
    public static String INSERT_NEW_ALCOHOL = """
            INSERT INTO
                market.alcohol(
                    name,
                    price,
                    country,
                    vol,
                    quantity
                )
                VALUES (?,?,?,?,?);
            """;
    public static String UPDATE_ALCOHOL_BY_ID = """
            UPDATE market.alcohol\s
                SET name = ?,
                	price = ?,
                	country = ?,
                	vol = ?,
                	quantity = ?
            	WHERE id = ?;
            """;
    public static String DELETE_ALCOHOL_BY_ID = """
            DELETE FROM market.alcohol
                WHERE id = ?;
            """;

}
