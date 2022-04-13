package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.StringTokenizer;
/**
 * Manages the available menu items for room service
 */
public class MenuItemManager extends DatabaseHandler implements Supermanager<MenuItem> {

	private final String db_filename = "menuitem_db.txt";
	public List<MenuItem> menu_item_list_;

	/**
	 * Constructor for Menu Item Manager
	 */
	public MenuItemManager() {
	}

	/**
	 * Adds a new menu item object into menu item list.
	 * 
	 * @param MenuItem MenuItem to be added
	 */
	public void AddToList(MenuItem menu_item) {
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
	 * Removes a menu item from menu item list.
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
	
	/**
	 * Returns menu item object from search for name of item
	 * 
	 * @return MenuItem searched for
	 */
	public MenuItem SearchList(String search_text) {
		// TODO - implement MenuItemManager.SearchList
		for (int i = 0; i < this.menu_item_list_.size(); i++)
		{
			if (this.menu_item_list_.get(i).getName().toUpperCase() == search_text.toUpperCase())
			{
				 return this.menu_item_list_.get(i);
			}
		}
		return null; 
	}

	/**
	 * Get List of menu items
	 * 
	 * @return List containing all menu items
	 */
	public List<MenuItem> GetList() {
		return this.menu_item_list_;
	}
	
	/**
	 * Prints the list of menu items with price and description
	 */
	public void DisplayItemMenu()
	{
		System.out.println("============ Item Menu =============");
		for (int i = 0; i < this.menu_item_list_.size(); i++)
		{
			System.out.println("Index: " + i+1 + this.menu_item_list_.get(i));
		}
		System.out.println("============ End of Menu =============");
	}
	
	/**
	 * Returns MenuItem based on index of item menu
	 * 
	 * @param index
	 * @return MenuItem
	 */
	public MenuItem GetItemByMenuIndex(int index)
	{
		if (index > 0 && (index-1) < this.menu_item_list_.size())
		{
			return this.menu_item_list_.get(index-1);
		}
		return null;
	}
	public void InitializeDB() {
		ArrayList dbArray = (ArrayList) read(db_filename);
		ArrayList dataList = new ArrayList();

		for (int i = 0; i < dbArray.size(); i++) {
			String st = (String) dbArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer

			String name = star.nextToken().trim(); // first token
			float price = Float.parseFloat(star.nextToken().trim()); // second token
			String description = star.nextToken().trim();
			
			// create object from file data
			MenuItem obj = new MenuItem(name, price, description);
			// add to Menu list
			dataList.add(obj);
		}
		 this.menu_item_list_ = dataList;
	}

	public void SaveDB() {
		List<String> menuItemData = new ArrayList<String>();

		for (MenuItem item : menu_item_list_) {
			StringBuilder st = new StringBuilder();
			st.append(item.getName());
			st.append(SEPARATOR);
			st.append(item.getPrice());
			st.append(SEPARATOR);
			st.append(item.getDescription());
			st.append(SEPARATOR);
			
			menuItemData.add(st.toString());
		}

		try {
			write(db_filename, menuItemData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edit price of menu item from menu item list to new value
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