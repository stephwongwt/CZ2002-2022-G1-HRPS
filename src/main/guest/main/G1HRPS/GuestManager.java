package main.G1HRPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class GuestManager extends DatabaseHandler implements Supermanager<Guest> {

	private List<Guest> guest_list_;
	private final String db_filename = "guest_db.txt";

	public GuestManager() {

	}

	/**
	 * 
	 * 
	 * @param identity
	 * @param payment_id
	 * @param room_num
	 * @param name
	 * @param cc_number
	 * @param address
	 * @param contact
	 * @param country
	 * @param gender
	 * @param nationality
	 */

	public boolean AddNewObject(String identity, UUID payment_id, int room_num, String name, String cc_number, String address, String contact, String country, Gender gender, String nationality) {

		for(Guest guest : guest_list_) {
			if(guest.GetIdentity().equals(identity)) {
				System.out.println("There exists guest with same ID");
				return false;
			}
			else if(guest.getPaymentId().equals(payment_id)) {
				System.out.println("There exists guest with same payment ID");
				return false;
			}
		}

		Guest new_guest = new Guest(identity, payment_id, room_num, name, cc_number, address, contact, country, gender, nationality);

		AddToList(new_guest);

		return true;
	}

	/**
	 * Takes in an class object and list to add the object into.
	 * 
	 * @param guest
	 */

	public void AddToList(Guest guest) {

		try{
			guest_list_.add(guest);
		}
		catch(NullPointerException e){
			System.out.println("Guest List not initialized");
			e.printStackTrace();
		}

	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 * 
	 * @param guest
	 */
	public void RemoveFromList(Guest guest) {

		try{
			guest_list_.remove(guest);
		}
		catch(NullPointerException e){
			System.out.println("Guest List not initialized");
			e.printStackTrace();
		}

	}

	/**
	 * Takes search text as a key to search for Guest class object
	 * in the list.
	 * 
	 * @param	search_text
	 * @return	guest object with matching search text as guest ID
	 */

	public Guest SearchList(String search_text) {

		try{
			for(Guest guest : guest_list_){
				if(search_text.equals(guest.GetIdentity())){
					return guest;
				}
			}
		}
		catch(NullPointerException e){
			System.out.println("Guest List not initialized");
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 
	 * @return	list of guests
	 */
	public List<Guest> GetList() {

		return guest_list_;

	}

	/**
	 * Read guest data as list of String objects and create
	 * a list of Guest class objects with corresponding data.
	 * 
	 */

	public void InitializeDB() {

		ArrayList<String> dbArray = (ArrayList) read(db_filename);
		ArrayList<Guest> dataList = new ArrayList<>();

		for(String st : dbArray){
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String identity = star.nextToken().trim();
			UUID payment_id = UUID.fromString(star.nextToken().trim());
			int room_num = Integer.parseInt(star.nextToken().trim());
			String name = star.nextToken().trim();
			String cc_number = star.nextToken().trim();
			String address = star.nextToken().trim();
			String contact = star.nextToken().trim();
			String country = star.nextToken().trim();
			Gender gender = Gender.valueOf(star.nextToken().trim());
			String nationality = star.nextToken().trim();

			Guest guest = new Guest(identity, payment_id, room_num, name, cc_number, address, contact, country, gender, nationality);

			dataList.add(guest);
		}

		guest_list_ = dataList;
	}

	/**
	 * Save each guest object from guest list by converting the
	 * attributes to one String object and writing to each line
	 * of the save file.
	 * 
	 */

	public void SaveDB() {

		List<String> guestData = new ArrayList<>();

		for(Guest guest : guest_list_){
			StringBuilder st = new StringBuilder();

			st.append(guest.GetIdentity());
			st.append(SEPARATOR);
			st.append(guest.getPaymentId());
			st.append(SEPARATOR);
			st.append(guest.getRoomNum());
			st.append(SEPARATOR);
			st.append(guest.GetName());
			st.append(SEPARATOR);
			st.append(guest.GetCcNumber());
			st.append(SEPARATOR);
			st.append(guest.GetBillingAddress());
			st.append(SEPARATOR);
			st.append(guest.GetContact());
			st.append(SEPARATOR);
			st.append(guest.GetCountry());
			st.append(SEPARATOR);
			st.append(guest.GetGender().toString());
			st.append(SEPARATOR);
			st.append(guest.GetNationality());

			guestData.add(st.toString());
		}
		
		try {
			write(db_filename, guestData);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param guest
	 * @param room_num
	 * @param payment_id
	 * @param billing_address
	 * @param cc_number
	 */
	public void CheckInGuest(Guest guest, int room_num, UUID payment_id, String billing_address, String cc_number) {

		guest.SetRoomNum(room_num);
		guest.SetBillingAddress(billing_address);
		guest.SetCcNumber(cc_number);
		guest.setPaymentId(payment_id);
		
	}

}