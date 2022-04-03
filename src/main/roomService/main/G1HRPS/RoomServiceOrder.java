package main.G1HRPS;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;


public class RoomServiceOrder {

	private final String room_service_order_code_;
	private String guest_id_;
	private int room_num_;
	private final Timestamp time_created_;
	private Timestamp time_completed_;
	private List<MenuItem> ordered_item_list_;
	private String remarks_;
	private OrderStatus status_;

	/**
	 * 
	 * @param room_service_order_code
	 * @param guest_id
	 * @param room_id
	 * @param ordered_item_list
	 * @param remarks
	 */
	public RoomServiceOrder(String room_service_order_code, String guest_id, int room_id, List<MenuItem> ordered_item_list, String remarks) {
		// TODO - implement RoomServiceOrder.RoomServiceOrder
		this.ordered_item_list_ = new ArrayList<MenuItem>(ordered_item_list);
		this.guest_id_ = guest_id;
		this.room_service_order_code_ = room_service_order_code;
		this.time_created_ = new Timestamp(System.currentTimeMillis());
		this.remarks_ = remarks;
		this.status_ = OrderStatus.Preparing;
	}

	public String GetRsoCode() {
		// TODO - implement RoomServiceOrder.GetRsoCode
		return this.room_service_order_code_;
	}

	/**
	 * 
	 * @param room_service_order_code_
	 */
	//May not be required??
	public void SetRsoCode(String room_service_order_code_) {
		// TODO - implement RoomServiceOrder.SetRsoCode
		//this.room_service_order_code_ = room_service_order_code_;
		
	}

	public Timestamp GetTimeCreated() {
		// TODO - implement RoomServiceOrder.GetTimeCreated
		return this.time_created_;
	}

	/**
	 * 
	 * @param time_created_
	 */
	// Not needed i think
	public void SetTimeCreated() {
		// TODO - implement RoomServiceOrder.SetTimeCreated
	}

	public Timestamp GetTimeCompleted() {
		// TODO - implement RoomServiceOrder.GetTimeCompleted
		return this.time_completed_;
		
	}

	/**
	 * 
	 * @param time_completed_
	 */
	public void SetTimeCompleted(Timestamp time_completed) {
		// TODO - implement RoomServiceOrder.SetTimeCompleted
		this.time_completed_ = time_completed;
	}

	public List<MenuItem> GetOrderedItemList() {
		// TODO - implement RoomServiceOrder.GetOrderedItemList
		return this.ordered_item_list_;
	}

	/**
	 * 
	 * @param item_list
	 */
	public void SetOrderedItemList(List<MenuItem> item_list) {
		// TODO - implement RoomServiceOrder.SetOrderedItemList
		throw new UnsupportedOperationException();
	}

	public int GetRoomNum() {
		// TODO - implement RoomServiceOrder.GetRoomNum
		return this.room_num_;
	}

	/**
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {
		// TODO - implement RoomServiceOrder.SetRoomNum
		this.room_num_ = room_num;
	}

	public String GetRemarks() {
		// TODO - implement RoomServiceOrder.GetRemarks
		return this.remarks_;
	}

	/**
	 * 
	 * @param remarks
	 */
	public void SetRemarks(String remarks) {
		// TODO - implement RoomServiceOrder.SetRemarks
		this.remarks_ = remarks;
	}

	public OrderStatus GetStatus() {
		// TODO - implement RoomServiceOrder.GetStatus
		return this.status_;
	}

	/**
	 * 
	 * @param status
	 */
	public void SetStatus(OrderStatus status) {
		// TODO - implement RoomServiceOrder.SetStatus
		this.status_ = status;
	}
	// To be worked out. This is still a simple version
	public String toString()
	{
		return ("RSO code: "+ this.room_service_order_code_ + " Remarks: " + this.remarks_);
	}

	public String getGuest_id() {
		return guest_id_;
	}
	public float CalTotalPrice()
	{
		float total_price = 0.0f;
		for (int i = 0; i < this.ordered_item_list_.size(); i++)
		{
			total_price += this.ordered_item_list_.get(i).getPrice(); 
		}
		return total_price;
	}
}