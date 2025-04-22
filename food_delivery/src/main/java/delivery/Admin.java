package delivery;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Admin {
    private String username;
    private String password;
    private String role;
    private List<String> actionHistory;
    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MODERATOR,
        SUPPORT
    }
    public Admin(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role.toString();
        this.actionHistory = new ArrayList<>();
    }

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getRole() { return this.role.toString(); }

    public void setUsername(String username) { 
        this.username=username; 
        addAction(  "Името е променено на " + username);
    }
    public void setPassword(String password) {
        this.password=password; 
        addAction("Паролата е променена");
    }
    public void setRole(String role) {
         this.role=role.toString(); 
        addAction(("Ролята е променена на  " + role));
        }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    public void addAction(String action) {
         actionHistory.add(LocalDateTime.now() + ": " + action); 
        }  

    public List<String> getAction() { return actionHistory; }


    
}                         