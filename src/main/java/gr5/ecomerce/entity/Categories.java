package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tblCategory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "categories",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attributes> attributes;
}
