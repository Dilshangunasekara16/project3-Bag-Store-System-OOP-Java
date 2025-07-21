package Main;

public class Bag {
    private int bagID;
    private String name;
    private String category;
    private double price;

    public Bag(int bagID, String name, String category, double price) {
        this.bagID = bagID;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String viewDetails() {
        return "Bag ID: " + bagID + ", Name: " + name + ", Category: " + category + ", Price: " + price;
    }

    // Getters and Setters
    public int getBagID() {
        return bagID;
    }

    public void setBagID(int bagID) {
        this.bagID = bagID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
