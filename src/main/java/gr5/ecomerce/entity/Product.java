package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tblProduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal sellPrice;
    private int stock;
    private String img;
    private BigDecimal review;
    private boolean isDeleted;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Categories> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Comment> comments;
}
