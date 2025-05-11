package delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import delivery.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsername(String username);
    Employee findByUsername(String username);  
}

