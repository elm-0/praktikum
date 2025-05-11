package delivery;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import delivery.food_delivery.AuthenticationService;
import delivery.repository.DishRepository;
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
        String chosenRole = "";

        while (true) {
            System.out.println("\nChoose role:");
            System.out.println("1. User");
            System.out.println("2. Employee");
            System.out.println("3. Admin");

            //String choice = scanner.nextLine();
            if (!scanner.hasNextLine()) {
                System.out.println("No input available. Exiting...");
                return;
            }
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> chosenRole = "user";
                case "2" -> chosenRole = "employee";
                case "3" -> chosenRole = "admin";
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }

            while (!"".equals(chosenRole)) {
                printMenuOptions();
                if (!scanner.hasNextLine()) {
                System.out.println("No input detected. Exiting...");
                return;
            }
            String choiceLogin = scanner.nextLine();

                switch (choiceLogin) {
                    case "1" -> {
                        switch (chosenRole) {
                            case "user" -> {
                                delivery.User currentUser = null;
                                while (currentUser == null) {
                                //    if (!scanner.hasNextLine()) {
                                //    System.out.println("No input detected. Exiting...");
                                //    return;
                                //}
                                    currentUser = loginUser(scanner);
                                }
                                menuService.openMenu(currentUser.getId());
                            }
                            case "employee" -> {
                                delivery.Employee currentEmployee = null;
                                while (currentEmployee == null) {
                                    if (!scanner.hasNextLine()) {
                                    System.out.println("No input detected. Exiting...");
                                    return;
                                }
                                    currentEmployee = loginEmployee(scanner);
                                }
                                openEmployeeMenu(scanner, currentEmployee);
                            }
                            case "admin" -> {
                                delivery.Admin currentAdmin = null;
                                while (currentAdmin == null) {
                                     if (!scanner.hasNextLine()) {
                                    System.out.println("No input detected. Exiting...");
                                    return;
                                }
                                    currentAdmin = loginAdmin(scanner);
                                }
                                currentAdmin.useAdminManager();
                            }
                        }
                    }

                    case "2" -> {
                     if (!scanner.hasNextLine()) {
                        System.out.println("No input detected. Exiting...");
                        return;
                    } register(scanner);
                }

                    case "3" -> {
                        System.out.println("Exiting...");
                        int exitCode = SpringApplication.exit(context, () -> 0);
                        System.exit(exitCode);
                    }

                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    //Оставям го ако Калин иска да види нещо!

	// @Override
    // public void run(String... args) {
    //     Scanner scanner = new Scanner(System.in);
    //     String chosenRole = "";

    //     while (true){
    //         System.out.println("\nChoose role:");
    //         System.out.println("1. User");
    //         System.out.println("2. Employee");
    //         System.out.println("3. Admin");

    //         String choice = scanner.nextLine();
    //         switch (choice) {
    //             case "1" -> chosenRole = "user";
    
    //             case "2" -> chosenRole = "employee";
    
    //             case "3" -> chosenRole = "admin";
    //             default -> {
    //                 System.out.println("Invalid choice. Please try again.");
    //             }
    //         }
                    
    //                 while(!"".equals(chosenRole)){
    //                     switch (chosenRole) {
    //                         case "user" -> {
    //                             printMenuOptions();
    //                             String choiceLogin = scanner.nextLine();
    //                             switch (choiceLogin) {
    //                                 case "1":
    //                                     delivery.User currentUser = null;
    //                                     while (currentUser == null) {
    //                                         currentUser = loginUser(scanner);}
    //                                         menuService.openMenu(currentUser.getId());
    //                                 case "2":
    //                                     register(scanner);
                                        
    //                                 case "3":
    //                                     System.out.println("Exiting...");
    //                                     int exitCode = SpringApplication.exit(context, () -> 0);
    //                                     System.exit(exitCode);
    //                                 default:
    //                                     System.out.println("Invalid choice. Please try again.");
    //                             }
    //                         }
    //                         case "employee" -> {
    //                             printMenuOptions();
    //                             String choiceLogin = scanner.nextLine();
    //                             switch (choiceLogin) {
    //                                 case "1":
    //                                     delivery.Employee currentEmployee = null;
    //                                     while (currentEmployee == null) {
    //                                         currentEmployee = loginEmployee(scanner);}
    //                                         openEmployeeMenu(scanner, currentEmployee); 
    //                                 case "2":
    //                                     register(scanner);
                                        
    //                                 case "3":
    //                                     System.out.println("Exiting...");
    //                                     int exitCode = SpringApplication.exit(context, () -> 0);
    //                                     System.exit(exitCode);
    //                                 default:
    //                                     System.out.println("Invalid choice. Please try again.");
    //                             }
    //                         }
    //                         case"admin" -> {
    //                             printMenuOptions();
    //                             String choiceLogin = scanner.nextLine();
    //                             switch (choiceLogin) {
    //                                 case "1":
    //                                     delivery.Admin currentAdmin = null;
    //                                     while (currentAdmin == null) {
    //                                         currentAdmin = loginAdmin(scanner);}
    //                                         currentAdmin.useAdminManager();
    //                                 case "2":
    //                                     register(scanner);
                                        
    //                                 case "3":
    //                                     System.out.println("Exiting...");
    //                                     int exitCode = SpringApplication.exit(context, () -> 0);
    //                                     System.exit(exitCode);
    //                                 default:
    //                                     System.out.println("Invalid choice. Please try again.");
    //                             }
    //                         }
    //                         default -> System.out.println("Invalid choice. Please try again.");
                            
    //                     }
    //                 }   
    //     }


    private void register(Scanner scanner) {
        System.out.println("Choose a username:");
        String username = scanner.nextLine();
        System.out.println("Choose a password:");
        String password = scanner.nextLine();
        System.out.println("Enter role (admin, employee, user):");
        String role = scanner.nextLine();
    
        authenticationService.register(username, password, role);
    }
    

    private delivery.User loginUser(Scanner scanner) {
        System.out.println("Input username:");
        String username = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();

        delivery.User user = authenticationService.loginUser(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getUsername());
            return user;
        } else {
            System.out.println("Login unsuccessful. Please try again.");
            return null;
        }
    }

    private delivery.Employee loginEmployee(Scanner scanner) {
        System.out.println("Input username:");
        String username = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();

        delivery.Employee employee = authenticationService.loginEmployee(username, password);
        if (employee != null) {
            System.out.println("Welcome, " + employee.getUsername());
            return employee;
        } else {
            System.out.println("Login unsuccessful. Please try again.");
            return null;
        }
    }
    private delivery.Admin loginAdmin(Scanner scanner) {
        System.out.println("Input username:");
        String username = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();

        delivery.Admin admin = authenticationService.loginAdmin(username, password);
        if (admin != null) {
            System.out.println("Welcome, " + admin.getUsername());
            return admin;
        } else {
            System.out.println("Login unsuccessful. Please try again.");
            return null;
        }
    }

    public void printMenuOptions(){
        System.out.println("\nChoose an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    
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


    //генерира автоматично храни в базата за по-лесно тестване
    @Bean
    public CommandLineRunner seedDishes(DishRepository dishRepository) {
        return args -> {
        List<Dish> initialDishes = List.of(
            new Dish("Pizza Margherita", 8.99, true),
            new Dish("Spaghetti Carbonara",10.99 , true),
            new Dish("Caesar Salad",6.49, true),
            new Dish("Burger",9.99, true),
            new Dish("French Fries",4.99, true),
            new Dish("Caprese Salad",6.49, true),
            new Dish("Spaghetti Bolognese",10.99, true)
        );

        for (Dish dish : initialDishes) {
            if (!dishRepository.existsByName(dish.getName())) {
                dishRepository.save(dish);
                System.out.println("Added: " + dish.getName());
            }
        }
    };
}
    
}



