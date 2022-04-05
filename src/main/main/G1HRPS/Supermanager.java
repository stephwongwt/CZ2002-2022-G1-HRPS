package main.G1HRPS;

import java.io.IOException;
import java.util.List;

public interface Supermanager<T> {

	/**
	 * Add given object to list.
	 * 
	 * @param t object to be added
	 */
	void AddToList(T t);

	/**
	 * Remove given object from list.
	 * 
	 * @param t object to be removed
	 */
	void RemoveFromList(T t);

	/**
	 * Search list using given search text.
	 * 
	 * @param search_text keyword to search for.
	 * @return object or null if not found.
	 */
	T SearchList(Object search_text);

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