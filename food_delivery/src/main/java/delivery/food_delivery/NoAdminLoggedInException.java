package delivery.food_delivery;

public class NoAdminLoggedInException extends Exception{
    public NoAdminLoggedInException(String message) {
        super(message);
    }
}
