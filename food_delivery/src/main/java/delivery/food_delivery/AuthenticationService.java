package delivery.food_delivery;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import delivery.Admin;
import delivery.Employee;
import delivery.User;
import delivery.repository.AdminRepository;
import delivery.repository.EmployeeRepository;
import delivery.repository.UserRepository;

@Service
public class AuthenticationService {

    private Map<String, User> users = new HashMap<>();
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;

        loadUsersFromDatabase();
    }

    /* 
    public AuthenticationService() { //тестови потребители
        //users.put("admin", new Admin("admin", "admin"));
        //users.put("employee", new Employee("employee", "employee", "EMP001"));
        //users.put("user", new User("user", "user", "USER"));

        Admin testAdmin= new Admin("admin", "admin");
        adminRepository.save(testAdmin);
        users.put("admin", testAdmin);

        Employee testEmployee= new Employee("employee", "employee", "EMP001");
        employeeRepository.save(testEmployee);
        users.put("employee", testEmployee);

        User testUser= new User("user", "user", "USER");
        userRepository.save(testUser);
        users.put("user", testUser);
    }
    */

    private void loadUsersFromDatabase() {
        adminRepository.findAll().forEach(admin -> users.put(admin.getUsername(), admin));
        employeeRepository.findAll().forEach(employee -> users.put(employee.getUsername(), employee));
        userRepository.findAll().forEach(user -> users.put(user.getUsername(), user));
    }


    public void register(String username, String password, String role) {
        if (users.containsKey(username)) {
            System.out.println("Потребител с това име вече съществува!");
            return;
        }
        

        switch (role.toLowerCase()) {
            case "admin":
                Admin newAdmin = new Admin(username, password);
                adminRepository.save(newAdmin); 
                users.put(username, newAdmin);
                break;
            case "employee":
                Employee newEmployee = new Employee(username, password, "EMP002"); 
                users.put(username, newEmployee);
                employeeRepository.save(newEmployee);  
                users.put(username, newEmployee);
                break;
            case "user":
                User newUser;
                newUser = new User(username, password, "USER");
                users.put(username, newUser);
                userRepository.save(newUser);
                users.put(username, newUser);
                break;
            default:
                System.out.println("Невалидна роля! (трябва да е admin, employee или user)");
                return;

        }

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
