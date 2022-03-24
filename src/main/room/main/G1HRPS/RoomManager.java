package main.G1HRPS;

import java.util.List;

public class RoomManager implements Supermanager<Room> {

	private List<Room> room_list_;

	public RoomManager() {
		// TODO - implement RoomManager.RoomManager
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(Room room) {
		// TODO - implement RoomManager.AddToList
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(Room room) {
		// TODO - implement RoomManager.RemoveFromList
		throw new UnsupportedOperationException();
	}

	public void SearchList(String search_text) {
		// TODO - implement RoomManager.SearchList
		throw new UnsupportedOperationException();
	}

	public List<Room> GetList() {
		// TODO - implement RoomManager.GetList
		throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement RoomManager.InitializeDB
		throw new UnsupportedOperationException();
	}

	public void SaveDB() {
		// TODO - implement RoomManager.SaveDB
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param guest
	 * @param room
	 */
	public void CheckInRoom(Guest guest, Room room) {
		// TODO - implement RoomManager.CheckInRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room
	 */
	public void CheckOutRoom(Room room) {
		// TODO - implement RoomManager.CheckOutRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room
	 * @param status
	 */
	public void SetRoomStatus(Room room, RoomStatus status) {
		// TODO - implement RoomManager.SetRoomStatus
		throw new UnsupportedOperationException();
	}

	/**
	 * Occupancy rate of each room type.
	 * e.g. Standard 2/5 occupied, VIP 1/5 occupied
	 */
	public void GetRoomStatisticsByTypeOccupancyRate() {
		// TODO - implement RoomManager.GetRoomStatisticsByTypeOccupancyRate
		throw new UnsupportedOperationException();
	}

	/**
	 * Number of each status.
	 * e.g. Vacant 5, Occupied 3, Maintenance 4
	 */
	public void GetRoomStatisticsByStatus() {
		// TODO - implement RoomManager.GetRoomStatisticsByStatus
		throw new UnsupportedOperationException();
	}

}