package main.guest.main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import main.main.G1HRPS.*;

public class GuestManager implements Supermanager<Guest> {

	private List<Guest> guest_list_;

	public GuestManager() {
		// TODO - implement GuestManager.GuestManager

		InitializeDB();

	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Guest guest) {
		// TODO - implement GuestManager.AddToList

		guest_list_.add(guest);

		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Guest guest) {
		// TODO - implement GuestManager.RemoveFromList

		guest_list_.remove(guest);

		throw new UnsupportedOperationException();
	}

	public void SearchList(String search_text) {
		// TODO - implement GuestManager.SearchList

		for(Guest guest : guest_list_){
			if(search_text.equals(guest.GetIdentity())){
				
			}
		}

		throw new UnsupportedOperationException();
	}

	public List<Guest> GetList() {
		// TODO - implement GuestManager.GetList

		return guest_list_;

		// throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement GuestManager.InitializeDB

		guest_list_ = new ArrayList<Guest>();

		throw new UnsupportedOperationException();
	}

	public void SaveDB() {
		// TODO - implement GuestManager.SaveDB

		

		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Room
	 * @param Guest
	 */
	public boolean CheckInGuest(int room_num, Guest guest) {
		// TODO - implement GuestManager.CheckedInGuest
		// CheckInGuest? is int[] Guest necessary

		guest.SetRoomNum(room_num);

		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param guest
	 * @param guestStatus
	 */
	public void SetGuestStatus(Guest guest, int guestStatus) {
		// TODO - implement GuestManager.SetGuestStatus
		
		

		throw new UnsupportedOperationException();
	}
}