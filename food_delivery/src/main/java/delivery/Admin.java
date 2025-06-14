package delivery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import delivery.food_delivery.AdminAlreadyExistsException;
import delivery.food_delivery.AdminNotFoundException;
import delivery.food_delivery.AuthenticationException;
import delivery.food_delivery.NoAdminLoggedInException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

@Entity
public class Admin {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_actions", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "action")
    private List<String> actionHistory = new ArrayList<>();

    @Transient
    private List<String> companies = new ArrayList<>();

    @Transient
    private List<String> restaurants = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    
    @Transient
    AdminManager adminManager;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MODERATOR,
        SUPPORT
    }

    public Admin() {
    }

    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.adminManager = new AdminManager();
    }

    public boolean isSuperAdmin() {
        return getRole().equals(Role.SUPER_ADMIN.toString());
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

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

    //@Override
    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Паролата трябва да е минимум 8 символа.");
        }
        //setPassword(hashPassword(password));
        this.password = password;
        addAction("Паролата е променена");
    }

    //@Override
    public void setUsername(String username) {
        //super.setUsername(username);
        this.username = username;
        addAction("Името е променено на " + username);
    }

    //@Override
    public void setRole(String role) {
        //super.setRole(role);
        this.role = role;
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
        System.out.println("Въведете команда:\n" +
                "1. Логин\n" +
                "2. Изход\n" +
                "3. Добавяне на администратор\n" +
                "4. Премахване на администратор\n" +
                "5. Получаване на всички администратори\n" +
                "6. Смяна на парола\n" +
                "7. Смяна на потребителско име\n" +
                "8. Смяна на роля\n" +
                "9. Добавяне на фирма\n" +
                "10. Премахване на фирма\n" +
                "11. Добавяне на ресторант\n" +
                "12. Премахване на ресторант\n" +
                "13. Изход");
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                try {
                    System.out.print("Въведете потребителско име за логин: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Въведете парола за логин: ");
                    String loginPassword = scanner.nextLine();
                    adminManager.login(loginUsername, loginPassword);
                } catch (AuthenticationException e) {
                    System.out.println("Грешка при логин: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    System.out.println("\nНатиснете Enter за изход...");
                    scanner.nextLine();
                    adminManager.logout();
                } catch (NoAdminLoggedInException e) {
                    System.out.println("Грешка при изход: " + e.getMessage());
                }
                break;
            case 3:
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
                break;
            case 4:
                try {
                    System.out.print("\nВъведете потребителско име на администратор за премахване: ");
                    String adminToRemove = scanner.nextLine();
                    adminManager.removeAdmin(adminToRemove);
                    System.out.println("Администраторът е премахнат успешно!");
                } catch (AdminNotFoundException e) {
                    System.out.println("Грешка при премахване: " + e.getMessage());
                }
                break;
            case 5:
                System.out.println("\nВсички администратори:");
                adminManager.getAllAdmins()
                        .forEach(admin -> System.out.println(admin.getUsername() + " - " + admin.getRole()));
                break;
            case 6:
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
                break;
            case 7:
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
                break;
            case 8:
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
                break;
            case 9:
                try {
                    scanner.nextLine();
                    System.out.print("Въведете име на фирмата за добавяне: ");
                    String companyName = scanner.nextLine();
                    adminManager.addCompany(companyName, adminManager.getCurrentAdmin());
                    System.out.println("Фирмата е добавена успешно!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Грешка: " + e.getMessage());
                }
                break;

            case 10:
                try {
                    scanner.nextLine();
                    System.out.print("Въведете име на фирмата за премахване: ");
                    String companyName = scanner.nextLine();
                    adminManager.removeCompany(companyName, adminManager.getCurrentAdmin());
                    System.out.println("Фирмата е премахната успешно!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Грешка: " + e.getMessage());
                }
                break;

            case 11:
                try {
                    scanner.nextLine();
                    System.out.print("Въведете име на ресторант за добавяне: ");
                    String restaurantName = scanner.nextLine();
                    adminManager.addRestaurant(restaurantName, adminManager.getCurrentAdmin());
                    System.out.println("Ресторантът е добавен успешно!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Грешка: " + e.getMessage());
                }
                break;

            case 12:
                try {
                    scanner.nextLine();
                    System.out.print("Въведете име на ресторант за премахване: ");
                    String restaurantName = scanner.nextLine();
                    adminManager.removeRestaurant(restaurantName, adminManager.getCurrentAdmin());
                    System.out.println("Ресторантът е премахнат успешно!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Грешка: " + e.getMessage());
                }
                break;

            case 13:
                System.out.println("Изход от администраторския панел.");
                break;
            default:
                System.out.println("Невалидна команда. Моля, опитайте отново.");
        }
        scanner.close();
    }
}
