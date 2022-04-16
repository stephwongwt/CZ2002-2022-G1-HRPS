package main.G1HRPS;

import java.util.ArrayList;

/**
 * This is a Room class object, containing details on a hotel room
 *
 * @author LiangHee
 *
 */
public class Room {
	private int room_number_;
	private RoomType room_type_;
	private float room_price_;
	private BedSize bedsize_;
	private boolean wifi_enabled_;
	private boolean w_view_;
	private boolean w_smoking_;
	private RoomStatus status_;
	private ArrayList<Guest> guest_list_ = new ArrayList<Guest>();

	/**
	 * Creates a Room object to store information about the room.
	 *
	 * @param room_number  This is room number of the room
	 * @param room_type    The room types are Single, Standard, VIP, Suite, Deluxe.
	 * @param room_price   This is price of the room per night
	 * @param bed_size     Bed size of bed in room
	 * @param wifi_enabled Whether a room has wifi or not
	 * @param w_view       Whether a room has view or not
	 * @param w_smoking    Whether a room allows smoking or not
	 * @param status       Vacant, Occupied, Reserved, Maintenance
	 */
	public Room(int room_number, RoomType room_type, float room_price, BedSize bed_size, boolean wifi_enabled,
			boolean w_view, boolean w_smoking, RoomStatus status) {
		room_number_ = room_number;
		room_type_ = room_type;
		room_price_ = room_price;
		bedsize_ = bed_size;
		wifi_enabled_ = wifi_enabled;
		w_view_ = w_view;
		w_smoking_ = w_smoking;
		status_ = status;
	}

	/**
	 * Get room number.
	 *
	 * @return room number of this room
	 */
	public int GetRoomNumber() {
		return this.room_number_;
	}

	/**
	 * Get room type.
	 *
	 * @return RoomType of this room
	 */
	public RoomType GetRoomType() {
		return this.room_type_;
	}

	/**
	 * Get room price.
	 *
	 * @return room price of this room
	 */
	public float GetRoomPrice() {
		return this.room_price_;
	}

	/**
	 * Get bed size.
	 *
	 * @return BedSize of this room
	 */
	public BedSize GetBedSize() {
		return this.bedsize_;
	}

	/**
	 * Get wifi enabled.
	 *
	 * @return if room has wifi
	 */
	public Boolean GetWifi() {
		return this.wifi_enabled_;
	}

	/**
	 * Get view.
	 *
	 * @return if room has view
	 */
	public Boolean GetView() {
		return this.w_view_;
	}

	/**
	 * Get smooking.
	 *
	 * @return if room allows smoking
	 */
	public Boolean GetSmoking() {
		return this.w_smoking_;
	}

	/**
	 * Get room status.
	 *
	 * @return room status of this room
	 */
	public RoomStatus GetStatus() {
		return this.status_;
	}

	/**
	 * Get whole guest list.
	 *
	 * @return guest list of this room
	 */
	public ArrayList<Guest> GetGuestList() {
		return this.guest_list_;
	}

	/**
	 * Adds a guest to the guest list of this room.
	 *
	 * @param guest object to be added to this room
	 */
	public void AddGuestToRoom(Guest guest) {
		this.guest_list_.add(guest);
	}

	/**
	 * Removes one guest from list.
	 * 
	 * @param guest_name name of guest to be removed
	 */
	public void RemoveGuestFromRoom(String guest_name) {
		for (Guest guest : guest_list_) {
			if (guest.GetName().equals(guest_name)) {
				this.guest_list_.remove(guest);
			}
		}
	}

	/**
	 * Removes all guests from the list.
	 */
	public void ClearGuestList() {
		this.guest_list_.clear();
	}

	/**
	 * Set room num.
	 * 
	 * @param room_num
	 */
	public void SetRoomNumber(int room_num) {
		this.room_number_ = room_num;
	}

	/**
	 * Set room type.
	 * 
	 * @param room_type
	 */
	public void SetRoomType(RoomType room_type) {
		this.room_type_ = room_type;
	}

	/**
	 * Set room price.
	 * 
	 * @param room_price
	 */
	public void SetRoomPrice(float room_price) {
		this.room_price_ = room_price;
	}

	/**
	 * Set room bedsize.
	 * 
	 * @param bedsize
	 */
	public void SetBedSize(BedSize bedsize) {
		this.bedsize_ = bedsize;
	}

	/**
	 * Set wifi.
	 * 
	 * @param wifi_enabled
	 */
	public void SetWifi(boolean wifi_enabled) {
		this.wifi_enabled_ = wifi_enabled;
	}

	/**
	 * Set view.
	 * 
	 * @param w_view
	 */
	public void SetView(boolean w_view) {
		this.w_view_ = w_view;
	}

	/**
	 * Set smoking.
	 * 
	 * @param w_smoking
	 */
	public void SetSmoking(boolean w_smoking) {
		this.w_smoking_ = w_smoking;
	}

	/**
	 * Set room status.
	 *
	 * @param status Status to be changed into
	 */
	public void SetStatus(RoomStatus status) {
		this.status_ = status;
	}

	/**
	 * Set guest list.
	 * 
	 * @param guest_list
	 */
	public void SetGuestList(ArrayList<Guest> guest_list) {
		this.guest_list_ = guest_list;
	}

	/**
	 * Prints out general information of the room
	 */
	@Override
	public String toString() {
		String output = "|Room Number: " + this.room_number_ + "|Room Price: " + this.room_price_ + "|\n|Bed Size: "
				+ this.bedsize_ + "|wifi Enabled: " + this.wifi_enabled_ + "|with View: " + this.w_view_
				+ "|\n|with Smoking: " + this.w_smoking_ + "|Room Status: " + this.status_ + "|\n|Guests: \n"
				+ this.guest_list_.toString();
		return output;
	}
}