package by.bobrovich.market.converter;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.data.MarketOrderEntry;
import by.bobrovich.market.exceptions.ConvertionException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapToOrderConverter implements OrderConverter<MultiValueMap<String, String>, Order> {

    @Override
    public Order convert(MultiValueMap<String, String> args) {
        if (!args.containsKey("itemId")) {
            throw new ConvertionException("Request must have 1 or more itemId parameter");
        }

        final Map<Integer, Integer> itemsId = getItemsIdQtyAsMap(args.get("itemId"));
        final MarketOrder.Builder builder = MarketOrder.builder();

        itemsId.forEach((id, qty) ->
                builder.addOrderEntry(new MarketOrderEntry(
                    id,
                    qty
                )));

        return builder.addDiscountCard(getDiscountCardNumber(args.get("card")))
                .build();
    }

    private Integer getDiscountCardNumber(List<String> cards) {
        if (cards == null || cards.isEmpty() || !cards.get(0).matches("\\d+")) {
            return null;
        }

        return Integer.parseInt(cards.get(0));
    }

    private Map<Integer, Integer> getItemsIdQtyAsMap(List<String> itemsId) {
        final Map<Integer, Integer> itemsIdQty = new HashMap<>();

        itemsId.forEach (id -> {
            int itemId = Integer.parseInt(id);
            itemsIdQty.put(itemId, itemsIdQty.getOrDefault(itemId, 0) + 1);
        });

        return itemsIdQty;
    }
}