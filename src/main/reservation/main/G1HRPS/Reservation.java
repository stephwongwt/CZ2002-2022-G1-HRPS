package main.G1HRPS;

import java.sql.Timestamp;

public class Reservation {

	private final String reservation_code_;
	private String guest_id_;
	public Timestamp check_in_date_;
	public Timestamp check_out_date_;
	public int adult_num_;
	public int children_num_;
	public ReservationStatus status_;
	private int room_num_;

	/**
	 * 
	 * @param reservationCode
	 * @param guest
	 * @param room
	 * @param checkInDate
	 * @param checkOutDate
	 * @param adultNum
	 * @param childrenNum
	 * @param status
	 */
	public Reservation(String reservation_code, String guest_id, Timestamp check_in_date, Timestamp check_out_date,
			int adult_num, int children_num, ReservationStatus status, int room_num) {
		this.reservation_code_ = reservation_code;
		this.guest_id_ = guest_id;
		this.check_in_date_ = check_in_date;
		this.check_out_date_ = check_out_date;
		this.adult_num_ = adult_num;
		this.children_num_ = children_num;
		this.status_ = status;
		this.room_num_ = room_num;
	}

	public void SetGuestId(String guest_id) {
		this.guest_id_ = guest_id;
	}

	public void SetCheckInDate(Timestamp check_in_date) {
		this.check_in_date_ = check_in_date;
	}

	public void SetCheckOutDate(Timestamp check_out_date) {
		this.check_out_date_ = check_out_date;
	}

	public void SetAdultNum(int adult_num) {
		this.adult_num_ = adult_num;
	}

	public void SetChildrenNum(int children_num) {
		this.children_num_ = children_num;
	}

	public void SetStatus(ReservationStatus status) {
		this.status_ = status;
	}

	public void GetRoomNum(int room_num) {
		this.room_num_ = room_num;
	}

	// String reservationCode, String guestId, Timestamp checkInDate, Timestamp
	// checkOutDate,
	// int adultNum, int childrenNum, ReservationStatus status, int roomNum
	public String GetReservationCode() {
		return reservation_code_;
	}

	public String GetGuestId() {
		return guest_id_;
	}

	public Timestamp GetCheckInDate() {
		return check_in_date_;
	}

	public Timestamp GetCheckOutDate() {
		return check_out_date_;
	}

	public int GetAdultNum() {
		return adult_num_;
	}

	public int GetChildrenNum() {
		return children_num_;
	}

	public ReservationStatus GetStatus() {
		return status_;
	}

	public int GetRoomNum() {
		return room_num_;
	}

}