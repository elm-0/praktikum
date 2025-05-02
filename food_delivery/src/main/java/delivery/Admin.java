package delivery;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends User {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_actions", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "action")
    private List<String> actionHistory = new ArrayList<>();

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MODERATOR,
        SUPPORT
    }

    // public Admin() {  //davashe greshka i go slojih kato komentar
    //    super();
    // }

    public Admin(String username, String password) {
        super(username, hashPassword(password), Role.ADMIN.toString());
    }

    public Admin(String username, String password, String role) {
        super(username, hashPassword(password), role);
    }

    public boolean isSuperAdmin() {
        return getRole().equals(Role.SUPER_ADMIN.toString());
    }

    public boolean isAdmin() {
        return getRole().equals(Role.ADMIN.toString());
    }

    public boolean isModerator() {
        return getRole().equals(Role.MODERATOR.toString());
    }

    public boolean isSupport() {
        return getRole().equals(Role.SUPPORT.toString());
    }

    private static String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Грешка при хеширане", e);
        }
    }

    @Override
    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Паролата трябва да е минимум 8 символа.");
        }
        super.setPassword(hashPassword(password));
        addAction("Паролата е променена");
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        addAction("Името е променено на " + username);
    }

    @Override
    public void setRole(String role) {
        super.setRole(role);
        addAction("Ролята е променена на " + role);
    }

    public boolean authenticate(String username, String password) {
        return getUsername().equals(username) && getPassword().equals(hashPassword(password));
    }

    public void addAction(String action) {
        actionHistory.add(LocalDateTime.now() + ": " + action);
    }

    public List<String> getActionHistory() {
        return actionHistory;
    }
}
