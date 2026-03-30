package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblProductImage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
