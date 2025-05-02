package delivery.food_delivery;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import delivery.Admin;
import delivery.Employee;
import delivery.User;
import delivery.service.MenuService;


@SpringBootApplication
public class FoodDeliveryApplication implements CommandLineRunner {

	@Autowired
	private AuthenticationService authenticationService;
    private MenuService menuService; 

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryApplication.class, args);
	}

	@Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        User currentUser = null;
        while (currentUser == null) {
            currentUser = login(scanner);  
        }

        if (currentUser instanceof Admin) {
            openAdminPanel();
        } else if (currentUser instanceof Employee) {
            openEmployeeMenu();
        } else if (currentUser instanceof User) {
    		menuService.openMenu();
        } else {
            System.out.println("Unknown role!");
        }
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

		

	public static void openAdminPanel(){
		System.out.println("Admin panel open");
	}

	public static void openEmployeeMenu(){
		System.out.println("Employee menu open");
	}

}



