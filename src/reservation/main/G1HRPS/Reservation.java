package main.G1HRPS;

import java.util.UUID;

/**
 * Reservation class for storing the info of each of the reservations.
 * 
 * @author Chris Lim Qing Chuan
 *
 */
public class Reservation {
	/**
	 * Unique generated ID.
	 */
	private final UUID reservation_code_;
	
	/**
	 * Guest that made this reservation.
	 */
	private String guest_id_;
	
	/**
	 * Selected check in date.
	 */
	public String check_in_date_;
	
	/**
	 * Selected check out date.
	 */
	public String check_out_date_;
	
	/**
	 * Number of adults.
	 */
	public int adult_num_;
	
	/**
	 * Number of children.
	 */
	public int children_num_;
	
	/**
	 * Status of reservation.
	 */
	public ReservationStatus status_;
	
	/**
	 * Reserved room number. 
	 */
	private int room_num_;

	/**
	 * Payment Constructor. Used when initializing from database.
	 * 
	 * @param reservation_code ID in UUID format, must be unique from other objects.
	 * @param guest_id         Guest ID of guest that made reservations
	 * @param check_in_date    Date the guest would like to check in
	 * @param check_out_date   Date the guest would like to check out
	 * @param adult_num        Number of adults
	 * @param children_num     Number of children
	 * @param status           Status of Reservation
	 * @param room_num         Reserved room number for this reservation
	 */
	public Reservation(UUID reservation_code, String guest_id, String check_in_date, String check_out_date,
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

	/**
	 * Constructor to be used when creating new reservation when in operation.
	 * 
	 * @param guest_id         Guest ID of guest that made reservations
	 * @param check_in_date    Date the guest would like to check in
	 * @param check_out_date   Date the guest would like to check out
	 * @param adult_num        Number of adults
	 * @param children_num     Number of children
	 * @param status           Status of Reservation
	 * @param room_num         Reserved room number for this reservation
	 */
	public Reservation(String guest_id, String check_in_date, String check_out_date, int adult_num, int children_num,
			ReservationStatus status, int room_num) {
		this.reservation_code_ = UniqueIdGenerator.Generate();
		this.guest_id_ = guest_id;
		this.check_in_date_ = check_in_date;
		this.check_out_date_ = check_out_date;
		this.adult_num_ = adult_num;
		this.children_num_ = children_num;
		this.status_ = status;
		this.room_num_ = room_num;
	}

	/**
	 * Sets the guest id for this reservation.
	 * 
	 * @param guest_id String to be set.
	 */
	public void SetGuestId(String guest_id) {
		this.guest_id_ = guest_id;
	}

	/**
	 * Sets check in date for this reservation.
	 * 
	 * @param check_in_date LocalDateTime in String format
	 */
	public void SetCheckInDate(String check_in_date) {
		this.check_in_date_ = check_in_date;
	}

	/**
	 * Sets check out date for this reservation.
	 * 
	 * @param check_out_date LocalDateTime in String format
	 */
	public void SetCheckOutDate(String check_out_date) {
		this.check_out_date_ = check_out_date;
	}

	/**
	 * Sets number of adults for this reservation.
	 * 
	 * @param adult_num Number of adults to set.
	 */
	public void SetAdultNum(int adult_num) {
		this.adult_num_ = adult_num;
	}

	/**
	 * Sets number of children for this reservation.
	 * 
	 * @param children_num Number of children to set.
	 */
	public void SetChildrenNum(int children_num) {
		this.children_num_ = children_num;
	}

	/**
	 * Sets status of this reservation.
	 * 
	 * @param status ReservationStatus to be set.
	 */
	public void SetStatus(ReservationStatus status) {
		this.status_ = status;
	}

	/**
	 * Sets room number of this reservation.
	 * 
	 * @param room_num Integer to be set.
	 */
	public void SetRoomNum(int room_num) {
		this.room_num_ = room_num;
	}

	/**
	 * Gets reservation code.
	 * 
	 * @return UUID Reservation code
	 */
	public UUID GetReservationCode() {
		return this.reservation_code_;
	}

	/**
	 * Gets guest id.
	 * 
	 * @return String Guest id
	 */
	public String GetGuestId() {
		return this.guest_id_;
	}

	/**
	 * Gets check in date.
	 * 
	 * @return String Check in date
	 */
	public String GetCheckInDate() {
		return this.check_in_date_;
	}

	/**
	 * Gets check out date.
	 * 
	 * @return String Check out date
	 */
	public String GetCheckOutDate() {
		return this.check_out_date_;
	}

	/**
	 * Gets number of adults.
	 * 
	 * @return number of adults in Integer
	 */
	public int GetAdultNum() {
		return this.adult_num_;
	}

	/**
	 * Gets number of children.
	 * 
	 * @return number of children in Integer
	 */
	public int GetChildrenNum() {
		return this.children_num_;
	}

	/**
	 * Gets reservation status.
	 * 
	 * @return ReservationStatus enumerator.
	 */
	public ReservationStatus GetStatus() {
		return this.status_;
	}

	/**
	 * Gets room number.
	 * 
	 * @return Room number in Integer
	 */
	public int GetRoomNum() {
		return this.room_num_;
	}

	/**
	 * Overrides toString method to print reservation object.
	 * @return Formatted String to be printed
	 */
	@Override
	public String toString() {
		String output = "|Code: " + this.reservation_code_ + "|Guest Id: " + this.guest_id_ + "|\n|Check In Date: "
				+ this.check_in_date_ + "|Check Out Date: " + this.check_out_date_ + "|\n|Room Number: "
				+ this.room_num_ + "|\n|Number of Guests: " + (this.adult_num_ + this.children_num_) + "|\n|Status:"
				+ this.status_ + "|";
		return output;
	}
}