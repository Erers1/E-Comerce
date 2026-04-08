package gr5.ecomerce.service;

import gr5.ecomerce.dto.ProfitDTO;
import org.springframework.http.ResponseEntity;

public interface StatisticService {
    ResponseEntity<ProfitDTO> getMonthlyProfit(Long sellerId);
}
