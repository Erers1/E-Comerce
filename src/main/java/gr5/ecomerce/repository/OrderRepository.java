package gr5.ecomerce.repository;

import gr5.ecomerce.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
        select o from Order o
        where 
          o.orderDate >= :start
          and o.orderDate < :end
    """)
    List<Order> getProfit(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.orderDate DESC")
    List<Order> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderDetail od JOIN od.product p WHERE p.seller.id = :sellerId ORDER BY o.orderDate DESC")
    List<Order> findOrdersBySellerId(@Param("sellerId") Long sellerId);

    @Query("""
        SELECT DISTINCT o FROM Order o
        JOIN o.orderDetail od
        JOIN od.product p
        WHERE o.orderDate >= :start AND o.orderDate < :end
        AND p.seller.id = :sellerId
    """)
    List<Order> getProfitBySeller(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("sellerId") Long sellerId
    );
}
