package delivery;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String username;
    private String password;
    private String employeeId;
    private int numberOfAvailableOrders;
    private List<String> acceptedOrderIds;
    private boolean isAvailable;
    private double totalRevenue;

    public Employee(String username, String password, String employeeId) {
        this.username = username;
        this.password = password;
        this.employeeId = employeeId;
        this.numberOfAvailableOrders = 1;
        this.acceptedOrderIds = new ArrayList<>();
        this.isAvailable = true;
        this.totalRevenue = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "DeliveryDriver{" +
                "username='" + username + '\'' +
                ", driverId='" + employeeId + '\'' +
                ", numberOfAvailableOrders=" + numberOfAvailableOrders +
                ", acceptedOrderIds=" + acceptedOrderIds +
                ", isAvailable=" + isAvailable +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}