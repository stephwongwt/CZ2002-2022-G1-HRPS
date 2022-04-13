package main.G1HRPS;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReservationManager implements Supermanager<Reservation> {

	private List<Reservation> reservation_list_;
	private final String db_filename = "reservation_db.txt";

	public ReservationManager() {
		reservation_list_ = new ArrayList<Reservation>();
	}
	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Reservation reservation) {
		reservation_list_.add(reservation);
	}

	public void AddNewReservation(String reservation_code, String guest_id, Timestamp check_in_date, Timestamp check_out_date,
	int adult_num, int children_num, ReservationStatus status, int room_num){
		Reservation res = new Reservation(reservation_code, guest_id, check_in_date, check_out_date, adult_num, children_num, status, room_num);
		reservation_list_.add(res);
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Reservation reservation) {
		// TODO - implement ReservationManager.RemoveFromList
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an text or guest object and and returns a reservation object
	 */
	public Reservation SearchList(String search_text) {
		for (Reservation res : reservation_list_){
			if(res.GetReservationCode().equals(search_text)){
				return res;
			}
		}
		System.out.println("cannot find reservation to this reservation Id");
		return null;
	}


	public Reservation SearchList(Guest guest) {
		for (Reservation res : reservation_list_){
			if(res.GetGuestId().equals(guest.GetIdentity())){
				return res;
			}
		}
		System.out.println("cannot find this guest");
		return null;
	}

	public List<Reservation> GetList() {
		return reservation_list_;
	}

	public void InitializeDB() {
		// TODO - implement ReservationManager.InitializeDB

		ArrayList<String> dbArray = (ArrayList) read(db_filename);
		ArrayList<Reservation> dataList = new ArrayList<>();

		for(String st : dbArray){
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String reservationCode = star.nextToken().trim();
			String guestId = star.nextToken().trim();
			String checkInDate = star.nextToken().trim();
			String checkOutDate = star.nextToken().trim();
			String adultNum = star.nextToken().trim();
			String childrenNum = star.nextToken().trim();
			String status = star.nextToken().trim();
			String roomNum = star.nextToken().trim();

			Reservation res = new Reservation(reservationCode, guestId, checkInDate, checkOutDate,
			adultNum, childrenNum, status, int room_num)

			dataList.add(res);
	}

	public void SaveDB() {
		// TODO - implement ReservationManager.SaveDB

		List<String> reservationData = new ArrayList<>();

		for (Reservation res : reservation_list_){
			StringBuilder st = new StringBuilder();

			st.append(res.GetReservationCode());
			st.append(SEPARATOR);
			st.append(res.GetGuestId());
			st.append(SEPARATOR);
			st.append(res.GetCheckInDate());
			st.append(SEPARATOR);
			st.append(res.GetCheckOutDate());
			st.append(SEPARATOR);
			st.append(res.GetAdultNum());
			st.append(SEPARATOR);
			st.append(res.GetChildrenNum());
			st.append(SEPARATOR);
			st.append(res.GetStatus());
			st.append(SEPARATOR);
			st.append(res.GetRoomNum());
			
			reservationData.add(st.toString());
		}

		try {
			write(db_filename, reservationData);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}



	public boolean CheckIn(String reservationCode) {
		Reservation res = SearchList(reservationCode);
		if (res != null){
			RemoveFromList(res);
			ReservationStatus status = ReservationStatus.CheckedIn;
			res.SetStatus(status);
			AddToList(res);
			return true;
		}
		return false;
		
	}

	public boolean CheckOut(String reservationCode) {
		Reservation res = SearchList(reservationCode);
		if (res != null){
			RemoveFromList(res);
			ReservationStatus status = ReservationStatus.CheckedOut;
			res.SetStatus(status);
			AddToList(res);
			return true;
		}
		return false;
		
	}

	/**
	 * 
	 * @param reservation
	 */
	public ReservationStatus GetReservationStatus(Reservation reservation) {
		Reservation res = SearchList(reservation.GetReservationCode());
		return res.status_;
	}
}