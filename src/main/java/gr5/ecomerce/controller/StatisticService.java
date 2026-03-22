package gr5.ecomerce.controller;

import gr5.ecomerce.dto.ProfitDTO;
import gr5.ecomerce.dto.TopProductDTO;
import gr5.ecomerce.repository.OrderDetailRepository;
import gr5.ecomerce.service.OrderService;
import gr5.ecomerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticService {
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/date")
    public ResponseEntity<ProfitDTO> getProfitByDate() {
        return orderService.getProfitByDay();
    }

    @GetMapping("/month")
    public ResponseEntity<ProfitDTO> getProfitByMonth() {
        return orderService.getProfitByMonth();
    }

    @GetMapping("/best")
    public ResponseEntity<List<TopProductDTO>> topProduct() {
        return productService.topProduct();
    }

}
