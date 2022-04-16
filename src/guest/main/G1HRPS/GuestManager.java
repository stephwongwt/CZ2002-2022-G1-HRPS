package main.G1HRPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Implements the Supermanager class to handle all the guests in our hotel.
 * 
 * @author Kim Sang Hyeon
 *
 */
public class GuestManager extends DatabaseHandler implements Supermanager<Guest> {
	/**
	 * Internally stored list of guests.
	 */
	private List<Guest> guest_list_;
	
	/**
	 * Filename of database file.
	 */
	private final String DB_FILENAME = "guest_db.txt";

	/**
	 * Creates a Guest Manager.
	 */
	public GuestManager() {
		guest_list_ = new ArrayList<Guest>();
	}

	/**
	 * Creates a new guest and adds it to guest list.
	 * 
	 * @param identity				Either national identity or driving license information
	 * @param name					Name of Guest
	 * @param credit_card_number	Payment details
	 * @param billing_address		Address to send bill to
	 * @param contact				Mobile contact number
	 * @param country				Guest's country of origin
	 * @param gender				Gender of guest
	 * @param nationality			Nationality of guest
	 * @return the created guest object if successful, else null
	 */
	public Guest CreateNewGuest(String identity, String name, String credit_card_number, String billing_address,
			String contact, String country, Gender gender, String nationality) {
		Guest new_guest = new Guest(identity, name, credit_card_number, billing_address, contact, country, gender,
				nationality);
		if (AddToList(new_guest)) {
			return new_guest;
		} else {
			return null;
		}
	}

	/**
	 * Method to verify the length of credit card numbers.
	 * 
	 * @param credit_card_number the number to be verified
	 * @return boolean true if number is valid, else false
	 */
	public boolean VerifyCreditCardNumber(String credit_card_number) {
		String noSpace_CreditCardNum = credit_card_number.replaceAll("\\s+", "");
		if (noSpace_CreditCardNum.matches("[0-9]+") && (noSpace_CreditCardNum.length() >= Guest.MIN_CC_NUMLEN)
				&& (noSpace_CreditCardNum.length() <= Guest.MAX_CC_NUMLEN)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds a new guest object into guest list.
	 * 
	 * @param guest Guest object to be added
	 * @return true if success / false if failed
	 */
	@Override
	public boolean AddToList(Guest guest) {
		boolean success = false;
		Guest found = SearchList(guest.GetIdentity());
		if (found == null) {
			try {
				success = guest_list_.add(guest);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Removes a given guest from the guest list.
	 * 
	 * @param guest Guest object to be removed
	 * @return true if success / false if failed
	 */
	@Override
	public boolean RemoveFromList(Guest guest) {
		boolean success = false;
		Guest found = SearchList(guest.GetIdentity());
		if (found != null) {
			try {
				success = this.guest_list_.remove(found);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Search internal list by guest's name.
	 * 
	 * @param guest_name the guest to be searched for
	 * @return list of guest objects
	 */
	public List<Guest> SearchListByName(String guest_name) {
		List<Guest> found_guests = new ArrayList<>();
		for (Guest guest : guest_list_) {
			if (guest_name.equals(guest.GetName())) {
				found_guests.add(guest);
			}
		}
		return found_guests;
	}

	/**
	 * Uses guest ID as a key to search for corresponding guest in the list.
	 * 
	 * @param search_text the guest id to be searched for.
	 * @return Guest object with matching guest ID.
	 */
	@Override
	public Guest SearchList(String search_text) {
		for (Guest guest : guest_list_) {
			if (search_text.equals(guest.GetIdentity())) {
				return guest;
			}
		}
		return null;
	}

	/**
	 * Gets a list of current guests.
	 * 
	 * @return List of guests objects.
	 */
	public List<Guest> GetList() {
		return guest_list_;
	}

	/**
	 * Checks-in a guest by setting room number.
	 * 
	 * @param guest    Object with details of guest to be checked in.
	 * @param room_num Room number to check guest into.
	 */
	public void CheckIntoRoom(Guest guest, int room_num) {
		guest.SetRoomNum(room_num);
	}

	/**
	 * Checks-out a guest by setting payment id.
	 * 
	 * @param guest 	 Guest to assign the payment_id
	 * @param payment_id Id to be assigned to guest
	 */
	public void CheckOutOfRoom(Guest guest, String payment_id) {
		guest.SetPaymentId(payment_id);
	}

	/**
	 * Read guest data as list of String objects and create a list of Guest class
	 * objects. Each String object is parsed to create each of the guests.
	 */
	@Override
	public void InitializeDB() {
		ArrayList<String> dbArray = (ArrayList) read(DB_FILENAME);
		ArrayList<Guest> dataList = new ArrayList<>();
		for (String st : dbArray) {
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			String identity = star.nextToken().trim();
			String payment_id = star.nextToken().trim();
			int room_num = Integer.parseInt(star.nextToken().trim());
			String name = star.nextToken().trim();
			String credit_card_number = star.nextToken().trim();
			String billing_address = star.nextToken().trim();
			String contact = star.nextToken().trim();
			String country = star.nextToken().trim();
			Gender gender = Gender.valueOf(star.nextToken().trim());
			String nationality = star.nextToken().trim();
			String check_in_date = star.nextToken().trim();
			Guest obj = new Guest(identity, payment_id, room_num, name, credit_card_number, billing_address, contact,
					country, gender, nationality, check_in_date);
			dataList.add(obj);
		}
		this.guest_list_ = dataList;
	}

	/**
	 * Save each guest object from guest list by converting the attributes to a
	 * String object and saving to each line of the save file.
	 * 
	 */
	@Override
	public void SaveDB() {
		List<String> guestData = new ArrayList<>();
		for (Guest guest : guest_list_) {
			StringBuilder st = new StringBuilder();
			st.append(guest.GetIdentity());
			st.append(SEPARATOR);
			String payment_id = guest.GetPaymentId();
			st.append(payment_id.isEmpty() ? Guest.EMPTY : payment_id);
			st.append(SEPARATOR);
			st.append(guest.GetRoomNum());
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
			st.append(SEPARATOR);
			st.append(guest.GetCheckInDate());
			guestData.add(st.toString());
		}
		try {
			write(DB_FILENAME, guestData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
