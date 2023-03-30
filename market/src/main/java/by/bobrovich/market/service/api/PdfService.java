package by.bobrovich.market.service.api;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.validation.ValidOrder;

import java.nio.file.Path;

public interface PdfService {

    /**
     * Creates a temporary Pdf file with a receipt, returns the path to the created file
     * @param order a receipt order
     * @return path to temp file
     */
    Path getPath(@ValidOrder Order order);
}
