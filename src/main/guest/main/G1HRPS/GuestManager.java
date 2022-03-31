package main.guest.main.G1HRPS;

import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import main.main.G1HRPS.*;

public class GuestManager implements Supermanager<Guest> {

	private List<Guest> guest_list_;
	public static final String SEPARATOR = "|";

	public GuestManager() {
		// TODO - implement GuestManager.GuestManager

		InitializeDB();

		throw new UnsupportedOperationException();
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

	public Guest SearchList(String search_text) {
		// TODO - implement GuestManager.SearchList

		for(Guest guest : guest_list_){
			if(search_text.equals(guest.GetIdentity())){
				return guest;
			}
		}

		return null;

		// throw new UnsupportedOperationException();
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

	public static List<Guest> readGuests(String filename) throws IOException {
		List<String> stringArray = read(filename);
		List<Guest> guestList = new ArrayList<>();

		for(String st : stringArray){
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			
			String identity = star.nextToken().trim();
			String payment_id = star.nextToken().trim();
			int room_num = Integer.parseInt(star.nextToken().trim());
			String name = star.nextToken().trim();
			int cc_number = Integer.parseInt(star.nextToken().trim());
			String address = star.nextToken().trim();
			String contact = star.nextToken().trim();
			String country = star.nextToken().trim();
			Gender gender = Gender.valueOf(star.nextToken().trim());
			String nationality = star.nextToken().trim();

			Guest guest = new Guest(identity, name, cc_number, address, contact, country, gender, nationality);
			guest.setPaymentId(payment_id);
			guest.SetRoomNum(room_num);

			guestList.add(guest);
		}

		return guestList;
	}

	public static void saveGuests(String fileName, List<Guest> guestList) throws IOException {
		List<String> guestData = new ArrayList<>();

		for(Guest guest : guestList){
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
		write(fileName, guestData);
	}

	public static List<String> read(String fileName) throws IOException {
		List<String> data = new ArrayList<>();
		Scanner sc = new Scanner(new FileInputStream(fileName));
		try{
			while(sc.hasNextLine()){
				data.add(sc.nextLine());
			}
		}		
		finally{
			sc.close();
		}

		return data;
	}

	public static void write(String fileName, List<String> data) throws IOException {
		PrintWriter out = new PrintWriter(fileName);

		try{
			for(int i = 0; i < data.size(); i++){
				out.println(data.get(i));
			}
		}
		finally{
			out.close();
		}
	}

}