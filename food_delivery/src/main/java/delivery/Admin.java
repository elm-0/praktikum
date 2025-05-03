package delivery;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.atn.AmbiguityInfo;

import delivery.food_delivery.*;
import java.util.Scanner;

@Entity
public class Admin extends User{

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_actions", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "action")
    private List<String> actionHistory = new ArrayList<>();

    AdminManager adminManager = new AdminManager();


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
        return new ArrayList<>(actionHistory);
    }

    void useAdminManager() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Въведете потребителско име за логин: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Въведете парола за логин: ");
            String loginPassword = scanner.nextLine();
            adminManager.login(loginUsername, loginPassword);
        } catch (AuthenticationException e) {
            System.out.println("Грешка при логин: " + e.getMessage());
        }

        try {
            System.out.println("\nНатиснете Enter за изход...");
            scanner.nextLine();
            adminManager.logout();
        } catch (NoAdminLoggedInException e) {
            System.out.println("Грешка при изход: " + e.getMessage());
        }

        try {
            System.out.print("\nВъведете ново потребителско име за администратор: ");
            String newAdminUsername = scanner.nextLine();
            System.out.print("Въведете парола за новия администратор: ");
            String newAdminPassword = scanner.nextLine();
            System.out.print("Въведете роля (SUPER_ADMIN, ADMIN, MODERATOR, SUPPORT): ");
            String newAdminRole = scanner.nextLine();

            Admin newAdmin = new Admin(newAdminUsername, newAdminPassword, newAdminRole);
            adminManager.addAdmin(newAdmin);
            System.out.println("Администраторът е добавен успешно!");
        } catch (AdminAlreadyExistsException | IllegalArgumentException e) {
            System.out.println("Грешка при добавяне: " + e.getMessage());
        }

        try {
            System.out.print("\nВъведете потребителско име на администратор за премахване: ");
            String adminToRemove = scanner.nextLine();
            adminManager.removeAdmin(adminToRemove);
            System.out.println("Администраторът е премахнат успешно!");
        } catch (AdminNotFoundException e) {
            System.out.println("Грешка при премахване: " + e.getMessage());
        }

        System.out.println("\nВсички администратори:");
        adminManager.getAllAdmins().forEach(admin -> System.out.println(admin.getUsername() + " - " + admin.getRole()));

        try {
            System.out.print("\nВъведете потребителско име за смяна на парола: ");
            String userForPassword = scanner.nextLine();
            System.out.print("Въведете нова парола: ");
            String newPassword = scanner.nextLine();
            adminManager.updatePassword(userForPassword, newPassword);
            System.out.println("Паролата е променена успешно!");
        } catch (AdminNotFoundException | IllegalArgumentException e) {
            System.out.println("Грешка при смяна на парола: " + e.getMessage());
        }

        try {
            System.out.print("\nВъведете текущо потребителско име: ");
            String oldUsername = scanner.nextLine();
            System.out.print("Въведете ново потребителско име: ");
            String newUsername = scanner.nextLine();
            adminManager.updateUsername(oldUsername, newUsername);
            System.out.println("Потребителското име е променено успешно!");
        } catch (AdminNotFoundException | AdminAlreadyExistsException | IllegalArgumentException e) {
            System.out.println("Грешка при смяна на име: " + e.getMessage());
        }

        try {
            System.out.print("\nВъведете потребителско име за смяна на роля: ");
            String userForRole = scanner.nextLine();
            System.out.print("Въведете нова роля: ");
            String newRole = scanner.nextLine();
            adminManager.updateRole(userForRole, newRole);
            System.out.println("Ролята е променена успешно!");
        } catch (AdminNotFoundException | IllegalArgumentException e) {
            System.out.println("Грешка при смяна на роля: " + e.getMessage());
        }

        scanner.close();
    }
}
