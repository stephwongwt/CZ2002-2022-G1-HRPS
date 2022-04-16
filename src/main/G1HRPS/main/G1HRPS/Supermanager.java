package main.G1HRPS;

import java.util.List;

/**
 * Interface class to be implemented by managers for basic functionality.
 * 
 * @author Steph Wong
 */
public interface Supermanager<T> {

	/**
	 * Add given object to list.
	 * 
	 * @param t object to be added
	 * @return true if successfully added, else false if object with same key already exists.
	 */
	boolean AddToList(T t);

	/**
	 * Remove given object from list.
	 * 
	 * @param t object to be removed
	 * @return true if successfully removed, else false if object does not exists.
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