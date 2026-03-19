package gr5.ecomerce.repository;

import gr5.ecomerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username like ?1")
    User findByName(String username);
}
