package gr5.ecomerce.repository;

import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.OrderDetail;
import gr5.ecomerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
