package gr5.ecomerce.controller;

import gr5.ecomerce.dto.DiscountDTO;
import gr5.ecomerce.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService service;

    @PostMapping("/discount/create")
    public ResponseEntity<DiscountDTO> create(@Valid @RequestBody DiscountDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDTO>> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<DiscountDTO> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/discount")
    public ResponseEntity<String> apply(@RequestParam Long orderId,@RequestParam Long discountId) {
        return service.apply(orderId, discountId);
    }
}
