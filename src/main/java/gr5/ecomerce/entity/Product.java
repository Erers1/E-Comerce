package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> img = new ArrayList<>();

    private BigDecimal review;
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttrValue> productAttrValues = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
