package main.G1HRPS;

public class MenuItem {

	public String name_;
	public float price_;
	public String description_;

	/**
	 * 
	 * @param name
	 * @param price
	 * @param description
	 */
	public MenuItem(String name, float price, String description) {
		// TODO - implement MenuItem.MenuItem
		this.name_ = name;
		this.price_ = price;
		this.description_ = description;
	}
	public String toString()
	{
		return "Item: "+ this.name_ + " Price: $" + this.price_ + " Description: " + this.description_;
	}
	public String getName() {
		return name_;
	}
	public void setName(String name) {
		this.name_ = name;
	}
	public float getPrice() {
		return price_;
	}
	public void setPrice(float price) {
		this.price_ = price;
	}
	

}