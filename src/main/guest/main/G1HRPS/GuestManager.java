package main.G1HRPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GuestManager extends DatabaseHandler implements Supermanager<Guest> {

	private List<Guest> guest_list_;
	private final String db_filename = "guest_db.txt";

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
		catch(NullPointerException e){
			System.out.println("Guest List not initialized");
			e.printStackTrace();
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
		catch(NullPointerException e){
			System.out.println("Guest List not initialized");
			e.printStackTrace();
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

		ArrayList<String> dbArray = (ArrayList) read(db_filename);
		ArrayList<Guest> dataList = new ArrayList<>();

		for(String st : dbArray){
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String identity = star.nextToken().trim();
			String payment_id = star.nextToken().trim();
			int room_num = Integer.parseInt(star.nextToken().trim());
			String name = star.nextToken().trim();
			String cc_number = star.nextToken().trim();
			String address = star.nextToken().trim();
			String contact = star.nextToken().trim();
			String country = star.nextToken().trim();
			Gender gender = Gender.valueOf(star.nextToken().trim());
			String nationality = star.nextToken().trim();

			Guest guest = new Guest(identity, name, cc_number, address, contact, country, gender, nationality);
			guest.setPaymentId(payment_id);
			guest.SetRoomNum(room_num);

			dataList.add(guest);
		}

		guest_list_ = dataList;
	}

	public void SaveDB() {
		// TODO - implement GuestManager.SaveDB

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
	public void CheckInGuest(Guest guest, int room_num, String payment_id, String billing_address, String cc_number) {

		guest.SetRoomNum(room_num);
		guest.SetBillingAddress(billing_address);
		guest.SetCcNumber(cc_number);
		guest.setPaymentId(payment_id);
		
	}
}