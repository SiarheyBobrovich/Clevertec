package by.bobrovich.market.validation;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.OrderEntry;
import by.bobrovich.market.api.ProductDao;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class OrderValidator implements ConstraintValidator<ValidOrder, Order> {

    private final ProductDao productDao;
    private final DiscountCardDao discountCardDao;

    public OrderValidator(ProductDao productDao,
                          DiscountCardDao discountCardDao) {
        this.productDao = productDao;
        this.discountCardDao = discountCardDao;
    }

    @Override
    public boolean isValid(Order order, ConstraintValidatorContext context) {
        if (order == null) return false;
        final List<OrderEntry> orderEntries = order.orderEntries();
        final Integer cardNumber = order.discountCardNumber();
        return orderEntries != null &&
                !orderEntries.isEmpty() &&
                orderEntries.stream()
                        .allMatch(entry -> entry.id() > 0 && entry.quantity() > 0) &&
                (cardNumber == null ||
                cardNumber > 0);
    }
}