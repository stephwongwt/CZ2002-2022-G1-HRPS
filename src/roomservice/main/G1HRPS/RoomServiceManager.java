package main.G1HRPS;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.UUID;
import java.util.StringTokenizer;

/**
 * Implements the Supermanager class to handle all the room service orders made
 * by guests.
 * 
 * @author Yeo Hong Wei
 */
public class RoomServiceManager extends DatabaseHandler implements Supermanager<RoomServiceOrder> {
	/**
	 * Internally stored list of room service orders.
	 */
	private List<RoomServiceOrder> room_service_order_list_;

	/**
	 * Filename of database file.
	 */
	private final String DB_FILENAME = "roomserviceorder_db.txt";

	/**
	 * Constructor for room service manager
	 * 
	 */
	public RoomServiceManager() {
		this.room_service_order_list_ = new ArrayList<RoomServiceOrder>();
	}

	/**
	 * Creates new RoomServiceOrder and adds it to internal list.
	 * 
	 * @param guest_id          Guest making this order
	 * @param room_number       Room number of guest
	 * @param ordered_item_list List of ordered menu items
	 * @param remarks           Remarks given by guest for this order
	 * @return RoomServiceOrder object if successful, else null
	 */
	public RoomServiceOrder CreateNewRoomServiceOrder(String guest_id, int room_number,
			List<MenuItem> ordered_item_list, String remarks) {
		RoomServiceOrder new_room_service_order = new RoomServiceOrder(guest_id, room_number, ordered_item_list,
				remarks);
		if (AddToList(new_room_service_order)) {
			return new_room_service_order;
		} else {
			return null;
		}
	}

