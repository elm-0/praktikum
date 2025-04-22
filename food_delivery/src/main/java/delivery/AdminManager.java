package delivery;

import java.util.ArrayList;
import java.util.List;
import delivery.food_delivery.AdminAlreadyExistsException;
import delivery.food_delivery.AdminNotFoundException;
import delivery.food_delivery.AuthenticationException;


public class AdminManager {
    private List<Admin> admins;
    private List<User> users = new ArrayList<>();

    public AdminManager() { this.admins = new ArrayList<>(); }    

    public void addAdmin(Admin admin) throws AdminAlreadyExistsException {
        if(findByUsername(admin.getUsername())!=null){
            throw new AdminAlreadyExistsException(admin.getUsername());
        }
        admins.add(admin);
    }
    

    public boolean removeAdmin(String username) throws AdminNotFoundException{ 
        for(int i=0;i<admins.size();i++){
            if(admins.get(i).getUsername().equals(username)){
                admins.remove(i);
                return true;
            }else if(!(admins.get(i).getUsername().equals(username))){
                throw new AdminNotFoundException("Не е намерен админ с име: " + username);
            }
        }
        return false;
    }

    public Admin login(String username, String password) throws AuthenticationException {
        for( Admin admin:admins){
            if(admin.authenticate(username, password)){
                admin.addAction(("Успешно влизане"));
                return admin;
            }
        }
        throw new AuthenticationException("Грешно потребителско име или парола");
    }

    public Admin findByUsername(String username){
        for(Admin admin:admins){
            if(admin.getUsername().equals(username)){
                return admin;
            }
        }
        return null;
    }

    public List<Admin> getAllAdmins(){
        return admins;
    }

    public List<Admin> findByRole(String role){
        List<Admin> filteredAdmins = new ArrayList<>();
        for(Admin admin : admins){
            if(admin.getRole().equals(role)){
                filteredAdmins.add(admin);
            }
        }
        return filteredAdmins;
    }

    public void showActionHistory(String username) throws AdminNotFoundException{
        Admin admin=findByUsername(username);
        if(admin==null){
            throw new AdminNotFoundException("Не е намерена история за админ с име: " + username);
        }
        if(admin!=null){
            List<String> actions=admin.getAction();
            for(String action:actions){
                System.out.println(action);
            }
        }
    }

    public boolean updatePassword(String username, String newPassword) throws AdminNotFoundException{
        Admin admin=findByUsername(username);
        
        if(admin!=null){
            admin.setPassword(newPassword);
            admin.addAction("Паролата е променена");
            return true;
        }else{
            throw new AdminNotFoundException("Не е намерен админ с име: " + username);  
        }
    }

    public boolean updateUsername(String oldUsername, String newUsername) throws AdminNotFoundException{
        Admin admin=findByUsername(oldUsername);
        if(admin!=null){
            admin.setUsername(newUsername);
            admin.addAction(("Потребителското име е променено"));
            return true;
        }else{
            throw new AdminNotFoundException("Не е намерен админ с име: " + oldUsername);  
        }
    }

    public boolean updateRole(String username, String newRole) throws AdminNotFoundException{
        Admin admin=findByUsername(username);
        if(admin!=null){
            admin.setRole(newRole);
            admin.addAction(("Ролята е променена"));
            return true;
        }else{
            throw new AdminNotFoundException("Не е намерен админ с име: " + username);  
        }
    }

    
}
