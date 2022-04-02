package main.G1HRPS;

import java.util.List;
import java.util.UUID;

/**
 * Manages room service orders from guests
 */
public class RoomServiceManager implements Supermanager<RoomServiceOrder>, CodeGen {

	private List<RoomServiceOrder> room_service_order_list;

	public RoomServiceManager() {
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(RoomServiceOrder rooms_ervice_order) {
		// TODO - implement RoomServiceManager.AddToList
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(RoomServiceOrder rooms_ervice_order) {
		// TODO - implement RoomServiceManager.RemoveFromList
		throw new UnsupportedOperationException();
	}

	public void SearchList(String search_text) {
		// TODO - implement RoomServiceManager.SearchList
		throw new UnsupportedOperationException();
	}

	/**
	 * Print a full list of room service orders
	 */
	public List<RoomServiceOrder> GetList() {
		// TODO - implement RoomServiceManager.GetList
		throw new UnsupportedOperationException();
	}

	public void InitializeDB() {
		// TODO - implement RoomServiceManager.InitializeDB
	}

	public void SaveDB() {
		// TODO - implement RoomServiceManager.SaveDB
	}

	/**
	 * 
	 * @param rso
	 * @param new_status
	 */
	public void SetRsoStatus(RoomServiceOrder rso, OrderStatus new_status) {
		// TODO - implement RoomServiceManager.SetRsoStatus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param rso
	 */
	public OrderStatus GetRsoStatus(RoomServiceOrder rso) {
		// TODO - implement RoomServiceManager.GetRsoStatus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room_id
	 */
	public void GetOrderedItemsByRoom(int room_id) {
		// TODO - implement RoomServiceManager.GetOrderedItemsByRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param guest_id
	 */
	public void GetOrderedItemsByGuest(int guest_id) {
		// TODO - implement RoomServiceManager.GetOrderedItemsByGuest
		throw new UnsupportedOperationException();
	}

	@Override
	public UUID GenerateCode() {
		// TODO Auto-generated method stub
		return null;
	}
}