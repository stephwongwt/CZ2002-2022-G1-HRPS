package main.G1HRPS;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class GuestManager extends DatabaseHandler implements Supermanager<Guest> {
	private List<Guest> guest_list_;
	private final String db_filename = "guest_db.txt";

	/**
	 * Creates a Guest Manager.
	 */
	public GuestManager() {
		guest_list_ = new ArrayList<Guest>();
	}

	/**
	 * Creates a new guest and adds it to guest list.
	 * 
	 * @param identity
	 * @param payment_id
	 * @param room_num
	 * @param name
	 * @param credit_card_number
	 * @param address            Billing address
	 * @param contact
	 * @param country
	 * @param gender
	 * @param nationality
	 */
	public void CreateNewGuest(String identity, String name, String credit_card_number, String address, String contact, String country, Gender gender, String nationality) {
		Guest new_guest = new Guest(identity, name, credit_card_number, address, contact, country, gender, nationality);
		AddToList(new_guest);
	}

	/**
	 * Adds a new guest object into guest list.
	 * 
	 * @param guest Guest object to be added.
	 */
	public void AddToList(Guest guest) {
		boolean success;
		try {
			success = guest_list_.add(guest);
			if (success) {
				System.out.println("Guest added to list");
			} else {
				System.out.println("Guest of Name: " + guest.GetName() +
						" and ID: " + guest.GetIdentity() + " not added to list");
			}
		} catch (NullPointerException e) {
			System.out.println("Guest List not initialized");
			e.printStackTrace();
		}
	}

	/**
	 * Removes a given guest from the guest list.
	 * 
	 * @param guest Guest object to be removed.
	 */
	public void RemoveFromList(Guest guest) {
		boolean success;
		try {
			success = guest_list_.remove(guest);
			if (success) {
				System.out.println("Guest removed from list");
			} else {
				System.out.println("Guest of Name: " + guest.GetName() +
						" and ID: " + guest.GetIdentity() + " not removed from list");
			}
		} catch (NullPointerException e) {
			System.out.println("Guest List not initialized");
			e.printStackTrace();
		}
	}

	/**
	 * Uses guest ID as a key to search for corresponding guest
	 * in the list.
	 * 
	 * @param search_text
	 * @return Guest object with matching guest ID.
	 */
	@Override
	public Guest SearchList(String guest_id) {
		for (Guest guest : guest_list_) {
			if (guest_id.equals(guest.GetIdentity())) {
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
	 * @param guest
	 * @param room_num
	 */
	public void CheckInGuest(Guest guest, int room_num) {
		guest.SetRoomNum(room_num);
	}

	/**
	 * Checks-out a guest by setting
	 * <ol>
	 * <li>Payment ID
	 * <li>Billing address
	 * <li>Credit card number
	 * </ol>
	 * 
	 * @param guest
	 * @param room_num
	 * @param billing_address
	 * @param credit_card_number
	 */
	public void CheckOutGuest(Guest guest, UUID payment_id, String billing_address, String credit_card_number) {
		guest.SetBillingAddress(billing_address);
		guest.SetCreditCardNumber(credit_card_number);
		guest.SetPaymentId(payment_id);
	}

	/**
	 * Read guest data as list of String objects and create
	 * a list of Guest class objects.
	 * Each String object is parsed to create each of the guests.
	 */
	public void InitializeDB() {
		ArrayList<String> dbArray = (ArrayList) read(db_filename);
		ArrayList<Guest> dataList = new ArrayList<>();
		for (String st : dbArray) {
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
			Guest guest = new Guest(identity, payment_id, room_num, name, cc_number, address, contact, country, gender,
					nationality);
			dataList.add(guest);
		}
		guest_list_ = dataList;
	}

	/**
	 * Save each guest object from guest list by converting the
	 * attributes to a String object and saving to each line of the
	 * save file.
	 * 
	 */
	public void SaveDB() {
		List<String> guestData = new ArrayList<>();
		for (Guest guest : guest_list_) {
			StringBuilder st = new StringBuilder();
			st.append(guest.GetIdentity());
			st.append(SEPARATOR);
			st.append(guest.GetPaymentId());
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
			guestData.add(st.toString());
		}
		try {
			write(db_filename, guestData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