	/**
	 * Adds a room service order to the order list.
	 * 
	 * @param room_service_order obj to be added
	 * @return true if success / false if failed
	 */
	@Override
	public boolean AddToList(RoomServiceOrder room_service_order) {
		boolean success = false;
		RoomServiceOrder found = SearchList(room_service_order.GetRoomServiceOrderCode().toString());
		if (found == null) {
			try {
				success = this.room_service_order_list_.add(room_service_order);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Removes a room service order to the order list.
	 * 
	 * @param room_service_order obj to be removed
	 * @return true if success / false if failed
	 */
	@Override
	public boolean RemoveFromList(RoomServiceOrder room_service_order) {
		boolean success = false;
		RoomServiceOrder found = SearchList(room_service_order.GetRoomServiceOrderCode().toString());
		if (found != null) {
			try {
				success = this.room_service_order_list_.remove(found);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Method overrides the SearchList(String search_text) in Supermanager interface
	 * and returns a RoomServiceOrder with room_num matching search text.
	 * 
	 * @param search_text This is the input that user keys in
	 * @return Room if found, else null
	 */
	@Override
	public RoomServiceOrder SearchList(String search_text) {
		for (RoomServiceOrder room_service_order : room_service_order_list_) {
			if (room_service_order.GetRoomServiceOrderCode() == UUID.fromString(search_text)) {
				return room_service_order;
			}
		}
		return null;
	}

	/**
	 * Returns the full list of room service orders.
	 * 
	 * @return List of room service orders
	 */
	public List<RoomServiceOrder> GetList() {
		return this.room_service_order_list_;
	}

	/**
	 * Reads the database file to initialize the internal list with objects.
	 */
	@Override
	public void InitializeDB() {
		// read String from text file
		ArrayList<String> dbArray = (ArrayList) read(DB_FILENAME);
		ArrayList<RoomServiceOrder> dataList = new ArrayList<RoomServiceOrder>();
		for (String st : dbArray) {
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer
			UUID room_service_order_code = UUID.fromString(star.nextToken().trim()); // first token
			String guest_id = star.nextToken().trim(); // second token
			int room_number = Integer.parseInt(star.nextToken().trim());
			String time_created = star.nextToken().trim();
			String time_completed = star.nextToken().trim();
			int quantity = Integer.parseInt(star.nextToken().trim());
			List<MenuItem> ordered_item_list = new ArrayList<MenuItem>();
			for (int j = 0; j < quantity; j++) {
				String name = star.nextToken().trim();
				float price = Float.parseFloat(star.nextToken().trim());
				String description = star.nextToken().trim();
				MenuItem m = new MenuItem(name, price, description);
				ordered_item_list.add(m);
			}
			String remarks = star.nextToken().trim();
			RoomServiceOrderStatus status = RoomServiceOrderStatus.valueOf(star.nextToken().trim());
			// create object from file data
			RoomServiceOrder room_service_order = new RoomServiceOrder(room_service_order_code, guest_id, room_number, time_created,
					time_completed, quantity, ordered_item_list, remarks, status);
			// add to Room service order list
			dataList.add(room_service_order);
		}
		this.room_service_order_list_ = dataList;
	}

	/**
	 * Saves all objects in the internal list into the database file.
	 */
	@Override
	public void SaveDB() {
		List<String> rsoData = new ArrayList<String>();
		for (RoomServiceOrder room_service_order : room_service_order_list_) {
			StringBuilder st = new StringBuilder();
			int quantity = room_service_order.GetOrderQuantity();
			st.append(room_service_order.GetRoomServiceOrderCode());
			st.append(SEPARATOR);
			st.append(room_service_order.GetGuestId());
			st.append(SEPARATOR);
			st.append(room_service_order.GetRoomNum());
			st.append(SEPARATOR);
			st.append(room_service_order.GetTimeCreated());
			st.append(SEPARATOR);
			st.append(room_service_order.GetTimeCompleted());
			st.append(SEPARATOR);
			st.append(quantity);
			st.append(SEPARATOR);
			for (int i = 0; i < quantity; i++) {
				st.append(room_service_order.GetOrderedItemList().get(i).GetName());
				st.append(SEPARATOR);
				st.append(room_service_order.GetOrderedItemList().get(i).GetPrice());
				st.append(SEPARATOR);
				st.append(room_service_order.GetOrderedItemList().get(i).GetDescription());
				st.append(SEPARATOR);
			}
			st.append(SEPARATOR);
			st.append(room_service_order.GetRemarks());
			st.append(SEPARATOR);
			st.append(room_service_order.GetStatus().toString());
			rsoData.add(st.toString());
		}
		try {
			write(DB_FILENAME, rsoData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets order status for the room service order.
	 * 
	 * @param room_service_order Object to set status
	 * @param new_status Status to be set
	 */
	public void SetRoomServiceRoomServiceOrderStatus(RoomServiceOrder room_service_order, RoomServiceOrderStatus new_status) {
		room_service_order.SetStatus(new_status);
	}

	/**
	 * Gets the Order status of the room service order.
	 * 
	 * @param room_service_order Object to get status
	 * @return RoomServiceOrderStatus status of room service order
	 */
	public RoomServiceOrderStatus GetRoomServiceRoomServiceOrderStatus(RoomServiceOrder room_service_order) {
		return room_service_order.GetStatus();
	}

	/**
	 * Returns list of room service order object based on room id.
	 * 
	 * @param room_number Number of room that ordered room service
	 * @return List of RoomServiceOrders of interest
	 */
	public ArrayList<RoomServiceOrder> GetOrderedItemsByRoom(int room_number) {
		ArrayList<RoomServiceOrder> room_service_orders = new ArrayList<>();
		for (RoomServiceOrder order : this.room_service_order_list_) {
			if (order.GetRoomNum() == room_number) {
				room_service_orders.add(order);
			}
		}
		return room_service_orders;
	}

	/**
	 * Returns the room service order object based on guest id.
	 * 
	 * @param guest_id ID of guest that ordered room service
	 * @return RoomServiceOrder of interest
	 */
	public ArrayList<RoomServiceOrder> GetOrderedItemsByGuest(String guest_id) {
		ArrayList<RoomServiceOrder> room_service_orders = new ArrayList<>();
		for (RoomServiceOrder order : this.room_service_order_list_) {
			if (order.GetGuestId() == guest_id) {
				room_service_orders.add(order);
			}
		}
		return room_service_orders;
	}
}