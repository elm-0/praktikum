
package delivery;

public class Employee extends User {  
    String employeeId;
    int numberOfAvailableOrders;
    public Employee(String username, String password) {
        super(username, password, "EMPLOYEE"); 
    }
}
