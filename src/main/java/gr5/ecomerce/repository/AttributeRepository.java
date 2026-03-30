package gr5.ecomerce.repository;

import gr5.ecomerce.entity.Attribute;
import gr5.ecomerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    @Query("SELECT a FROM Attribute a WHERE a.name = :name")
    Attribute findByName(@Param("name") String name);
}
