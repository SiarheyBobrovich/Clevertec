package by.bobrovich.market.dao.postgresql.util;

public class DiscountCardQueryUtil {

    public static final String SELECT_DISCOUNT_CARD_BY_ID_QUERY =
            """
            SELECT
                card_number,
                discount
            FROM
                discount_card
            WHERE
                card_number = ?;
            """;

    private DiscountCardQueryUtil(){}
}
