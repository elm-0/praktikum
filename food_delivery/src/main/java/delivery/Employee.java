package delivery;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String employeeId;
    private int numberOfAvailableOrders;
    @ElementCollection
    private List<String> acceptedOrderIds;
    private boolean isAvailable;
    private double totalRevenue;

    @OneToMany(mappedBy = "employee")
    private List<ShoppingCart> assignedOrders = new ArrayList<>();

    public Employee() {
        
    }

    public Employee(String username, String password, String employeeId) {
        super(username, password, "EMPLOYEE");
        this.employeeId = employeeId;
        this.numberOfAvailableOrders = 1;
        this.acceptedOrderIds = new ArrayList<>();
        this.isAvailable = true;
        this.totalRevenue = 0.0;
    }

    public List<ShoppingCart> getAssignedOrders() {
        return assignedOrders;
    }

    public void setAssignedOrders(List<ShoppingCart> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getNumberOfAvailableOrders() {
        return numberOfAvailableOrders;
    }

    public void setNumberOfAvailableOrders(int numberOfAvailableOrders) {
        this.numberOfAvailableOrders = numberOfAvailableOrders;
        this.isAvailable = numberOfAvailableOrders > 0;
    }

    public List<String> getAcceptedOrderIds() {
        return acceptedOrderIds;
    }

    public void setAcceptedOrderIds(List<String> acceptedOrderIds) {
        this.acceptedOrderIds = acceptedOrderIds;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void acceptOrder(String orderId) {
        if (isAvailable && numberOfAvailableOrders > 0) {
            acceptedOrderIds.add(orderId);
            numberOfAvailableOrders--;
            isAvailable = numberOfAvailableOrders > 0;
        } else {
            System.out.println("Доставчикът не може да приеме повече поръчки.");
        }
    }

    public void completeOrder(String orderId, double orderAmount) {
        if (acceptedOrderIds.contains(orderId)) {
            acceptedOrderIds.remove(orderId);
            numberOfAvailableOrders++;
            isAvailable = true;
            totalRevenue += orderAmount;
            System.out.println("Поръчка " + orderId + " е доставена.");
        } else {
            System.out.println("Доставчикът не е приел тази поръчка.");
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + getUsername() + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", numberOfAvailableOrders=" + numberOfAvailableOrders +
                ", acceptedOrderIds=" + acceptedOrderIds +
                ", isAvailable=" + isAvailable +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
