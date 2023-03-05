package by.bobrovich.market.cache.controller;

import by.bobrovich.market.cache.data.request.RequestAlcoholDto;
import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;
import by.bobrovich.market.cache.service.api.AlcoholService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alcohol")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService service;

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseAlcoholDto> alcoholGet(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("/save")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> alcoholSave(@RequestBody RequestAlcoholDto dto) {
        long id = service.save(dto);
        return ResponseEntity.created(URI.create("/alcohol/" + id))
                .build();
    }

    @PutMapping("/update/id/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> alcoholUpdate(@PathVariable Long id,
            @RequestBody RequestAlcoholDto dto) {
        service.update(id, dto);
        return ResponseEntity.created(URI.create("/alcohol/" + id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> alcoholDelete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
