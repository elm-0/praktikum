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
    private Map<String, Employee> employees = new HashMap<>();
    private Map<String, Admin> admins = new HashMap<>();
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

    private void loadUsersFromDatabase() {
        adminRepository.findAll().forEach(admin -> admins.put(admin.getUsername(), admin));
        employeeRepository.findAll().forEach(employee -> employees.put(employee.getUsername(), employee));
        userRepository.findAll().forEach(user -> users.put(user.getUsername(), user));
    }


    public void register(String username, String password, String role) {
    if (userRepository.existsByUsername(username) || employeeRepository.existsByUsername(username) || adminRepository.existsByUsername(username)) {
        System.out.println("Потребител с това име вече съществува!");
        return;
    }

    switch (role.toLowerCase()) {
        case "admin":
            Admin newAdmin = new Admin(username, password, role); 
            adminRepository.save(newAdmin);
            break;
        case "employee":
            createAndSaveEmployee(username, password);
            break;
        case "user":
            User newUser = new User(username, password, "USER");
            userRepository.save(newUser);
            break;
        default:
            System.out.println("Невалидна роля! (трябва да е admin, employee или user)");
            return;
    }

    System.out.println("Успешна регистрация!");
}


    public User loginUser(String username, String password) {
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

    public Employee loginEmployee(String username, String password) {
        Employee employee = employees.get(username);
        if (employee == null) {
            System.out.println("Няма такъв потребител!");
            return null;
        }
        if (employee.getPassword() == null) {
            System.out.println("Потребителят няма зададена парола!");
            return null;
        }
        if (!employee.getPassword().equals(password)) {
            System.out.println("Грешна парола!");
            return null;
        }
        System.out.println("Успешен вход: " + employee.getUsername());
        return employee;
    }

    public Admin loginAdmin(String username, String password) {
        Admin admin = admins.get(username);
        if (admin == null) {
            System.out.println("Няма такъв потребител!");
            return null;
        }
        if (admin.getPassword() == null) {
            System.out.println("Потребителят няма зададена парола!");
            return null;
        }
        if (!admin.getPassword().equals(password)) {
            System.out.println("Грешна парола!");
            return null;
        }
        System.out.println("Успешен вход: " + admin.getUsername());
        return admin;
    }
    

    public void createAndSaveEmployee(String username, String password) {
    if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
        System.out.println("Username or password is null/empty. Aborting save.");
        return;
    }

    try {
        Employee newEmployee = new Employee();
        newEmployee.setUsername(username);
        newEmployee.setPassword(password);
        newEmployee.setEmployeeId("EMP" + username);  // You might want to ensure this ID is unique

        System.out.println("Before saving: username=" + newEmployee.getUsername() +
                           ", password=" + newEmployee.getPassword());

        // Save to DB
        employeeRepository.save(newEmployee);

        System.out.println("After saving: username=" + newEmployee.getUsername() +
                           ", password=" + newEmployee.getPassword());

        employees.put(username, newEmployee);
    } catch (Exception e) {
        System.out.println("Failed to save employee.");
        e.printStackTrace(); // This will show if a constraint or persistence error is thrown
    }
}
}
