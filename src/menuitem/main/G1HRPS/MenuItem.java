package main.G1HRPS;

/**
 * MenuItem class for storing the info of each of the menu item for use with
 * room service order.
 * 
 * @author Yeo Hong Wei
 *
 */
public class MenuItem {
	/**
	 * Name of menu item.
	 */
	private String name_;
	
	/**
	 * Price of menu item.
	 */
	private float price_;
	
	/**
	 * Description of menu item.
	 */
	private String description_;

	/**
	 * Constructor for a new menu item.
	 * 
	 * @param name			Item name
	 * @param price			Item price
	 * @param description	Item Description
	 */
	public MenuItem(String name, float price, String description) {
		this.name_ = name;
		this.price_ = price;
		this.description_ = description;
	}

	/**
	 * Gets the name of the menu item.
	 * 
	 * @return String containing name of menu item
	 */
	public String GetName() {
		return name_;
	}

	/**
	 * Sets the name of the menu item.
	 * 
	 * @param name New name of item
	 */
	public void SetName(String name) {
		this.name_ = name;
	}

	/**
	 * Gets the price of the menu item.
	 * 
	 * @return float containing the price of the item
	 */
	public float GetPrice() {
		return price_;
	}

	/**
	 * Sets the price of the menu item.
	 * 
	 * @param price New price of item
	 */
	public void SetPrice(float price) {
		this.price_ = price;
	}

	/**
	 * Gets the description of the menu item.
	 * 
	 * @return String containing the description
	 */
	public String GetDescription() {
		return description_;
	}

	/**
	 * Sets the description of the menu item.
	 * 
	 * @param description New description of item
	 */
	public void SetDescription(String description) {
		this.description_ = description;
	}

	/**
	 * Prints menu item information.
	 */
	@Override
	public String toString() {
		return "Name: " + this.name_ + " Price: $" + this.price_ + " Description: " + this.description_;
	}
}