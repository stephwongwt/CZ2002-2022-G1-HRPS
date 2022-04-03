package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
/**
 * Manages the available menu items for room service
 */
public class MenuItemManager implements Supermanager<MenuItem> {

	public List<MenuItem> menu_item_list_;

	public MenuItemManager() {
		// TODO - implement MenuItemManager.MenuItemManager
		this.menu_item_list_ = new ArrayList<MenuItem>();
		InitializeDB();
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	// User is asked for 3 inputs on Meny before this method is called
	public void AddToList(MenuItem menu_item) {
		// TODO - implement MenuItemManager.AddToList
		for (int i = 0; i < this.menu_item_list_.size(); i++)
		{
			if (this.menu_item_list_.get(i).getName() == menu_item.getName())
			{
				System.out.println("Item with the same name already exists. This will not to be added");
				return;
			}
		}
		this.menu_item_list_.add(menu_item);
		System.out.println("Item " + menu_item.getName() + " added");
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(MenuItem menu_item) {
		// TODO - implement MenuItemManager.RemoveFromList
		for (int i = 0; i < this.menu_item_list_.size(); i++)
		{
			if (this.menu_item_list_.get(i).getName() == menu_item.getName())
			{
				this.menu_item_list_.remove(i);
				System.out.println("Item " + menu_item.getName() + " removed");
				return;
			}
		}
		System.out.println("Item to be removed not found in Menu!");
		
	}
	// What is this for exactly ?????
	public void SearchList(String search_text) {
		// TODO - implement MenuItemManager.SearchList
		for (int i = 0; i < this.menu_item_list_.size(); i++)
		{
			if (this.menu_item_list_.get(i).getName() == search_text)
			{
				System.out.println(this.menu_item_list_.get(i));
				return;
			}
		}
	}

	/**
	 * Prints the menu items with price and description
	 */
	public List<MenuItem> GetList() {
		// TODO - implement MenuItemManager.GetList
		System.out.println("========= Menu List =========");
		for (int i = 0; i < this.menu_item_list_.size(); i++)
		{
			System.out.println(this.menu_item_list_.get(i));
		}
		System.out.println("========= End of Menu =========");
		return this.menu_item_list_;
	}

	public void InitializeDB() {
		// TODO - implement MenuItemManager.InitializeDB
	}

	public void SaveDB() {
		// TODO - implement MenuItemManager.SaveDB
	}

	/**
	 * 
	 * @param item_name
	 * @param new_price
	 */
	public void EditMenuItemPrice(String item_name, float new_price) {
		// TODO - implement MenuItemManager.EditMenuItemPrice
		for(int i = 0; i < this.menu_item_list_.size(); i++)
		{
			if (this.menu_item_list_.get(i).getName() == item_name )
			{
				this.menu_item_list_.get(i).setPrice(new_price);
				System.out.println("Price for " + item_name + " amended!");
				return;
			}
		}
		System.out.println("Item not found in Menu!");
		
	}

}