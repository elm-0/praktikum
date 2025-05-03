package delivery.food_delivery;

public class EmployeeAlreadyExistsException extends Exception {
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
