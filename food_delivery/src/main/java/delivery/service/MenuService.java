package delivery.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import delivery.Dish;
import delivery.ShoppingCart;

@Service
public class MenuService {

    private final ShoppingCartService cartService;
    private final DishService dishService;

    
    public MenuService(ShoppingCartService cartService, DishService dishService) {
        this.cartService = cartService;
        this.dishService = dishService;
    }

    
    public void openMenu() {
        Scanner scanner = new Scanner(System.in);

        ShoppingCart cart = cartService.createCart();

        List<Dish> menu = dishService.getAllDishes(); 

        while (true) {
            System.out.println("Menu:");
            for (int i = 0; i < menu.size(); i++) {
                Dish d = menu.get(i);
                System.out.printf("%d - %s ($%.2f)\n", i + 1, d.getName(), d.getPrice());
            }

            System.out.println("\nChoose an option:");
            System.out.println("1 - Add item to cart");
            System.out.println("2 - Remove item from cart");
            System.out.println("3 - View total and finish");
            System.out.println("4 - Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.print("Enter item number to add: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < menu.size()) {
                        cartService.addItemToCart(cart.getId(), menu.get(idx));
                        System.out.println("Added.");
                    }
                }
                case "2" -> {
                    System.out.print("Enter item number to remove: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < menu.size()) {
                        cartService.removeItemFromCart(cart.getId(), menu.get(idx));
                        System.out.println("Removed.");
                    }
                }
                case "3" -> {
                    double total = cartService.calculateCartTotal(cart.getId());
                    System.out.println("Total: $" + total);
                    System.out.println("Order sent! Thank you.");
                    return;
                }
                case "4" -> {
                    System.out.println("Exiting.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
            scanner.close();
        }
    }
}
