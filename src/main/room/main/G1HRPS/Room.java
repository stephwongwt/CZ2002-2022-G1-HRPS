package main.G1HRPS;

import java.util.List;

public class Room {

	private int room_num_;
	private RoomType room_type_;
	private float room_price_;
	private BedSize bedsize_;
	private boolean wifi_enabled_;
	private boolean w_view_;
	private boolean w_smoking_;
	private RoomStatus status_;
	private List<Guest> guest_list_;

	/**
	 * 
	 * @param room_type
	 * @param price
	 * @param room_number
	 * @param bedSize
	 * @param wifiEnabled
	 * @param withView
	 * @param Smoking
	 * @param status
	 */
	public Room(RoomType room_type, float price, int room_number, String bedSize, boolean wifiEnabled, boolean withView, boolean Smoking, RoomStatus status) {
		// TODO - implement Room.Room
		throw new UnsupportedOperationException();
	}

}