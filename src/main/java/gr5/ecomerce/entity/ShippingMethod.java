package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tblShippingMethod")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal fee;

    @OneToMany(mappedBy = "shippingMethod", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Order> order;
}
