package main.G1HRPS;

import java.util.List;

public class GuestManager implements Supermanager<Guest> {

	private List<Guest> guest_list_;

	public GuestManager() {
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Guest guest) {
		// TODO - implement GuestManager.AddToList
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Guest guest) {
		// TODO - implement GuestManager.RemoveFromList
		throw new UnsupportedOperationException();
	}

	public void SearchList(String search_text) {
		// TODO - implement GuestManager.SearchList
		throw new UnsupportedOperationException();
	}

	public List<Guest> GetList() {
		// TODO - implement GuestManager.GetList
		throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement GuestManager.InitializeDB
	}

	public void SaveDB() {
		// TODO - implement GuestManager.SaveDB
	}

	/**
	 * 
	 * @param Room
	 * @param Guest
	 */
	public boolean CheckedInGuest(int Room, int[] Guest) {
		// TODO - implement GuestManager.CheckedInGuest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Guest
	 * @param GuestStatus
	 */
	public void SetGuestStatus(int Guest, int GuestStatus) {
		// TODO - implement GuestManager.SetGuestStatus
		throw new UnsupportedOperationException();
	}

}