package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tblAttribute")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    @OneToMany(mappedBy = "attributes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttrValue> productAttrValues;
}
