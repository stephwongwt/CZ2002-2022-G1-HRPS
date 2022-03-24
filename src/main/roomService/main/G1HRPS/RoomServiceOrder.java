package main.G1HRPS;

import java.sql.Timestamp;
import java.util.List;

public class RoomServiceOrder {

	private final String room_service_order_code_;
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
		throw new UnsupportedOperationException();
	}

	public String GetRsoCode() {
		// TODO - implement RoomServiceOrder.GetRsoCode
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room_service_order_code_
	 */
	public void SetRsoCode(String room_service_order_code_) {
		// TODO - implement RoomServiceOrder.SetRsoCode
		throw new UnsupportedOperationException();
	}

	public Timestamp GetTimeCreated() {
		// TODO - implement RoomServiceOrder.GetTimeCreated
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param time_created_
	 */
	public void SetTimeCreated(Timestamp time_created_) {
		// TODO - implement RoomServiceOrder.SetTimeCreated
		throw new UnsupportedOperationException();
	}

	public Timestamp GetTimeCompleted() {
		// TODO - implement RoomServiceOrder.GetTimeCompleted
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param time_completed_
	 */
	public void SetTimeCompleted(Timestamp time_completed_) {
		// TODO - implement RoomServiceOrder.SetTimeCompleted
		throw new UnsupportedOperationException();
	}

	public List<MenuItem> GetOrderedItemList() {
		// TODO - implement RoomServiceOrder.GetOrderedItemList
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room_num
	 */
	public void SetRoomNum(int room_num) {
		// TODO - implement RoomServiceOrder.SetRoomNum
		throw new UnsupportedOperationException();
	}

	public String GetRemarks() {
		// TODO - implement RoomServiceOrder.GetRemarks
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param remarks
	 */
	public void SetRemarks(String remarks) {
		// TODO - implement RoomServiceOrder.SetRemarks
		throw new UnsupportedOperationException();
	}

	public OrderStatus GetStatus() {
		// TODO - implement RoomServiceOrder.GetStatus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param status
	 */
	public void SetStatus(OrderStatus status) {
		// TODO - implement RoomServiceOrder.SetStatus
		throw new UnsupportedOperationException();
	}

}