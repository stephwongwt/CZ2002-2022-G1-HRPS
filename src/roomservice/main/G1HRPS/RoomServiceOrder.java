package main.G1HRPS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 * Room Service Order class for storing the info of each of the orders made by
 * guests.
 * 
 * @author Yeo Hong Wei
 */
public class RoomServiceOrder {
	/**
	 * Unique generated ID
	 */
	private UUID room_service_order_code_;

	/**
	 * Guest's driving license or passport number
	 */
	private String guest_id_;

	/**
	 * Room number this order is for.
	 */
	private int room_number_;

	/**
	 * Auto generated time for this order's creation.
	 */
	private final String time_created_;

	/**
	 * Auto generated time to complete this order.
	 */
	private String time_completed_;

	/**
	 * Quantity or menu items ordered. To enable reconstructing of objects from
	 * database file.
	 */
	private int order_quantity_;

	/**
	 * List of menu items ordered.
	 */
	private List<MenuItem> ordered_item_list_;

	/**
	 * Remarks for this order.
	 */
	private String remarks_;

	/**
	 * Status of this order.
	 */
	private RoomServiceOrderStatus status_;

	/**
	 * Constructor for room service order. Used when initializing from database.
	 * 
	 * @param room_service_order_code Unique generated ID
	 * @param guest_id                Guest making this order
	 * @param room_number             Room number of guest
	 * @param time_created            Time order was created
	 * @param time_completed          Time order was completed
	 * @param quantity                Quantity of menu items ordered
	 * @param ordered_item_list       List of ordered menu items
	 * @param remarks                 Remarks given by guest for this order
	 * @param status                  Status of order
	 */
	public RoomServiceOrder(UUID room_service_order_code, String guest_id, int room_number, String time_created,
			String time_completed, int quantity, List<MenuItem> ordered_item_list, String remarks, RoomServiceOrderStatus status) {
		this.room_service_order_code_ = room_service_order_code;
		this.guest_id_ = guest_id;
		this.room_number_ = room_number;
		this.time_created_ = time_created;
		this.time_completed_ = time_completed;
		this.order_quantity_ = quantity;
		this.ordered_item_list_ = new ArrayList<MenuItem>(ordered_item_list);
		this.remarks_ = remarks;
		this.status_ = status;
	}

	/**
	 * Constructor to be used when creating new orders when in operation.
	 * 
	 * @param guest_id          Guest making this order
	 * @param room_number       Room number of guest
	 * @param ordered_item_list List of ordered menu items
	 * @param remarks           Remarks given by guest for this order
	 */
	public RoomServiceOrder(String guest_id, int room_number, List<MenuItem> ordered_item_list, String remarks) {
		this.room_service_order_code_ = UniqueIdGenerator.Generate();
		this.guest_id_ = guest_id;
		this.room_number_ = room_number;
		this.ordered_item_list_ = new ArrayList<MenuItem>(ordered_item_list);
		this.remarks_ = remarks;
		LocalDateTime dateTime = LocalDateTime.now();
		this.time_created_ = AppManager.DATETIME_FORMATTER.format(dateTime);
		this.time_completed_ = null;
		this.status_ = RoomServiceOrderStatus.Confirmed;
		this.order_quantity_ = this.ordered_item_list_.size();
	}

	/**
	 * Gets the room service order code.
	 * 
	 * @return UUID on the code for the room service order
	 */
	public UUID GetRoomServiceOrderCode() {
		return this.room_service_order_code_;
	}

	/**
	 * Sets the ID for room service_order_code.
	 * 
	 * @param room_service_order_code_ Unique code to be set
	 */
	public void SetRoomServiceOrderCode(String room_service_order_code) {
		this.room_service_order_code_ = UUID.fromString(room_service_order_code);
	}

	/**
	 * Get the time stamp when order was delivered.
	 * 
	 * @return timestamp of when order was created
	 */
	public String GetTimeCreated() {
		return this.time_created_;
	}

