package delivery;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import delivery.food_delivery.AuthenticationService;
import delivery.service.MenuService;
import delivery.service.ShoppingCartService;


@SpringBootApplication
public class FoodDeliveryApplication implements CommandLineRunner {

	private AuthenticationService authenticationService;
    private MenuService menuService; 
    private final ApplicationContext context;
    private final ShoppingCartService shoppingCartService;
    
    public FoodDeliveryApplication(AuthenticationService authenticationService, MenuService menuService, ApplicationContext context, ShoppingCartService shoppingCartService) {
        this.authenticationService = authenticationService;
        this.menuService = menuService;
        this.context = context;
        this.shoppingCartService = shoppingCartService;
    }

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryApplication.class, args);
	}

	@Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
    
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    User currentUser = null;
                    while (currentUser == null) {
                        currentUser = login(scanner);
                    }
    
                    if (currentUser instanceof Admin) {
                        ((Admin) currentUser).useAdminManager();
                    } else if (currentUser instanceof Employee) {
                        openEmployeeMenu(scanner, (Employee) currentUser);
                    } else if (currentUser instanceof User) {
                        menuService.openMenu(currentUser.getId());
                    } else {
                        System.out.println("Unknown role!");
                    }
                    break;
    
                case "2":
                    register(scanner);
                    break;
    
                case "3":
                    System.out.println("Exiting...");
                    int exitCode = SpringApplication.exit(context, () -> 0);
                    System.exit(exitCode);
                    break;
            
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void register(Scanner scanner) {
        System.out.println("Choose a username:");
        String username = scanner.nextLine();
        System.out.println("Choose a password:");
        String password = scanner.nextLine();
        System.out.println("Enter role (admin, employee, user):");
        String role = scanner.nextLine();
    
        authenticationService.register(username, password, role);
    }
    

    private User login(Scanner scanner) {
        System.out.println("Input username:");
        String username = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();

        User user = authenticationService.login(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getUsername());
            return user;
        } else {
            System.out.println("Login unsuccessful. Please try again.");
            return null;
        }
    }




    public void openEmployeeMenu(Scanner scanner, Employee currentEmployee) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. Accept Order");
            System.out.println("2. View Accepted Orders");
            System.out.println("3. Update Order Status");
            System.out.println("4. View Order History");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    acceptOrder(scanner, currentEmployee);
                    break;
                case 2:
                    viewAcceptedOrders(currentEmployee);
                    break;
                case 3:
                    updateOrderStatus(scanner);
                    break;
                case 4:
                    viewOrderHistory(currentEmployee);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void acceptOrder(Scanner scanner, Employee employee) {
        System.out.print("Enter order ID to accept: ");
        Long orderId = scanner.nextLong();
        scanner.nextLine();
        shoppingCartService.assignOrderToEmployee(orderId, employee);
        System.out.println("Order accepted.");
    }

    private void viewAcceptedOrders(Employee employee) {
        List<ShoppingCart> acceptedOrders = shoppingCartService.getAssignedOrdersToEmployee(employee);
        if (acceptedOrders.isEmpty()) {
            System.out.println("No accepted orders.");
        } else {
            System.out.println("\nAccepted Orders:");
            acceptedOrders.forEach(System.out::println);
        }
    }

    private void updateOrderStatus(Scanner scanner) {
        System.out.print("Enter order ID to update: ");
        Long orderId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Enter new order status: ");
        String newStatus = scanner.nextLine();
        shoppingCartService.updateOrderStatus(orderId, newStatus);
        System.out.println("Order status updated.");
    }

    private void viewOrderHistory(Employee employee) {
        List<ShoppingCart> orderHistory = shoppingCartService.getEmployeeOrderHistory(employee); // Променен метод
        if (orderHistory.isEmpty()) {
            System.out.println("No order history.");
        } else {
            System.out.println("\nOrder History:");
            orderHistory.forEach(System.out::println);
        }
    }
}



