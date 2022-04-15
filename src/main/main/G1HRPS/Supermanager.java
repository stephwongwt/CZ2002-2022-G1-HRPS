package main.G1HRPS;

import java.util.List;

public interface Supermanager<T> {

    /**
     * Add given object to list.
     * 
     * @param t object to be added
     */
    boolean AddToList(T t);

    /**
     * Remove given object from list.
     * 
     * @param t object to be removed
     */
    boolean RemoveFromList(T t);

    /**
     * Search list using given search text.
     * 
     * @param search_text keyword to search for.
     * @return object or null if not found.
     */
    T SearchList(String search_text);

    /**
     * Gets list.
     * 
     * @return List of object
     */
    List<T> GetList();

    /**
     * Initialize list from database file or create file if it does not exist.
     */
    void InitializeDB();

    /**
     * Save list to database file.
     */
    void SaveDB();

}