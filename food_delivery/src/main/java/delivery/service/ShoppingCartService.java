package delivery.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import delivery.Dish;
import delivery.Employee;
import delivery.ShoppingCart;
import delivery.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart createCart(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null when creating a shopping cart.");
        }

        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userId);
        cart.setStatus("ACTIVE");
        cart.setCreatedAt(LocalDateTime.now());
        return shoppingCartRepository.save(cart);
    }    

    public ShoppingCart findCartById(Long id) {
        return shoppingCartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }


    @Transactional
    public void addItemToCart(Long cartId, Dish dish) {
         ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.addItem(dish);
        shoppingCartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Dish dish) {
         ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        //Dish dish = dishRepository.findById(itemId)
         //       .orElseThrow(() -> new RuntimeException("Item not found"));
        cart.removeItem(dish);
        shoppingCartRepository.save(cart);
    }

    @Transactional
    public double calculateCartTotal(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findByIdWithItems(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getTotal();
    }

   // public void clearCart(Long cartId) {
    //    ShoppingCart cart = findCartById(cartId);
    //    cart.clearCart();
    //    shoppingCartRepository.save(cart);
    //}
    public void clearCart(Long cartId) {
        ShoppingCart cart = findCartByIdWithItems(cartId);
        cart.getItems().clear();
        shoppingCartRepository.save(cart);
    }


    public void assignOrderToEmployee(Long orderId, Employee employee) {
        ShoppingCart cart = findCartById(orderId);
        if (cart == null) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
        if (cart.getEmployee() != null) {
            throw new IllegalStateException("Order with ID " + orderId + " is already assigned.");
        }
        if (!employee.isAvailable()) {
            throw new IllegalStateException("Employee " + employee.getEmployeeId() + " is not available to take more orders.");
        }
        cart.setEmployee(employee);
        cart.setStatus("Assigned");
        employee.acceptOrder(orderId.toString());
        shoppingCartRepository.save(cart);
    }

    public List<ShoppingCart> getAssignedOrdersToEmployee(Employee employee) {
        return shoppingCartRepository.findAssignedOrdersByEmployeeId(employee.getId());
    }

    public void updateOrderStatus(Long orderId, String newStatus) {
        ShoppingCart cart = findCartById(orderId);
        if (cart == null) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
        cart.setStatus(newStatus);
        shoppingCartRepository.save(cart);
    }

    public List<ShoppingCart> getEmployeeOrderHistory(Employee employee) {
        return shoppingCartRepository.findOrderHistoryByEmployeeId(employee.getId());
    }

    public ShoppingCart findCartByIdWithItems(Long id) {
    return shoppingCartRepository.findByIdWithItems(id)
        .orElseThrow(() -> new RuntimeException("Cart not found"));
}

}
