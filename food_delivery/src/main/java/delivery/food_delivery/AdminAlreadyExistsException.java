package delivery.food_delivery;

public class AdminAlreadyExistsException extends Exception{
    public AdminAlreadyExistsException(String username) {
        super("Admin with username " + username + " already exists.");
    }

}
