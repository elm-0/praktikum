    package delivery;
    import java.util.ArrayList;
    import java.util.List;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.ManyToMany;
    
    @Entity
    public class ShoppingCart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToMany
        private List<Dish> items = new ArrayList<>();

        public ShoppingCart() {}

        public Long getId() {
            return id;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Employee getEmployee() {
            return employee;
        }

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }


        public void addItem(Dish item) {
            items.add(item);
        }

        public void removeItem(Dish item) {
            items.remove(item);
        }

        public double getTotal() {
            return items.stream().mapToDouble(Dish::getPrice).sum();
        }

        public void displayCart() {
            System.out.println("Your Cart:");
            for (Dish item : items) {
                System.out.println(item);
            }
            System.out.printf("Total: $%.2f%n", getTotal());
        }

        public List<Dish> getItems() {
            return items;
        }

        public void clearCart() {
            items.clear();
        }
}
