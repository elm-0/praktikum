package delivery;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Admin {
    private String username;
    private String password;
    private String role;
    private List<String> actionHistory;
    private enum Role {
        SUPER_ADMIN,
        ADMIN,
        MODERATOR,
        SUPPORT
    }

    public boolean isSuperAdmin(){
        return this.role== Role.SUPER_ADMIN.toString();
    }
    public boolean isAdmin(){
        return this.role== Role.ADMIN.toString();
    }
    public boolean isModerator(){
        return this.role== Role.MODERATOR.toString();
    }
    public boolean isSupport(){
        return this.role== Role.SUPPORT.toString();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Грешка при хеширането на паролата", e);
        }
    }

    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = hashPassword(password);
        this.role = role;
        this.actionHistory = new ArrayList<>();
    }

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getRole() { return this.role; }

    public void setUsername(String username) { 
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Името не може да бъде празно");
        }
        this.username=username; 
        addAction(  "Името е променено на " + username);
    }
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty() || password.length() < 8) {
            throw new IllegalArgumentException("Паролата трябва да е дълга поне 8 символа и не може да бъде празна");
        }
        this.password=hashPassword(password); 
        addAction("Паролата е променена");
    }
    public void setRole(String role) {
         this.role=role; 
        addAction(("Ролята е променена на  " + role));
        }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    public void addAction(String action) {
         actionHistory.add(LocalDateTime.now() + ": " + action); 
        }  

    public List<String> getActionHistory() { return actionHistory; }


    
}   