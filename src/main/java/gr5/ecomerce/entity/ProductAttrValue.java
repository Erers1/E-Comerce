package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblProductAttrValue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttrValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "attributeVal_id")
    private AttributeValue attributeValues;
}
