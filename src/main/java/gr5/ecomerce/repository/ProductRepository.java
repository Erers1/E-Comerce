package gr5.ecomerce.repository;

import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.OrderDetail;
import gr5.ecomerce.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.seller.id = :sellerId and p.isDeleted = false")
    List<Product> findBySellerId(@Param("sellerId") Long sellerId, Pageable pageable);
}
