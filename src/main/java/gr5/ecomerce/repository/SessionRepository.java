package gr5.ecomerce.repository;

import gr5.ecomerce.entity.Session;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Modifying
    @Transactional
    @Query("delete from Session s where s.refreshToken like ?1")
    void delByToken(String refreshToken);
}
