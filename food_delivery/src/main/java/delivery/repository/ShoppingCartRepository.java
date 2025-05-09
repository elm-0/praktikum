package delivery.repository;

import delivery.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
  @Query("SELECT c FROM ShoppingCart c WHERE c.employee.id = :employeeId")
  List<ShoppingCart> findOrderHistoryByEmployeeId(@Param("employeeId") Long employeeId);

  @Query("SELECT c FROM ShoppingCart c WHERE c.employee.id = :employeeId AND c.status = 'Assigned'")
  List<ShoppingCart> findAssignedOrdersByEmployeeId(@Param("employeeId") Long employeeId);
}
