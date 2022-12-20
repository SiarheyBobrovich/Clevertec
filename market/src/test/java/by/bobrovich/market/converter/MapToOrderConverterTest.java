package by.bobrovich.market.converter;

import by.bobrovich.market.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapToOrderConverterTest {

    MapToOrderConverter converter = new MapToOrderConverter();

    @Test
    void convert() {
        MultiValueMap<String, String> orderMap = getOrderMap();
        Order convert = converter.convert(orderMap);

        assertNotNull(convert);
        assertEquals(1234, convert.discountCardNumber());
        assertEquals(3, convert.orderEntries().size());

        convert.orderEntries().forEach(x -> {
           if (x.id() == 1) {
               assertEquals(2, x.quantity());
           }else if (x.id() == 2) {
               assertEquals(3, x.quantity());
           }else if (x.id() == 3) {
               assertEquals(1, x.quantity());
           }
        });
    }

    private MultiValueMap<String, String> getOrderMap() {
        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        List<String> id = new ArrayList<>();
        id.add("1");
        id.add("1");
        id.add("2");
        id.add("2");
        id.add("2");
        id.add("3");
        valueMap.put("itemId", id);
        valueMap.put("card", List.of("1234"));

        return valueMap;
    }
}