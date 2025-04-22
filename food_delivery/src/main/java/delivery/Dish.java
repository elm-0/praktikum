package delivery;
public class Dish {
    
    //String allegries;   //tova ne znam dali ima smisul da go dobavqme, no neka stoi za ideq

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
}
