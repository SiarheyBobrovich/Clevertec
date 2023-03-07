package by.bobrovich.market.cache.controller;

import by.bobrovich.market.cache.data.request.RequestAlcoholDto;
import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;
import by.bobrovich.market.cache.service.api.AlcoholService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/alcohol")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<ResponseAlcoholDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseAlcoholDto> alcoholGet(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> alcoholSave(@RequestBody RequestAlcoholDto dto) {
        service.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> alcoholUpdate(@PathVariable Long id,
            @RequestBody RequestAlcoholDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> alcoholDelete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
