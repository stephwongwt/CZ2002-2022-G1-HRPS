package main.G1HRPS;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReservationManager implements Supermanager<Reservation>, CodeGen {

	private List<Reservation> reservation_list_;

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
	}

	public void SaveDB() {
		// TODO - implement ReservationManager.SaveDB
	}

	/**
	 * 
	 * @param reservation
	 * @param new_status
	 */

	 //change to check in

	 //add "to check out//
	public boolean SetReservationStatus(Reservation reservation, ReservationStatus new_status) {
		Reservation res = SearchList(reservation.GetReservationCode());
		if (res != null){
			RemoveFromList(reservation);
			res.SetStatus(new_status);
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

	@Override
	public UUID GenerateCode() {
		// TODO Auto-generated method stub
		return null;
	}

}