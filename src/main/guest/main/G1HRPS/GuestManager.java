package main.guest.main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import main.main.G1HRPS.*;

public class GuestManager implements Supermanager<Guest> {

	private List<Guest> guest_list_;

	public GuestManager() {

		InitializeDB();

	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Guest guest) {

		try{
			guest_list_.add(guest);
		}
		catch(NullPointerException ex){
			System.out.println("Guest List not initialized");
		}

	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Guest guest) {

		try{
			guest_list_.remove(guest);
		}
		catch(NullPointerException ex){
			System.out.println("Guest List not initialized");
		}

	}

	public void SearchList(String search_text) {

		try{
			for(Guest guest : guest_list_){
				if(search_text.equals(guest.GetIdentity())){
					guest.printGuestInfo();
				}
			}
		}
		catch(NullPointerException ex){
			System.out.println("Guest List not initialized");
		}

	}

	public List<Guest> GetList() {

		return guest_list_;

	}

	public void InitializeDB() {



	}

	public void SaveDB() {

		
	}

	/**
	 * 
	 * @param Room
	 * @param Guest
	 */
	public boolean CheckInGuest(int room_num, Guest guest) {

		guest.SetRoomNum(room_num);
		guest.SetBillingAddress(billing_address);
		guest.SetCcNumber(cc_number);
		guest.setPaymentId(payment_id);
		
	}
}