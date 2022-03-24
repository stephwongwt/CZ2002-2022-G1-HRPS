package main.G1HRPS;

import java.sql.Timestamp;

public class Reservation {

	private final String reservation_code_;
	private String guest_id_;
	public Timestamp check_in_date;
	public Timestamp check_out_date;
	public int adult_num_;
	public int children_num;
	public ReservationStatus status_;
	private int room_num_;

	/**
	 * 
	 * @param reservation_code
	 * @param guest
	 * @param room
	 * @param check_in_date
	 * @param check_out_date
	 * @param adult_num
	 * @param children_num
	 * @param status
	 */
	public Reservation(String reservation_code, Guest guest, Room room, Timestamp check_in_date, Timestamp check_out_date, int adult_num, int children_num, ReservationStatus status) {
		// TODO - implement Reservation.Reservation
		throw new UnsupportedOperationException();
	}

	public int GetRoomNum() {
		// TODO - implement Reservation.GetRoomNum
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {
		// TODO - implement Reservation.SetRoomNum
		throw new UnsupportedOperationException();
	}

	public String GetGuestId() {
		// TODO - implement Reservation.GetGuestId
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param guest_id
	 */
	public void SetGuestId(String guest_id) {
		// TODO - implement Reservation.SetGuestId
		throw new UnsupportedOperationException();
	}

}