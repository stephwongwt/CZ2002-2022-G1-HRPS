package main.G1HRPS;

import java.util.ArrayList;

/**
 * This is a Room class object, containing details on a hotel room
 *
 * @author LiangHee
 *
 */
public class Room {
	/**
	 * Unique room number.
	 */
	private int room_number_;

	/**
	 * Room type.
	 */
	private RoomType room_type_;

	/**
	 * Room price.
	 */
	private float room_price_;

	/**
	 * Size of bed in room.
	 */
	private BedSize bedsize_;

	/**
	 * If WIFI is provided in room.
	 */
	private boolean wifi_enabled_;

	/**
	 * If room comes with a view.
	 */
	private boolean with_view_;

	/**
	 * If room allows smoking.
	 */
	private boolean with_smoking_;

	/**
	 * Status of room.
	 */
	private RoomStatus status_;

	/**
	 * List of guest occupying room.
	 */
	private ArrayList<Guest> guest_list_ = new ArrayList<Guest>();

	/**
	 * Creates a Room object to store information about the room.
	 *
	 * @param room_number  This is room number of the room
	 * @param room_type    The room types are Single, Standard, VIP, Suite, Deluxe.
	 * @param room_price   This is price of the room per night
	 * @param bed_size     Bed size of bed in room
	 * @param wifi_enabled Whether a room has wifi or not
	 * @param with_view    Whether a room has view or not
	 * @param with_smoking Whether a room allows smoking or not
	 * @param status       Vacant, Occupied, Reserved, Maintenance
	 */
	public Room(int room_number, RoomType room_type, float room_price, BedSize bed_size, boolean wifi_enabled,
			boolean with_view, boolean with_smoking, RoomStatus status) {
		room_number_ = room_number;
		room_type_ = room_type;
		room_price_ = room_price;
		bedsize_ = bed_size;
		wifi_enabled_ = wifi_enabled;
		with_view_ = with_view;
		with_smoking_ = with_smoking;
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
		return this.with_view_;
	}

	/**
	 * Get smooking.
	 *
	 * @return if room allows smoking
	 */
	public Boolean GetSmoking() {
		return this.with_smoking_;
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
	 * Set room number.
	 * 
	 * @param room_num Room number to be set.
	 */
	public void SetRoomNumber(int room_num) {
		this.room_number_ = room_num;
	}

	/**
	 * Set room type.
	 * 
	 * @param room_type Room type to be set.
	 */
	public void SetRoomType(RoomType room_type) {
		this.room_type_ = room_type;
	}

	/**
	 * Set room price.
	 * 
	 * @param room_price Room price to be set.
	 */
	public void SetRoomPrice(float room_price) {
		this.room_price_ = room_price;
	}

	/**
	 * Set room bed size.
	 * 
	 * @param bedsize Bed size to be set.
	 */
	public void SetBedSize(BedSize bedsize) {
		this.bedsize_ = bedsize;
	}

	/**
	 * Set WIFI.
	 * 
	 * @param wifi_enabled true if WIFI is provided, else false
	 */
	public void SetWifi(boolean wifi_enabled) {
		this.wifi_enabled_ = wifi_enabled;
	}

	/**
	 * Set view.
	 * 
	 * @param with_view true if room has view, else false
	 */
	public void SetView(boolean with_view) {
		this.with_view_ = with_view;
	}

	/**
	 * Set smoking.
	 * 
	 * @param with_smoking true if smoking is allowed, else false
	 */
	public void SetSmoking(boolean with_smoking) {
		this.with_smoking_ = with_smoking;
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
	 * @param guest_list List of guest staying in room
	 */
	public void SetGuestList(ArrayList<Guest> guest_list) {
		this.guest_list_ = guest_list;
	}

	/**
	 * Prints out general information of the room
	 */
	@Override
	public String toString() {
		String output = String.format(
				"| Room Number: %04d | Room Price: %.2f |\n| Bed Size: %s | wifi Enabled: %s |\n| with View: %s |with Smoking: %s |\n| Room Status: %s | Guests: \n%s\n",
				this.room_number_, this.room_price_, this.bedsize_, this.wifi_enabled_, this.with_view_,
				this.with_smoking_, this.status_, this.guest_list_.toString());
		return output;
	}
}