	/**
	 * Gets the time stamp when order was delivered.
	 * 
	 * @return time stamp of when order was delivered
	 */
	public String GetTimeCompleted() {
		return this.time_completed_;
	}

	/**
	 * Sets the time completed for the room service order.
	 * 
	 * @param time_completed time stamp to be set
	 */
	public void SetTimeCompleted(String time_completed) {
		this.time_completed_ = time_completed;
	}

	/**
	 * Gets the list of ordered menu items.
	 * 
	 * @return List containing the list of MenuItem
	 */
	public List<MenuItem> GetOrderedItemList() {
		return this.ordered_item_list_;
	}

	/**
	 * Sets the ordered item list.
	 * 
	 * @param item_list list of menu items to be set
	 */
	public void SetOrderedItemList(List<MenuItem> item_list) {
		this.ordered_item_list_ = item_list;
	}

	/**
	 * Gets the room number.
	 * 
	 * @return int containing the room number
	 */
	public int GetRoomNum() {
		return this.room_number_;
	}

	/**
	 * Sets the room number for the room service order.
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {
		this.room_number_ = room_num;
	}

	/**
	 * Return remarks for the room service order.
	 * 
	 * @return String containing remarks for the room service order
	 */
	public String GetRemarks() {
		return this.remarks_;
	}

	/**
	 * Sets remarks for the room service order.
	 * 
	 * @param remarks String for the remarks
	 */
	public void SetRemarks(String remarks) {
		this.remarks_ = remarks;
	}

	/**
	 * Gets the order status of the room service order.
	 * 
	 * @return RoomServiceOrderStatus status of the order
	 */
	public RoomServiceOrderStatus GetStatus() {
		return this.status_;
	}

	/**
	 * Set the order status of the room service order. Updates time stamp when order
	 * is delivered.
	 * 
	 * @param status enum RoomServiceOrderStatus on the status
	 */
	public void SetStatus(RoomServiceOrderStatus status) {
		this.status_ = status;
		if (this.status_ == RoomServiceOrderStatus.Delivered) {
			// create only first time
			if (this.time_completed_ == null) {
				LocalDateTime dateTime = LocalDateTime.now();
				this.time_completed_ = AppManager.DATETIME_FORMATTER.format(dateTime);
			}
		}
	}

	/**
	 * Generates string for all the menu items for the room service order in
	 * separate lines.
	 * 
	 * @return String containing the list of menu items
	 */
	public String MenuItemstoString() {
		String Textout = "";
		for (MenuItem menu_item : ordered_item_list_) {
			Textout += "   ." + menu_item.toString() + "\n";
		}
		return Textout;
	}

	/**
	 * Returns String for guest ID.
	 * 
	 * @return String guest ID
	 */
	public String GetGuestId() {
		return guest_id_;
	}

	/**
	 * Gets number of orders for the room service order.
	 * 
	 * @return int containing number of orders
	 */
	public int GetOrderQuantity() {
		return order_quantity_;
	}

	/**
	 * Gets total price of room service order based on all the menu items in the
	 * ordered list.
	 * 
	 * @return float containing the total price
	 */
	public float CalculateOrderTotalPrice() {
		float total_price = 0.0f;
		for (MenuItem menu_item : ordered_item_list_) {
			total_price += menu_item.GetPrice();
		}
		return total_price;
	}

	/**
	 * Generate string for printing room service order at search menu.
	 */
	@Override
	public String toString() {
		String output = String.format(
				"|Room Service Order Code: %s|\n" + "|Guest Id: %s|Room Number: %d|\n"
						+ "|Time Created: %s|Time Completed: %s|\n" + "|Remarks: %s|\n" + "|Status: %s|Quantity: %d|\n"
						+ "|Ordered Items:\n",
				this.room_service_order_code_, this.guest_id_, this.room_number_, this.time_created_,
				this.time_completed_, this.remarks_, this.status_, this.order_quantity_);
		for (MenuItem menu_item : this.ordered_item_list_) {
			output += "|  " + menu_item.toString() + "\n";
		}
		return output;
	}
}