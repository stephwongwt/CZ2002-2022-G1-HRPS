package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Manages the available menu items for room service
 */
public class MenuItemManager extends DatabaseHandler implements Supermanager<MenuItem> {
    private final String DB_FILENAME = "menuitem_db.txt";
    public List<MenuItem> menu_item_list_;

    /**
     * Constructor for Menu Item Manager
     */
    public MenuItemManager() {
        this.menu_item_list_ = new ArrayList<MenuItem>();
    }

    /**
     * Creates new MenuItem and adds it to MenuItem list.
     * 
     * @param name
     * @param price
     * @param description
     * @return
     */
    public MenuItem CreateNewMenuItem(String name, float price, String description) {
        MenuItem menu_item = new MenuItem(name, price, description);
        AddToList(menu_item);
        return menu_item;
    }

    /**
     * Adds a new menu item object into menu item list.
     * 
     * @param MenuItem obj to be added
     * @return true if success / false if failed
     */
    @Override
    public boolean AddToList(MenuItem menu_item) {
        boolean success = false;
        MenuItem found = SearchList(menu_item.GetName());
        if (found == null) {
            try {
                success = this.menu_item_list_.add(menu_item);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Removes a menu item from menu item list.
     * 
     * @param MenuItem obj to be removed
     * @return true if success / false if failed
     */
    @Override
    public boolean RemoveFromList(MenuItem menu_item) {
        boolean success = false;
        MenuItem found = SearchList(menu_item.GetName());
        if (found != null) {
            try {
                success = this.menu_item_list_.remove(found);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Returns menu item object from search for name of item
     * 
     * @param search_text menu item name to be searched
     * @return MenuItem if found, else null
     */
    @Override
    public MenuItem SearchList(String search_text) {
        for (MenuItem menu_item : this.menu_item_list_) {
            if (menu_item.GetName().toUpperCase().equals(search_text.toUpperCase())) {
                return menu_item;
            }
        }
        return null;
    }

    /**
     * Get List of menu items
     * 
     * @return List containing all menu items
     */
    @Override
    public List<MenuItem> GetList() {
        return this.menu_item_list_;
    }

    public void InitializeDB() {
        ArrayList<String> dbArray = (ArrayList) read(DB_FILENAME);
        ArrayList<MenuItem> dataList = new ArrayList<MenuItem>();
        for(String st : dbArray){
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
        for (MenuItem item : this.menu_item_list_) {
            StringBuilder st = new StringBuilder();
            st.append(item.GetName());
            st.append(SEPARATOR);
            st.append(item.GetPrice());
            st.append(SEPARATOR);
            st.append(item.GetDescription());
            st.append(SEPARATOR);
            menuItemData.add(st.toString());
        }
        try {
            write(DB_FILENAME, menuItemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}