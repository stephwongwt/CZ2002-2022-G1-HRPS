package main.G1HRPS;

import java.util.List;

public class ReservationManager implements Supermanager<Reservation>, CodeGen {

	private List<Reservation> reservation_list_;

	public ReservationManager() {
		// TODO - implement ReservationManager.ReservationManager
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Reservation reservation) {
		// TODO - implement ReservationManager.AddToList
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Reservation reservation) {
		// TODO - implement ReservationManager.RemoveFromList
		throw new UnsupportedOperationException();
	}

	public void SearchList(String search_text) {
		// TODO - implement ReservationManager.SearchList
		throw new UnsupportedOperationException();
	}

	public List<Reservation> GetList() {
		// TODO - implement ReservationManager.GetList
		throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement ReservationManager.InitializeDB
		throw new UnsupportedOperationException();
	}

	public void SaveDB() {
		// TODO - implement ReservationManager.SaveDB
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param reservation
	 * @param new_status
	 */
	public boolean SetReservationStatus(Reservation reservation, ReservationStatus new_status) {
		// TODO - implement ReservationManager.SetReservationStatus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param reservation
	 */
	public ReservationStatus GetReservationStatus(Reservation reservation) {
		// TODO - implement ReservationManager.GetReservationStatus
		throw new UnsupportedOperationException();
	}

	@Override
	public String GenerateCode(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

}