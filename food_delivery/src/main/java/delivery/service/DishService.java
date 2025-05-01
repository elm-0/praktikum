package delivery.service;

import delivery.Dish;
import delivery.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish findDishById(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public void removeDish(Long id) {
        dishRepository.deleteById(id);
    }

    public void updateDishAvailability(Long id, boolean available) {
        Dish dish = findDishById(id);
        dish.setAvailable(available);
        dishRepository.save(dish);
    }
}
