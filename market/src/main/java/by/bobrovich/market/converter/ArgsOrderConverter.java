package by.bobrovich.market.converter;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.data.MarketOrderEntry;
import by.bobrovich.market.exceptions.ConvertionException;
import org.springframework.stereotype.Component;

@Component
public class ArgsOrderConverter implements OrderConverter<String[], Order> {

    @Override
    public Order convert(String[] args) {
        check(args);
        MarketOrder.Builder orderBuilder = MarketOrder.builder();

        for (String arg : args) {
            String[] keyValue = arg.split("-");
            String key = keyValue[0];
            int value = tryToParse(keyValue[1]);

            if (isDigit(key)){
               orderBuilder.addOrderEntry(
                       new MarketOrderEntry(
                           tryToParse(keyValue[0]),
                           value
                       )
               );
            } else {
               orderBuilder.addDiscountCard(value);
            }
        }

        return orderBuilder.build();
    }

    /**
     * Check string as digits
     * @param key - checked string
     * @return true if digits
     */
    private boolean isDigit(String key) {
        return key.matches("\\d+");
    }

    private int tryToParse(String key) {
        try {
            return Integer.parseInt(key);
        }catch (NumberFormatException e) {
            throw new ConvertionException("Number must be as integer");
        }
    }

    private void check(String[] args) {
        if (args == null || args.length == 0) {
            throw new ConvertionException("Empty args");
        }
        if (countItems(args) == 0) {
            throw new ConvertionException("Empty orders");
        }
    }

    /**
     * Check and count items
     * @param args - String array
     * @return - Count items
     */
    private int countItems(String[] args) {
        int countOrders = 0;

        for (String arg : args) {
            if (isOrder(arg)) {
                countOrders++;
            }else if (!isCard(arg)) {
                throw new ConvertionException("Unsupported args");
            }
        }
        return countOrders;
    }

    private boolean isOrder(String s) {
        return s.matches("\\d+-\\d+");
    }

    private boolean isCard(String s) {
        return s.matches("card-\\d+");
    }
}