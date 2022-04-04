package main.G1HRPS;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 * Manages room service orders from guests
 */
public class RoomServiceManager implements Supermanager<RoomServiceOrder>, CodeGen {

	private List<RoomServiceOrder> room_service_order_list;

	public RoomServiceManager() {
		// TODO - implement RoomServiceManager.RoomServiceManager
		this.room_service_order_list = new ArrayList<RoomServiceOrder>();
		InitializeDB();
	}

	/**
	 * Takes in an class object and list to add the object into.
	 */
	public void AddToList(RoomServiceOrder room_service_order) {
		// TODO - implement RoomServiceManager.AddToList
		this.room_service_order_list.add(room_service_order);
	}

	/**
	 * Takes in an class object and list to remove the object from the given list.
	 */
	public void RemoveFromList(RoomServiceOrder rooms_service_order) {
		// TODO - implement RoomServiceManager.RemoveFromList
		this.room_service_order_list.remove(rooms_service_order);
	}

	public void SearchList(String search_text) {
		// TODO - implement RoomServiceManager.SearchList
		for (int i = 0; i < this.room_service_order_list.size(); i++)
		{
			if (this.room_service_order_list.get(i).getGuest_id() == search_text)
			{
				System.out.println(this.room_service_order_list.get(i));
				return;
			}
		}
		
	}

	/**
	 * Print a full list of room service orders
	 */
	public List<RoomServiceOrder> GetList() {
		// TODO - implement RoomServiceManager.GetList
		return this.room_service_order_list;
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
		rso.SetStatus(new_status);
		
	}

	/**
	 * 
	 * @param rso
	 */
	public OrderStatus GetRsoStatus(RoomServiceOrder rso) {
		// TODO - implement RoomServiceManager.GetRsoStatus
		return rso.GetStatus();
	}

	/**
	 * 
	 * @param room_id
	 */
	public void GetOrderedItemsByRoom(int room_id) {
		// TODO - implement RoomServiceManager.GetOrderedItemsByRoom
		for (RoomServiceOrder rso : room_service_order_list)
		{
			if (rso.GetRoomNum() == room_id)
			{
				System.out.println("Ordered items for room " + room_id);
				for (MenuItem m : rso.GetOrderedItemList())
				{
					System.out.println(m);
				}
				return;
			}
		}
		System.out.println("No room number of " + room_id);
		
	}

	/**
	 * 
	 * @param guest_id
	 */
	public void GetOrderedItemsByGuest(String guest_id) {
		// TODO - implement RoomServiceManager.GetOrderedItemsByGuest
		for (RoomServiceOrder rso : room_service_order_list)
		{
			if (rso.getGuest_id() == guest_id)
			{
				System.out.println("Ordered items for guest " + guest_id);
				for (MenuItem m : rso.GetOrderedItemList())
				{
					System.out.println(m);
				}
				return;
			}
		}
		System.out.println("No room number of " + guest_id);
	}

	@Override
	public UUID GenerateCode() {
		// TODO Auto-generated method stub
		return null;
	}
}