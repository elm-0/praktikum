package delivery;

import java.util.ArrayList;
import java.util.List;
import delivery.food_delivery.*;

public class AdminManager {
    private List<Admin> admins;
    private List<User> users = new ArrayList<>();
    private Admin currentAdmin;

    public AdminManager() { 
        this.admins = new ArrayList<>();
        this.users = new ArrayList<>();
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
    }
    

    public boolean removeAdmin(String username) throws AdminNotFoundException {
        Admin admin = findByUsername(username);
        if (admin == null) {
            throw new AdminNotFoundException("Не е намерен админ с име: " + username);
        }
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
}