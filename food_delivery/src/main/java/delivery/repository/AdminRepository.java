package delivery.repository;

import delivery.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUsername(String username);
    Admin findByUsername(String username);  
}
