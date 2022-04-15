package main.G1HRPS;

public class MenuItem {
    private String name_;
    private float price_;
    private String description_;

    /**
     * Constructor for a new menu item
     * 
     * @param name
     * @param price
     * @param description
     */
    public MenuItem(String name, float price, String description) {
        this.name_ = name;
        this.price_ = price;
        this.description_ = description;
    }

    /**
     * Gets the name of the menu item
     * 
     * @return String containing name of menu item
     */
    public String GetName() {
        return name_;
    }

    /**
     * Sets the name of the menu item
     * 
     * @param name
     */
    public void SetName(String name) {
        this.name_ = name;
    }

    /**
     * Gets the price of the menu item
     * 
     * @return float containing the price of the item
     */
    public float GetPrice() {
        return price_;
    }

    /**
     * Sets the price of the menu item
     * 
     * @param price
     */
    public void SetPrice(float price) {
        this.price_ = price;
    }

    /**
     * Gets the description of the menu item
     * 
     * @return String containing the description
     */
    public String GetDescription() {
        return description_;
    }

    /**
     * Sets the description of the menu item
     * 
     * @param description
     */
    public void SetDescription(String description) {
        this.description_ = description;
    }

    /**
     * Prints menu item information
     */
    @Override
    public String toString() {
        return "Name: " + this.name_ + " Price: $" + this.price_ + "\nDescription: " + this.description_;
    }
}