package by.bobrovich.market.controller;

import by.bobrovich.market.data.product.request.RequestProductDto;
import by.bobrovich.market.data.product.response.ResponseProductDto;
import by.bobrovich.market.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<ResponseProductDto>> getAllProducts() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Valid
    public ResponseEntity<Void> updateProduct(@PathVariable Integer id,
                                              @RequestBody RequestProductDto dto) {
        service.update(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Valid
    public ResponseEntity<Void> saveProduct(@RequestBody RequestProductDto dto) {
        service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
