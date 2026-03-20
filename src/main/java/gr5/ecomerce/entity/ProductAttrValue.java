package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblAttrValue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttrValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attributes attributes;
}
