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
}
