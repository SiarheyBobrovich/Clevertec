package by.bobrovich.market.service.api;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.validation.ValidOrder;

import java.nio.file.Path;

/**
 * @author Babrovich Siarhey on 17.03.2023
 */
public interface PdfService {
    Path getPath(@ValidOrder Order order);
}
