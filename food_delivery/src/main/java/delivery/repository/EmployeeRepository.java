package delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import delivery.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
    //Employee findTopByOrderByIdDesc();
}
