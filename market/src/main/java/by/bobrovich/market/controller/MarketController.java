package by.bobrovich.market.controller;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.service.MapProductServiceDecorator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/check")
public class MarketController {

    private final MapProductServiceDecorator service;

    public MarketController(MapProductServiceDecorator service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Receipt> getBill(@RequestParam MultiValueMap<String, String> parameters) {
        Receipt receipt = service.getReceipt(parameters);

        return ResponseEntity.ok().body(receipt);
    }
}
