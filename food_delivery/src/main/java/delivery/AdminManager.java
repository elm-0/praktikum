package delivery;

import java.util.ArrayList;
import java.util.List;
import delivery.food_delivery.*;

public class AdminManager {
    private List<Admin> admins;
    private List<User> users;
    private List<Employee> employees;
    private Admin currentAdmin;

    public AdminManager() { 
        this.admins = new ArrayList<>();
        this.users = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.currentAdmin = null;
    } 

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public boolean isLoggedIn(){
        return currentAdmin!=null;
    }

    public Admin login(String username, String password) throws AuthenticationException {
        Admin admin = findByUsername(username);
        if (admin != null && admin.authenticate(username, password)) {
            admin.addAction("Успешно влизане");
            this.currentAdmin = admin;
            return admin;
        }
        throw new AuthenticationException("Грешно потребителско име или парола");
    }

    public void logout() throws NoAdminLoggedInException {
        if (currentAdmin == null) {
            throw new NoAdminLoggedInException("Няма логнат администратор в системата");
        }
        String username = currentAdmin.getUsername();
        currentAdmin.addAction("Успешно излизане от системата");
        this.currentAdmin = null;
        System.out.println("Администратор " + username + " излезе от системата.");
    }
   
    public void addAdmin(Admin admin) throws AdminAlreadyExistsException {
        if (admin == null) {
            throw new IllegalArgumentException("Администраторът не може да бъде null");
        }
        if (findByUsername(admin.getUsername()) != null) {
            throw new AdminAlreadyExistsException(admin.getUsername());
        }
        admins.add(admin);
        admin.addAction("Добавен е нов администратор с име: " + admin.getUsername());
    }
    

    public boolean removeAdmin(String username) throws AdminNotFoundException {
        Admin admin = findByUsername(username);
        if (admin == null) {
            throw new AdminNotFoundException("Не е намерен админ с име: " + username);
        }
        admin.addAction("Премахнат е администратор с име: " + username);
        return admins.remove(admin);
    }

    public List<Admin> getAllAdmins(){
        return admins;
    }
    //find методи
    public Admin findByUsername(String username){
        for(Admin admin:admins){
            if(admin.getUsername().equals(username)){
                return admin;
            }
        }
        return null;
    }

    public List<Admin> findByRole(String role) {
        List<Admin> filteredAdmins = new ArrayList<>();
        for (Admin admin : admins) {
            if (admin.getRole().equals(role)) {
                filteredAdmins.add(admin);
            }
        }
        return filteredAdmins;
    }
    //admin history метод
    public void showActionHistory(String username) throws AdminNotFoundException{
        Admin admin=findByUsername(username);
        if(admin==null){
            throw new AdminNotFoundException("Не е намерена история за админ с име: " + username);
        }
        if(admin!=null){
            List<String> actions=admin.getActionHistory();
            for(String action:actions){
                System.out.println(action);
            }
        }
    }
    // update методи
    public boolean updatePassword(String username, String newPassword) 
            throws AdminNotFoundException, IllegalArgumentException {
        Admin admin = findByUsername(username);
        if (admin == null) {
            throw new AdminNotFoundException("Не е намерен админ с име: " + username);
        }
        admin.setPassword(newPassword);
        admin.addAction("Паролата е променена");
        return true;
    }

    public boolean updateUsername(String oldUsername, String newUsername) 
            throws AdminNotFoundException, AdminAlreadyExistsException {
        Admin admin = findByUsername(oldUsername);
        if (admin == null) {
            throw new AdminNotFoundException("Не е намерен админ с име: " + oldUsername);
        }
        if (findByUsername(newUsername) != null && !oldUsername.equals(newUsername)) {
            throw new AdminAlreadyExistsException(newUsername);
        }
        admin.setUsername(newUsername);
        admin.addAction("Потребителското име е променено на " + newUsername);
        return true;
    }

    public boolean updateRole(String username, String newRole) throws AdminNotFoundException {
        Admin admin = findByUsername(username);
        if (admin == null) {
            throw new AdminNotFoundException("Не е намерен админ с име: " + username);
        }
        admin.setRole(newRole);
        admin.addAction("Ролята е променена на " + newRole);
        return true;
    }

    //търсене на служител по ID
    public Employee findEmployeeById(String employeeId) throws EmployeeNotFoundException {
        if (employeeId == null || employeeId.isEmpty()) {
            throw new IllegalArgumentException("ID-то на служителя не може да бъде null или празно");
        }
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Не е намерен служител с ID: " + employeeId);
    }

    // добавяне на нов служител
    public void addEmployee(Employee employee, Admin admin) 
        throws EmployeeAlreadyExistsException, IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Служителят не може да бъде null");
        }
        try {
            findEmployeeById(employee.getEmployeeId());
            throw new EmployeeAlreadyExistsException("Служител с ID " + employee.getEmployeeId() + " вече съществува");
        } catch (EmployeeNotFoundException e) {
        }
        employees.add(employee);
        admin.addAction("Добавен е нов служител с ID: " + employee.getEmployeeId());
    }

    // премахване на служител
    public void removeEmployee( String employeeId, Admin admin) throws EmployeeNotFoundException{
        Employee employee=findEmployeeById(employeeId);
        if(employee==null){
            throw new EmployeeNotFoundException(employeeId);
        }
        employees.remove(employee);
        admin.addAction("Премахнат е служител с ID: " + employeeId);
    }

    //промяна на статус на служител(активен/неактивен)
    public void setEmployeeActive(String employeeId,boolean isAvailable, Admin admin) throws EmployeeNotFoundException{
        Employee employee=findEmployeeById(employeeId);
        if(employee==null){
            throw new EmployeeNotFoundException(employeeId);
        }
        employee.setAvailable(isAvailable);
        admin.addAction("Променен е статусът на служител с ID: " + employeeId + " на " + (isAvailable ? "активен" : "неактивен"));
    } 

    //списък с всички служители
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}