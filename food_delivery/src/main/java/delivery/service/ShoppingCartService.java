package delivery.service;

import delivery.Dish;
import delivery.ShoppingCart;
import delivery.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart createCart() {
        return shoppingCartRepository.save(new ShoppingCart());
    }

    public ShoppingCart findCartById(Long id) {
        return shoppingCartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void addItemToCart(Long cartId, Dish dish) {
        ShoppingCart cart = findCartById(cartId);
        cart.addItem(dish);
        shoppingCartRepository.save(cart);
    }

    public void removeItemFromCart(Long cartId, Dish dish) {
        ShoppingCart cart = findCartById(cartId);
        cart.removeItem(dish);
        shoppingCartRepository.save(cart);
    }

    public double calculateCartTotal(Long cartId) {
        ShoppingCart cart = findCartById(cartId);
        return cart.getTotal();
    }

    public void clearCart(Long cartId) {
        ShoppingCart cart = findCartById(cartId);
        cart.clearCart();
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
        cart.setEmployee(employee);
        cart.setStatus("Assigned");
        shoppingCartRepository.save(cart);
    }

    public List<ShoppingCart> getAssignedOrdersToEmployee(Employee employee) {
        return shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getEmployee() != null && cart.getEmployee().getId().equals(employee.getId()) && "Assigned".equals(cart.getStatus()))
                .collect(Collectors.toList());
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
        return shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getEmployee() != null && cart.getEmployee().getId().equals(employee.getId()))
                .collect(Collectors.toList());
    }
}
