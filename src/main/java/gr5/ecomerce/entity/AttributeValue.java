package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tblAttrValue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @OneToMany(mappedBy = "attributeValues", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttrValue> attributeValues;
}
