package gr5.ecomerce.repository;

import gr5.ecomerce.entity.Order;
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
}
