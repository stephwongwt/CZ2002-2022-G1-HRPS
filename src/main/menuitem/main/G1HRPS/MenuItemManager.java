package main.G1HRPS;

import java.util.List;

/**
 * Manages the available menu items for room service
 */
public class MenuItemManager implements Supermanager<MenuItem> {

	public List<MenuItem> menu_item_list_;

	public MenuItemManager() {
		// TODO - implement MenuItemManager.MenuItemManager
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(MenuItem menu_item) {
		// TODO - implement MenuItemManager.AddToList
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(MenuItem menu_item) {
		// TODO - implement MenuItemManager.RemoveFromList
		throw new UnsupportedOperationException();
	}

	public void SearchList(String search_text) {
		// TODO - implement MenuItemManager.SearchList
		throw new UnsupportedOperationException();
	}

	/**
	 * Prints the menu items with price and description
	 */
	public List<MenuItem> GetList() {
		// TODO - implement MenuItemManager.GetList
		throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement MenuItemManager.InitializeDB
		throw new UnsupportedOperationException();
	}

	public void SaveDB() {
		// TODO - implement MenuItemManager.SaveDB
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param item_name
	 * @param new_price
	 */
	public void EditMenuItemPrice(String item_name, float new_price) {
		// TODO - implement MenuItemManager.EditMenuItemPrice
		throw new UnsupportedOperationException();
	}

}