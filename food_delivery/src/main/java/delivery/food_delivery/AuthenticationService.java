package delivery.food_delivery;

import delivery.Admin;
import delivery.Employee;
import delivery.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private Map<String, User> users = new HashMap<>();

    public AuthenticationService() { //тестови потребители
        users.put("admin", new Admin("admin", "admin"));
        users.put("employee", new Employee("employee", "employee"));
        users.put("user", new User("user", "user", "USER"));
    }


    public void register(String username, String password, String role) {
        if (users.containsKey(username)) {
            System.out.println("Потребител с това име вече съществува!");
            return;
        }

        User newUser;
        switch (role.toLowerCase()) {
            case "admin":
                newUser = new Admin(username, password);
                break;
            case "employee":
                newUser = new Employee(username, password);
                break;
            case "user":
                newUser = new User(username, password, "USER");
                break;
            default:
                System.out.println("Невалидна роля! (трябва да е admin, employee или user)");
                return;
        }

        users.put(username, newUser);
        System.out.println("Успешна регистрация!");
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            System.out.println("Няма такъв потребител!");
            return null;
        }
        if (user.getPassword() == null) {
            System.out.println("Потребителят няма зададена парола!");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Грешна парола!");
            return null;
        }
        System.out.println("Успешен вход: " + user.getUsername());
        return user;
    }
    
}
