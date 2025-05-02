package delivery;
public class Dish {
    

    private String name;
    private double price;
    private boolean available;

    public Dish(String name, double price, boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }


    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean getAvailable() {return available; }

    @Override
    public String toString() {
        return name + " - $" + price;
    }


    public void setAvailable(boolean availableChange) {
        this.available = availableChange;
    }
}
