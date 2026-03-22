package gr5.ecomerce.repository;

import gr5.ecomerce.dto.TopProductDTO;
import gr5.ecomerce.entity.OrderDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("Select o from OrderDetail o where o.product.id = :productId")
    List<OrderDetail> existsByProductId(@Param("productId") Long productId);

    @Query("""
        SELECT new gr5.ecomerce.dto.TopProductDTO(od.product.id, od.product.name, SUM(od.quantity))
        FROM OrderDetail od
        GROUP BY od.product
        ORDER BY SUM(od.quantity) DESC
    """)
    List<TopProductDTO> findTopProduct(Pageable pageable);
}
