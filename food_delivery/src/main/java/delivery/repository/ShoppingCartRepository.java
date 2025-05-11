package delivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import delivery.ShoppingCart;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
  @Query("SELECT c FROM ShoppingCart c WHERE c.employee.id = :employeeId")
  List<ShoppingCart> findOrderHistoryByEmployeeId(@Param("employeeId") Long employeeId);

  @Query("SELECT c FROM ShoppingCart c WHERE c.employee.id = :employeeId AND c.status = 'Assigned'")
  List<ShoppingCart> findAssignedOrdersByEmployeeId(@Param("employeeId") Long employeeId);

  @Query("SELECT sc FROM ShoppingCart sc LEFT JOIN FETCH sc.items WHERE sc.id = :id")
  Optional<ShoppingCart> findByIdWithItems(@Param("id") Long id);

  
}
