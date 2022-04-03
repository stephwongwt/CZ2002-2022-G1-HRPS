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

	public Guest SearchList(String search_text) {

		try{
			for(Guest guest : guest_list_){
				if(search_text.equals(guest.GetIdentity())){
					return guest;
				}
			}
		}
		catch(NullPointerException ex){
			System.out.println("Guest List not initialized");
		}

		return null;

	}

	/**
	 * 
	 */
	public List<Guest> GetList() {

		return guest_list_;

	}

	public void InitializeDB() {
		// TODO - implement GuestManager.InitializeDB
	}

	public void SaveDB() {
		// TODO - implement GuestManager.SaveDB
	}

	/**
	 * 
	 * @param guest
	 * @param room_num
	 * @param payment_id
	 * @param billing_address
	 * @param cc_number
	 */
	public void CheckInGuest(Guest guest, int room_num, String payment_id, String billing_address, String cc_number) {

		guest.SetRoomNum(room_num);
		guest.SetBillingAddress(billing_address);
		guest.SetCcNumber(cc_number);
		guest.setPaymentId(payment_id);
		
	}
}