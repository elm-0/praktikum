package delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import delivery.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByAvailableTrue();
    boolean existsByName(String name);
}
