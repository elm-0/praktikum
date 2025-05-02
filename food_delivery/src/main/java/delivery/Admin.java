package delivery;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Admin extends User {
  
    private List<String> actionHistory;
    public enum Role {
        SUPER_ADMIN,
        ADMIN,  
        MODERATOR,
        SUPPORT
    }
    public Admin(String username, String password) {
        super(username, password, "ADMIN"); 
        this.actionHistory = new ArrayList<>();
    }

    @Override
    public void setUsername(String username) { 
        super.setUsername(username); 
        addAction(  "Името е променено на " + username);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        addAction("Паролата е променена");
    }
    
    @Override
    public void setRole(String role) {
        super.setRole(role);
        addAction(("Ролята е променена на  " + role));
        }

    public boolean authenticate(String username, String password) {
        return getUsername().equals(username) && getPassword().equals(password);
    }
    
    public void addAction(String action) {
         actionHistory.add(LocalDateTime.now() + ": " + action); 
        }  

    public List<String> getAction() {
        return actionHistory; 
    }


    
}                         