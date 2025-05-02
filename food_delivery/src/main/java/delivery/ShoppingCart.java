    package delivery;
    import java.util.ArrayList;
    import java.util.List;

    public class ShoppingCart {
        private List<Dish> items = new ArrayList<>();

        public void addItem(Dish item) {
            items.add(item);
        }

        public void removeItem(Dish item) {
            items.remove(item);
        }

        public double getTotal() {
            double total = 0;
            for (Dish item : items) {
                total += item.getPrice();
            }
            return total;
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