package gr5.ecomerce.repository;

import gr5.ecomerce.entity.Attribute;
import gr5.ecomerce.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    @Query("SELECT a FROM AttributeValue a WHERE a.attribute.id = :id AND a.value = :name")
    AttributeValue findByValueAndAttribute(@Param("id") Long id, @Param("name") String name);
}
