package delivery;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Admin extends User {

    private List<String> actionHistory;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MODERATOR,
        SUPPORT
    }

    public Admin(String username, String password) {
        super(username, hashPassword(password), "ADMIN");
        this.actionHistory = new ArrayList<>();
    }

    public Admin(String username, String password, String role) {
        super(username, hashPassword(password), role);
        this.actionHistory = new ArrayList<>();
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

    @Override
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Името не може да бъде празно");
        }
        super.setUsername(username);
        addAction("Името е променено на " + username);
    }

    @Override
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty() || password.length() < 8) {
            throw new IllegalArgumentException("Паролата трябва да е дълга поне 8 символа и не може да бъде празна");
        }
        super.setPassword(hashPassword(password));
        addAction("Паролата е променена");
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
