package by.bobrovich.market.controller;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.data.MarketOrder;
import by.bobrovich.market.service.api.PdfService;
import by.bobrovich.market.service.api.ReceiptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class PdfMarketController extends MarketController{

    private final PdfService service;

    public PdfMarketController(ReceiptService service, PdfService service1) {
        super(service);
        this.service = service1;
    }

    @GetMapping(value = "/pdf" ,consumes = "application/*", produces = "application/pdf")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<byte[]> getReceiptAsPdf(@RequestParam List<Integer> itemId,
                                                       @RequestParam(required = false) Integer card) throws IOException {
        Order order = MarketOrder.builder()
                .addItemsId(itemId)
                .addDiscountCard(card)
                .build();

        HttpHeaders headers = new HttpHeaders();
        String filename = "receipt.pdf";
        headers.setContentDispositionFormData(filename, filename);

        Path path = service.getPath(order);
        return ResponseEntity.ok().headers(headers).body(Files.readAllBytes(path));
    }
}
