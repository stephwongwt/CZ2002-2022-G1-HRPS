package main.G1HRPS;

import java.io.IOException;
import java.util.List;

public interface Supermanager<T> {

	/**
	 * Takes in an class object and list to add the object into.
	 * @param t
	 */
	void AddToList(T t);

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 * @param t
	 */
	void RemoveFromList(T t);

	/**
	 * 
	 * @param search_text
	 */
	void SearchList(String search_text);

	List<T> GetList();

	void InitializeDB() throws IOException;

	void SaveDB() throws IOException;

}