package gr5.ecomerce.controller;

import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> add(@Valid @RequestBody ProductDTO dto) {
        return service.add(dto);
    }

    @PostMapping("/all")
    public ResponseEntity<List<ProductDTO>> addAll(@Valid @RequestBody List<ProductDTO> dto) {
        return service.addAll(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(@RequestParam int page,@RequestParam int size) {
        return service.getAll(page, size);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> update(@RequestParam Long id, @Valid @RequestBody ProductDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
