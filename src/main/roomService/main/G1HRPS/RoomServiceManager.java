package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Manages room service orders from guests
 */
public class RoomServiceManager implements Supermanager<RoomServiceOrder>, CodeGen {

	private List<RoomServiceOrder> room_service_order_list;
	
	/**
	 * Constructor for room service manager
	 * 
	 */
	public RoomServiceManager() {
		this.room_service_order_list = new ArrayList<RoomServiceOrder>();
	}

	/**
	 * Adds a room service order to the order list
	 * 
	 * @param room_service_order
	 */
	public void AddToList(RoomServiceOrder room_service_order) {
		this.room_service_order_list.add(room_service_order);
	}

	/**
	 * Removes a room service order to the order list
	 * 
	 * @param room_service_order the order to be removed
	 */
	public void RemoveFromList(RoomServiceOrder rooms_service_order) {
		this.room_service_order_list.remove(rooms_service_order);
	}

	public RoomServiceOrder SearchList(String search_text) {
		// TODO - implement RoomServiceManager.SearchList
		throw new UnsupportedOperationException();
	}

	
	/**
	 * Returns the full list of room service orders
	 * 
	 * @return List of room service orders
	 */
	public List<RoomServiceOrder> GetList() {
		// TODO - implement RoomServiceManager.GetList
		return this.room_service_order_list;
	}

	public void InitializeDB() {
		// TODO - implement RoomServiceManager.InitializeDB
		throw new UnsupportedOperationException();
	}

	public void SaveDB() {
		// TODO - implement RoomServiceManager.SaveDB
		throw new UnsupportedOperationException();
		
	}

	/**
	 * Sets order status for the room service order
	 * 
	 * @param rso
	 * @param new_status
	 */
	public void SetRsoStatus(RoomServiceOrder rso, OrderStatus new_status) {
		rso.SetStatus(new_status);
		
	}

	/**
	 * Gets the Order status of the room service order
	 * 
	 * @param rso
	 * @return OrderStatus on the service order status 
	 */
	public OrderStatus GetRsoStatus(RoomServiceOrder rso) {
		
		return rso.GetStatus();
	}

	/**
	 * Returns the room service order object based on room id
	 * 
	 * @param room_id
	 * @return RoomServiceOrder of interest
	 */
	public RoomServiceOrder GetOrderedItemsByRoom(int room_id) {
		// TODO - implement RoomServiceManager.GetOrderedItemsByRoom
		for (RoomServiceOrder rso : room_service_order_list)
		{
			if (rso.GetRoomNum() == room_id)
			{
				return rso;
			}
		}
		System.out.println("No order from room number of " + room_id);
		return null;
	}

	/**
	 * Returns the room service order object based on guest id
	 * 
	 * @param guest_id
	 * @return RoomServiceOrder of interest
	 */
	public RoomServiceOrder GetOrderedItemsByGuest(String guest_id) {
		// TODO - implement RoomServiceManager.GetOrderedItemsByGuest
		for (RoomServiceOrder rso : room_service_order_list)
		{
			if (rso.getGuest_id() == guest_id)
			{
				return rso;
			}
		}
		System.out.println("No room number of " + guest_id);
		return null;
	}

	@Override
	public UUID GenerateCode() {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		return uuid;
	}
}