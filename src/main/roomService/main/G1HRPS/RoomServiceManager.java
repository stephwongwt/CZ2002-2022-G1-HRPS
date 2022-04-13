package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.UUID;
import java.util.StringTokenizer;

/**
 * Manages room service orders from guests
 */
public class RoomServiceManager extends DatabaseHandler implements Supermanager<RoomServiceOrder>, CodeGen {

	private final String db_filename = "roomserviceorder_db.txt";
	private List<RoomServiceOrder> room_service_order_list_;
	
	/**
	 * Constructor for room service manager
	 * 
	 */
	public RoomServiceManager() {
	}

	/**
	 * Adds a room service order to the order list
	 * 
	 * @param room_service_order
	 */
	public void AddToList(RoomServiceOrder room_service_order) {
		this.room_service_order_list_.add(room_service_order);
	}

	/**
	 * Removes a room service order to the order list
	 * 
	 * @param room_service_order the order to be removed
	 */
	public void RemoveFromList(RoomServiceOrder rooms_service_order) {
		this.room_service_order_list_.remove(rooms_service_order);
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
		return this.room_service_order_list_;
	}

	public void InitializeDB() {
		// read String from text file
		ArrayList dbArray = (ArrayList) read(db_filename);
		ArrayList dataList = new ArrayList();
		

		for (int i = 0; i < dbArray.size(); i++) {
			String st = (String) dbArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer
			
			String rso_code = star.nextToken().trim(); // first token
			String guest_id = star.nextToken().trim(); // second token
			int room_id = Integer.parseInt(star.nextToken().trim());
			String remarks = star.nextToken().trim();
			int quantity = Integer.parseInt(star.nextToken().trim());
			List<MenuItem> rso_items = new ArrayList<MenuItem>();
			for (int j = 0; j < quantity; j++)
			{
				String name = star.nextToken().trim(); 
				float price = Float.parseFloat(star.nextToken().trim());
				String description = star.nextToken().trim();
				MenuItem m = new MenuItem(name, price, description);
				rso_items.add(m);
			}
			
			// create object from file data
			RoomServiceOrder obj = new RoomServiceOrder(rso_code, guest_id, room_id, rso_items, remarks);
			// add to Room service order list
			dataList.add(obj);
		}
		this.room_service_order_list_ = dataList;
	}

	public void SaveDB() {
		List<String> rsoData = new ArrayList<String>();

		for (RoomServiceOrder rso : room_service_order_list_) {
			StringBuilder st = new StringBuilder();
			int quantity = rso.getOrderQuantity();
			st.append(rso.GetRsoCode());
			st.append(SEPARATOR);
			st.append(rso.getGuest_id());
			st.append(SEPARATOR);
			st.append(rso.GetRoomNum());
			st.append(SEPARATOR);
			st.append(rso.GetRemarks());
			st.append(SEPARATOR);
			st.append(quantity);
			st.append(SEPARATOR);
			for(int i = 0; i < quantity; i++)
			{
				st.append(rso.GetOrderedItemList().get(i).getName());
				st.append(SEPARATOR);
				st.append(rso.GetOrderedItemList().get(i).getPrice());
				st.append(SEPARATOR);
				st.append(rso.GetOrderedItemList().get(i).getDescription());
				st.append(SEPARATOR);
			}
			rsoData.add(st.toString());
		}

		try {
			write(db_filename, rsoData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		for (RoomServiceOrder rso : room_service_order_list_)
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
		for (RoomServiceOrder rso : room_service_order_list_)
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